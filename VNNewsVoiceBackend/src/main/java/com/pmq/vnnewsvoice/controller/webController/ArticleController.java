package com.pmq.vnnewsvoice.controller.webController;

import com.pmq.vnnewsvoice.enums.ArticleStatus;
import com.pmq.vnnewsvoice.helpers.PaginationHelper;
import com.pmq.vnnewsvoice.pojo.Article;
import com.pmq.vnnewsvoice.pojo.ArticleBlock;
import com.pmq.vnnewsvoice.pojo.Category;
import com.pmq.vnnewsvoice.pojo.Editor;
import com.pmq.vnnewsvoice.service.*;
import com.pmq.vnnewsvoice.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCrawlerService articleCrawlerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PaginationHelper paginationHelper;

    @Autowired
    private EditorService editorService;

    @Autowired
    private ArticleBlockService articleBlockService;

    @GetMapping("/articles")
    public String listArticles(Model model,
            @RequestParam Map<String, String> params,
            RedirectAttributes redirectAttributes) {

        // Thêm cơ chế xác thực người dùng sau này

        //

        long totalArticles = articleService.countSearchArticles(params);

        if (totalArticles == 0) {
            redirectAttributes.addFlashAttribute("error", "No articles found");
            return "redirect:/admin/articles";
        }

        Pagination pagination = paginationHelper.createPagination(params, totalArticles, 10);
        params.put("page", String.valueOf(pagination.getCurrentPage()));
        List<Article> articles = articleService.getArticles(params);
        List<Category> categories = categoryService.getCategories(new HashMap<>());
        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", pagination.getCurrentPage());
        model.addAttribute("totalPages", pagination.getTotalPages());
        model.addAttribute("startIndex", pagination.getStartIndex());
        model.addAttribute("endIndex", pagination.getEndIndex());
        model.addAttribute("totalItems", totalArticles);


        model.addAttribute("categoryFilter", params.get("categoryId"));
        model.addAttribute("statusFilter", params.get("status"));
        model.addAttribute("publishedDateFilter", params.get("publishedDates"));
        model.addAttribute("title", params.get("title"));
        return "dashboard/admin/article_list";
    }

    @GetMapping("/articles/fetch")
    public String showFetchArticlesForm() {
        return "dashboard/admin/fetch_articles";
    }

    @PostMapping("/articles/fetch")
    public String fetchArticles(RedirectAttributes redirectAttributes) {
        try {
            int count = articleCrawlerService.fetchAndSaveArticles();
            redirectAttributes.addFlashAttribute("success", "Successfully fetched and saved " + count + " articles");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error fetching articles: " + e.getMessage());
        }
        return "redirect:/admin/articles";
    }

    @GetMapping("/articles/{slug}_{id}")
    public String viewArticle(@PathVariable String slug,
            @PathVariable Long id,
            @RequestParam Map<String, String> params,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<Article> article = articleService.getArticleBySlugAndId(slug, id);
            List<Editor> editors = editorService.getEditors(new HashMap<>());
            if (article.isPresent()) {
                List<ArticleBlock> articleBlocks = articleBlockService
                        .getArticleBlocksByArticleId(article.get().getId());
                model.addAttribute("article", article.get());
//                model.addAttribute("editors", editors);
                model.addAttribute("articleBlocks", articleBlocks);
                return "dashboard/admin/article_admin_detail";
            } else {
                redirectAttributes.addFlashAttribute("error", "Article not found");
                return "redirect:/admin/articles";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error retrieving article: " + e.getMessage());
            return "redirect:/admin/articles";
        }
    }

    @PostMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // Check the role of the user before allowing deletion (later)

            // Adding additional check to ensure the article are not being edited (if
            // applicable)

            // Assuming the articleService has a method to check if an article exists

            Optional<Article> article = articleService.getArticleById(id);
            if (article.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Article not found");
                return "redirect:/admin/articles";
            }

            if (article.get().getIsBeingEdited()) {
                redirectAttributes.addFlashAttribute("error",
                        "Article is currently being edited and cannot be deleted");
                return "redirect:/admin/articles";
            }

            boolean deleted = articleService.deleteArticle(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("success", "Article deleted successfully");
            } else {
                redirectAttributes.addFlashAttribute("error", "Article not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting article: " + e.getMessage());
        }
        return "redirect:/admin/articles";
    }

    @PostMapping("articles/{slug}_{id}/assign-editor")
    public String assignEditor(@PathVariable String slug,
            @PathVariable Long id,
            @RequestParam("editorId") Long editorId,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<Article> article = articleService.getArticleBySlugAndId(slug, id);
            if (article.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Article not found");
                return "redirect:/admin/articles";
            }
            Optional<Editor> editor = editorService.getEditorById(editorId);
            if (editor.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Editor not found");
                return "redirect:/admin/articles";
            }
            article.get().setEditorId(editor.get());
            articleService.updateArticle(article.get());
            redirectAttributes.addFlashAttribute("success", "Editor assigned to article successfully");
            return "redirect:/admin/articles/" + slug + "_" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error assigning editor to article: " + e.getMessage());
            return "redirect:/admin/articles";
        }
    }

    @PostMapping("/articles/{slug}_{id}/change-status")
    public String changeArticleStatus(@PathVariable String slug,
            @PathVariable Long id,
            @RequestParam("status") String status,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<Article> articleOpt = articleService.getArticleBySlugAndId(slug, id);
            if (articleOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Article not found");
                return "redirect:/admin/articles";
            }

            Article article = articleOpt.get();
            if (article.getIsBeingEdited()) {
                redirectAttributes.addFlashAttribute("error",
                        "Article is currently being edited and cannot change status");
                return "redirect:/admin/articles/" + slug + "_" + id;
            }

            ArticleStatus newStatus = ArticleStatus.valueOf(status);
            article.setStatus(newStatus);

            // Nếu trạng thái là PUBLISHED, cập nhật ngày xuất bản
            if (newStatus == ArticleStatus.PUBLISHED) {
                article.setPublishedDate(new Date());
            }

            articleService.updateArticle(article);
            redirectAttributes.addFlashAttribute("success", "Article status updated successfully");
            return "redirect:/admin/articles/" + slug + "_" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating article status: " + e.getMessage());
            return "redirect:/admin/articles";
        }
    }

    @PostMapping("/articles/{slug}_{id}/save-all-changes")
    @ResponseBody
    public Map<String, Object> saveAllChanges(@PathVariable String slug,
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Article> articleOpt = articleService.getArticleBySlugAndId(slug, id);
            if (articleOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Article not found");
                return response;
            }

            Article article = articleOpt.get();
            
            // Lấy danh sách các block đã chỉnh sửa
            List<Map<String, Object>> editedBlocks = (List<Map<String, Object>>) payload.get("blocks");
            
            if (editedBlocks != null && !editedBlocks.isEmpty()) {
                for (Map<String, Object> blockData : editedBlocks) {
                    // Xử lý block loại summary
                    if ("summary".equals(blockData.get("type"))) {
                        String summaryContent = (String) blockData.get("content");
                        if (summaryContent != null && !summaryContent.isEmpty()) {
                            article.setSummary(summaryContent);
                        }
                        continue;
                    }
                    
                    Long blockId = Long.valueOf(blockData.get("id").toString());
                    String blockType = (String) blockData.get("type");
                    
                    Optional<ArticleBlock> blockOpt = articleBlockService.getArticleBlockById(blockId);
                    if (blockOpt.isPresent()) {
                        ArticleBlock block = blockOpt.get();
                        
                        // Cập nhật thông tin block dựa trên loại
                        switch (blockType) {
                            case "paragraph":
                                block.setContent((String) blockData.get("content"));
                                break;
                            case "image":
                                block.setSrc((String) blockData.get("src"));
                                block.setAlt((String) blockData.get("alt"));
                                block.setCaption((String) blockData.get("caption"));
                                break;
                            case "heading":
                                block.setText((String) blockData.get("text"));
                                block.setTag((String) blockData.get("tag"));
                                break;
                        }
                        
                        // Cập nhật block
                        articleBlockService.updateArticleBlock(block);
                    }
                }
            }
            
            // Xử lý trường summary được gửi trực tiếp từ payload
            if (payload.containsKey("summary")) {
                Object summaryObj = payload.get("summary");
                if (summaryObj != null && !(summaryObj instanceof String && ((String) summaryObj).isEmpty())) {
                    article.setSummary(summaryObj.toString());
                }
            }
            
            // Cập nhật thời gian chỉnh sửa
            article.setUpdatedAt(new Date());
            
            // Lưu thay đổi
            articleService.updateArticle(article);
            
            response.put("success", true);
            response.put("message", "All changes saved successfully");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error saving changes: " + e.getMessage());
        }
        
        return response;
    }

    @PostMapping("/articles/{slug}_{id}/start-editing")
    public String startEditing(@PathVariable String slug,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<Article> articleOpt = articleService.getArticleBySlugAndId(slug, id);
            if (articleOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài viết");
                return "redirect:/admin/articles";
            }

            // Kiểm tra người bắt đầu chỉnh sửa có được phép chỉnh sửa không (Thêm vào sau)

            //

            Article article = articleOpt.get();
            
            if (article.getIsBeingEdited()) {
                redirectAttributes.addFlashAttribute("error", "Bài viết đang được chỉnh sửa bởi người khác");
                return "redirect:/admin/articles/" + slug + "_" + id;
            }
            
            // Cập nhật trạng thái chỉnh sửa
            article.setIsBeingEdited(true);
            article.setEditStartedAt(new Date());
            articleService.updateArticle(article);
            
            redirectAttributes.addFlashAttribute("success", "Đã bắt đầu chỉnh sửa bài viết");
            return "redirect:/admin/articles/" + slug + "_" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi bắt đầu chỉnh sửa: " + e.getMessage());
            return "redirect:/admin/articles";
        }
    }

    @PostMapping("/articles/{slug}_{id}/submit")
    public String submitArticle(@PathVariable String slug,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<Article> articleOpt = articleService.getArticleBySlugAndId(slug, id);
            if (articleOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài viết");
                return "redirect:/admin/articles";
            }

            Article article = articleOpt.get();

            if (!article.getIsBeingEdited()) {
                redirectAttributes.addFlashAttribute("error", "Bài viết không đang trong trạng thái chỉnh sửa");
                return "redirect:/admin/articles/" + slug + "_" + id;
            }
            
            // Kiểm tra xem bài viết đã có tóm tắt chưa
            if (article.getSummary() == null || article.getSummary().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Bài viết cần có tóm tắt trước khi gửi phê duyệt");
                return "redirect:/admin/articles/" + slug + "_" + id;
            }
            
            // Kiểm tra xem bài viết đã có audio chưa
            if (article.getAudioUrl() == null || article.getAudioUrl().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Bài viết cần có audio trước khi gửi phê duyệt");
                return "redirect:/admin/articles/" + slug + "_" + id;
            }

            // Cập nhật trạng thái bài viết
            article.setStatus(ArticleStatus.PENDING);
            article.setIsBeingEdited(false);
            article.setEditStartedAt(null);
            articleService.updateArticle(article);

            redirectAttributes.addFlashAttribute("success", "Đã gửi bài viết thành công");
            return "redirect:/admin/articles/" + slug + "_" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi gửi bài viết: " + e.getMessage());
            return "redirect:/admin/articles";
        }
    }
    
    @PostMapping("/articles/{slug}_{id}/save-audio-url")
    @ResponseBody
    public Map<String, Object> saveAudioUrl(@PathVariable String slug,
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Article> articleOpt = articleService.getArticleBySlugAndId(slug, id);
            if (articleOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Không tìm thấy bài viết");
                return response;
            }

            Article article = articleOpt.get();
            
            // Lấy audio URL từ payload
            String audioUrl = (String) payload.get("audioUrl");
            
            // Cập nhật audio URL
            article.setAudioUrl(audioUrl);
            
            // Cập nhật thời gian chỉnh sửa
            article.setUpdatedAt(new Date());
            
            // Lưu thay đổi
            articleService.updateArticle(article);
            
            response.put("success", true);
            response.put("message", "Đã lưu audio URL thành công");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi khi lưu audio URL: " + e.getMessage());
        }
        
        return response;
    }
}
