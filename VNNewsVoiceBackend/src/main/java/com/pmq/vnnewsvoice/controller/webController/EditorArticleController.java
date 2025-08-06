package com.pmq.vnnewsvoice.controller.webController;

import com.pmq.vnnewsvoice.enums.ArticleStatus;
import com.pmq.vnnewsvoice.helpers.PaginationHelper;
import com.pmq.vnnewsvoice.pojo.*;
import com.pmq.vnnewsvoice.service.*;
import com.pmq.vnnewsvoice.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/editor")
public class EditorArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private EditorService editorService;

    @Autowired
    private PaginationHelper paginationHelper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleBlockService articleBlockService;

    @GetMapping("/articles")
    public String listArticles(
            Model model,
            @RequestParam Map<String, String> params,
            @AuthenticationPrincipal CustomUserDetails principal) {

        Optional<UserInfo> user = userInfoService.getUserById(principal.getUserInfo().getId());

        if (user.isEmpty()) {
            return "authentication/login";
        }

        if (user.isPresent()) {
            Optional<Editor> editor = editorService.getEditorByUserId(user.get().getId());
            if (editor.isPresent()) {
                params.put("editorId", editor.get().getId().toString());
                long totalArticles = articleService.countSearchArticles(params);

                Pagination pagination = paginationHelper.createPagination(params, totalArticles, 10);
                params.put("page", String.valueOf(pagination.getCurrentPage()));
                List<Article> articleList = articleService.getArticles(params);
                List<Category> categoryList = categoryService.getCategories(new HashMap<>());
                model.addAttribute("articles", articleList);
                model.addAttribute("categories", categoryList);
                model.addAttribute("currentPage", pagination.getCurrentPage());
                model.addAttribute("totalPages", pagination.getTotalPages());
                model.addAttribute("startIndex", pagination.getStartIndex());
                model.addAttribute("endIndex", pagination.getEndIndex());
                model.addAttribute("totalItems", totalArticles);

                model.addAttribute("categoryFilter", params.get("categoryId"));
                model.addAttribute("statusFilter", params.get("status"));
                model.addAttribute("publishedDateFilter", params.get("publishedDates"));
                model.addAttribute("title", params.get("title"));
                return "dashboard/editor/article_editor_list";
            }
        }
        return "authentication/login";
    }

    @GetMapping("articles/{slug}_{id}")
    public String viewArticle(@PathVariable String slug,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes,
            Model model,
            @AuthenticationPrincipal CustomUserDetails principal) {
        try {
            Optional<Article> article = articleService.getArticleBySlugAndIdWithPermissionCheck(slug, id,
                    principal.getUserInfo());

            if (article.isPresent()) {
                List<ArticleBlock> articleBlocks = articleBlockService
                        .getArticleBlocksByArticleId(article.get().getId());

                model.addAttribute("article", article.get());

                model.addAttribute("articleBlocks", articleBlocks);

                return "dashboard/editor/article_editor_detail";
            }
            redirectAttributes.addFlashAttribute("error", "Không thấy bài báo");
            return "redirect:/editor/articles";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi tải bài báo: " + e.getMessage());
            return "redirect:/editor/articles";
        }
    }

    @PostMapping("/articles/{slug}_{id}/save-all-changes")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveAllChanges(@PathVariable String slug,
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload,
            @AuthenticationPrincipal CustomUserDetails principal) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Article> articleOpt = articleService.getArticleBySlugAndIdWithPermissionCheck(slug, id,
                    principal.getUserInfo());

            if (articleOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Article not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Article article = articleOpt.get();

            List<Map<String, Object>> editedBlocks = (List<Map<String, Object>>) payload.get("blocks");

            if (editedBlocks != null && !editedBlocks.isEmpty()) {
                for (Map<String, Object> blockData : editedBlocks) {
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

            if (payload.containsKey("summary")) {
                Object summaryObj = payload.get("summary");
                if (summaryObj != null && !(summaryObj instanceof String && ((String) summaryObj).isEmpty())) {
                    article.setSummary(summaryObj.toString());
                }
            }

            // Xử lý audioUrl nếu có trong payload
            if (payload.containsKey("audioUrl")) {
                String audioUrl = (String) payload.get("audioUrl");
                article.setAudioUrl(audioUrl);
            }

            article.setUpdatedAt(new Date());

            articleService.updateArticle(article);
            response.put("success", true);
            response.put("message", "All changes saved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error saving changes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/articles/{slug}_{id}/start-editing")
    public String startEditing(@PathVariable String slug,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails principal) {
        try {
            Optional<Article> articleOpt = articleService.getArticleBySlugAndIdWithPermissionCheck(slug, id,
                    principal.getUserInfo());

            if (articleOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài viết");
                return "redirect:/admin/articles";
            }

            Article article = articleOpt.get();

            article.setIsBeingEdited(true);
            article.setEditStartedAt(new Date());
            articleService.updateArticle(article);

            redirectAttributes.addFlashAttribute("success", "Đã bắt đầu chỉnh sửa bài viết");
            return "redirect:/editor/articles/" + slug + "_" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi bắt đầu chỉnh sửa: " + e.getMessage());
            return "redirect:/editor/articles";
        }
    }

    @PostMapping("/articles/{slug}_{id}/save-audio-url")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveAudioUrl(@PathVariable String slug,
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload,@AuthenticationPrincipal CustomUserDetails principal) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Article> articleOpt = articleService.getArticleBySlugAndIdWithPermissionCheck(slug, id,
                    principal.getUserInfo());
            if (articleOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Không tìm thấy bài viết");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi khi lưu audio URL: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/articles/{slug}_{id}/submit")
    public String submitArticle(@PathVariable String slug,
                                @PathVariable Long id,
                                RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal CustomUserDetails principal) {
        try {
            Optional<Article> articleOpt = articleService.getArticleBySlugAndIdWithPermissionCheck(slug, id,
                    principal.getUserInfo());

            if (articleOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài viết");
                return "redirect:/editor/articles";
            }

            Article article = articleOpt.get();

            if (!article.getIsBeingEdited()) {
                redirectAttributes.addFlashAttribute("error", "Bài viết không đang trong trạng thái chỉnh sửa");
                return "redirect:/editor/articles/" + slug + "_" + id;
            }

            // Kiểm tra xem bài viết đã có tóm tắt chưa
            if (article.getSummary() == null || article.getSummary().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Bài viết cần có tóm tắt trước khi gửi phê duyệt");
                return "redirect:/editor/articles/" + slug + "_" + id;
            }

            // Kiểm tra xem bài viết đã có audio chưa
            if (article.getAudioUrl() == null || article.getAudioUrl().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Bài viết cần có audio trước khi gửi phê duyệt");
                return "redirect:/editor/articles/" + slug + "_" + id;
            }

            // Cập nhật trạng thái bài viết
            article.setStatus(ArticleStatus.PENDING);
            article.setIsBeingEdited(false);
            article.setEditStartedAt(null);
            articleService.updateArticle(article);

            redirectAttributes.addFlashAttribute("success", "Đã gửi bài viết thành công");
            return "redirect:/editor/articles/" + slug + "_" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi gửi bài viết: " + e.getMessage());
            return "redirect:/editor/articles/";
        }
    }

}
