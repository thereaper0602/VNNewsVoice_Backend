package com.pmq.vnnewsvoice.controller.webController;

import com.pmq.vnnewsvoice.pojo.Article;
import com.pmq.vnnewsvoice.service.ArticleCrawlerService;
import com.pmq.vnnewsvoice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class ArticleController {
    
    @Autowired
    private ArticleService articleService;
    
    @Autowired
    private ArticleCrawlerService articleCrawlerService;
    
    @GetMapping("/articles")
    public String listArticles(Model model) {
        Map<String, String> params = new HashMap<>();
        List<Article> articles = articleService.getArticles(params);
        model.addAttribute("articles", articles);
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
    
    @PostMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
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
}
