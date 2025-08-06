package com.pmq.vnnewsvoice.controller.webController;

import com.pmq.vnnewsvoice.enums.ArticleStatus;
import com.pmq.vnnewsvoice.helpers.PaginationHelper;
import com.pmq.vnnewsvoice.pojo.*;
import com.pmq.vnnewsvoice.service.*;
import com.pmq.vnnewsvoice.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.AccessDeniedException;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCrawlerService articleCrawlerService;

    @Autowired
    AdminService adminService;

    @Autowired
    private PaginationHelper paginationHelper;

    @Autowired
    private EditorService editorService;

    @Autowired
    private ArticleBlockService articleBlockService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/articles")
    public String listArticles(Model model,
            @RequestParam Map<String, String> params,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails principal) {

        Optional<UserInfo> user = userInfoService.getUserById(principal.getUserInfo().getId());

        if (user.isEmpty()) {
            return "authentication/login";
        }

        if (user.isPresent()) {
            if (user.get().getAdmin() != null) {
                params.put("isActive", "true");
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
                return "dashboard/admin/article_list";
            }
        }
        return "authentication/login";
    }

    @PostMapping("/articles/fetch")
    public String fetchArticles(RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails principal) {
        try {
            Optional<UserInfo> userInfo = userInfoService.getUserById(principal.getUserInfo().getId());

            if (userInfo.isPresent() && userInfo.get().getRoleId().getName().contains("ADMIN")
                    && userInfo.get().getAdmin() != null) {
                int count = articleCrawlerService.fetchAndSaveArticles();
                redirectAttributes.addFlashAttribute("success", "Đã tải và lưu thành công " + count + " bài báo");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi tải bài báo: " + e.getMessage());
        }
        return "redirect:/admin/articles";
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
                List<Editor> editors = editorService.getEditors(new HashMap<>());

                model.addAttribute("article", article.get());

                model.addAttribute("articleBlocks", articleBlocks);

                model.addAttribute("editors", editors);

                return "dashboard/admin/article_admin_detail";
            }
            redirectAttributes.addFlashAttribute("error", "Không thấy bài báo");
            return "redirect:/admin/articles";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi tải bài báo: " + e.getMessage());
            return "redirect:/admin/articles";
        }
    }

    @GetMapping("/articles/fetch")
    public String showFetchArticlesForm(@AuthenticationPrincipal CustomUserDetails principal) {
        Optional<UserInfo> user = userInfoService.getUserById(principal.getUserInfo().getId());

        if (user.isPresent() && user.get().getAdmin() != null) {
            return "dashboard/admin/fetch_articles";
        }
        return "authentication/login";
    }

    @PostMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails principal) {
        try {
            Optional<UserInfo> user = userInfoService.getUserById(principal.getUserInfo().getId());

            if (user.isPresent() && user.get().getAdmin() != null) {
                Optional<Article> article = articleService.getArticleById(id);
                if (article.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài báo");
                    return "redirect:/admin/articles";
                }

                if (article.get().getIsBeingEdited()) {
                    redirectAttributes.addFlashAttribute("error",
                            "Bài báo đang được chỉnh sửa và không thể xóa");
                    return "redirect:/admin/articles";
                }

                boolean deleted = articleService.deleteArticle(id);
                if (deleted) {
                    redirectAttributes.addFlashAttribute("success", "Đã xóa bài báo thành công");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài báo");
                }
            } else {
                return "authentication/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa bài báo: " + e.getMessage());
        }
        return "redirect:/admin/articles";
    }

    @PostMapping("articles/{slug}_{id}/assign-editor")
    public String assignEditor(@PathVariable String slug,
            @PathVariable Long id,
            @RequestParam("editorId") Long editorId,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails principal) {
        try {

            Optional<UserInfo> user = userInfoService.getUserById(principal.getUserInfo().getId());

            if (user.isPresent() && user.get().getAdmin() != null
                    && user.get().getRoleId().getName().contains("ADMIN")) {
                Optional<Article> article = articleService.getArticleBySlugAndId(slug, id);
                if (article.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài báo");
                    return "redirect:/admin/articles";
                }
                Optional<Editor> editor = editorService.getEditorById(editorId);
                if (editor.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Không tìm thấy biên tập viên");
                    return "redirect:/admin/articles";
                }

                if (article.get().getEditorId() != null) {
                    redirectAttributes.addFlashAttribute("error",
                            "Bài báo hiện đang được chỉnh sửa bởi người kiểm duyệt khác");
                    return "redirect:/admin/articles/" + slug + "_" + id;
                }

                article.get().setEditorId(editor.get());
                articleService.updateArticle(article.get());
                redirectAttributes.addFlashAttribute("success", "Đã gán biên tập viên cho bài báo thành công");
                return "redirect:/admin/articles/" + slug + "_" + id;
            } else {
                return "authentication/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi gán biên tập viên cho bài báo: " + e.getMessage());
            return "redirect:/admin/articles";
        }
    }

    @PostMapping("/articles/{slug}_{id}/change-status")
    public String changeArticleStatus(@PathVariable String slug,
            @PathVariable Long id,
            @RequestParam("status") String status,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails principal) {
        try {
            Optional<UserInfo> user = userInfoService.getUserById(principal.getUserInfo().getId());

            if (user.isPresent() && user.get().getAdmin() != null
                    && user.get().getRoleId().getName().contains("ADMIN")) {
                Optional<Article> articleOpt = articleService.getArticleBySlugAndId(slug, id);
                if (articleOpt.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài báo");
                    return "redirect:/admin/articles";
                }

                Article article = articleOpt.get();
                if (article.getIsBeingEdited()) {
                    redirectAttributes.addFlashAttribute("error",
                            "Bài báo đang được chỉnh sửa và không thể thay đổi trạng thái");
                    return "redirect:/admin/articles/" + slug + "_" + id;
                }

                ArticleStatus newStatus = ArticleStatus.valueOf(status);
                article.setStatus(newStatus);

                // Nếu trạng thái là PUBLISHED, cập nhật ngày xuất bản
                if (newStatus == ArticleStatus.PUBLISHED) {
                    article.setPublishedDate(new Date());
                }

                articleService.updateArticle(article);

                // Tạo thông báo cho editor nếu bài viết có editor được gán
                if (article.getEditorId() != null) {
                    Notification notification = new Notification();
                    notification.setTitle("Trạng thái bài viết đã thay đổi");
                    notification.setContent("Bài viết '" + article.getTitle() + "' đã được chuyển sang trạng thái "
                            + newStatus.toString());
                    notification.setIsRead(false);
                    notification.setCreatedAt(new Date());
                    notification.setUserId(article.getEditorId().getUserId());
                    notificationService.addNotification(notification);
                }

                redirectAttributes.addFlashAttribute("success", "Đã cập nhật trạng thái bài báo thành công");
                return "redirect:/admin/articles/" + slug + "_" + id;
            }
            return "authentication/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật trạng thái bài báo: " + e.getMessage());
            return "redirect:/admin/articles";
        }
    }

}
