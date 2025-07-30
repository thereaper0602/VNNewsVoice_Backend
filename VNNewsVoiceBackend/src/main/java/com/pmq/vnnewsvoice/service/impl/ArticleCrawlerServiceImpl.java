package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.enums.ArticleStatus;
import com.pmq.vnnewsvoice.pojo.Article;
import com.pmq.vnnewsvoice.pojo.ArticleBlock;
import com.pmq.vnnewsvoice.pojo.Category;
import com.pmq.vnnewsvoice.pojo.Generator;
import com.pmq.vnnewsvoice.service.ArticleCrawlerService;
import com.pmq.vnnewsvoice.service.ArticleBlockService;
import com.pmq.vnnewsvoice.service.ArticleService;
import com.pmq.vnnewsvoice.service.GeneratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ArticleCrawlerServiceImpl implements ArticleCrawlerService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleBlockService articleBlockService;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.crawler.url}")
    private String apiUrl;

    // Store blocks data temporarily for each article
    private Map<String, List<Map<String, Object>>> articleBlocksData = new HashMap<>();

    @Override
    public List<Article> fetchArticlesFromAPI() {
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            Map<String, Object> responseBody = response.getBody();
            List<Article> articles = new ArrayList<>();
            articleBlocksData.clear();

            if (responseBody != null && (Boolean) responseBody.get("success")) {
                @SuppressWarnings("unchecked") // Báo trình biên dịch bỏ qua các lỗi ép kiểu
                List<Map<String, Object>> articlesData = (List<Map<String, Object>>) responseBody.get("data");

                if (articlesData != null) {
                    for (Map<String, Object> articleData : articlesData) {
                        // Extract blocks data
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> blocks = (List<Map<String, Object>>) articleData.get("blocks");

                        Article article = mapToArticle(articleData);
                        articles.add(article);

                        // Store blocks data for later use
                        articleBlocksData.put(article.getTitle(), blocks);
                    }
                }
            }

            return articles;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public int saveArticlesToDatabase(List<Article> articles) {
        int count = 0;
        for (Article article : articles) {
            try {
                // Check if article with same title or slug already exists
                List<Article> existingArticles = articleService.searchArticles(
                        Map.of("title", article.getTitle(), "slug", article.getSlug()),
                        Map.of("page", "1", "size", "1"));

                if (existingArticles.isEmpty()) {
                    // Get blocks data from our temporary storage
                    List<Map<String, Object>> blocks = articleBlocksData.get(article.getTitle());

                    // Save article first without blocks
                    Article savedArticle = articleService.addArticle(article);

                    // Create and save article blocks after article is saved
                    if (blocks != null && !blocks.isEmpty()) {
                        List<ArticleBlock> articleBlocks = createArticleBlocksFromData(blocks);
                        for (ArticleBlock block : articleBlocks) {
                            block.setArticleId(savedArticle);
                            articleBlockService.addArticleBlock(block);
                        }
                    }

                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public int fetchAndSaveArticles() {
        List<Article> articles = fetchArticlesFromAPI();
        return saveArticlesToDatabase(articles);
    }

    private Article mapToArticle(Map<String, Object> articleData) {
        Article article = new Article();

        // Map fields from API response to Article object
        String title = (String) articleData.get("title");
        String url = (String) articleData.get("url");
        String publishedAt = (String) articleData.get("published_at");

        article.setTitle(title);
        article.setOriginalUrl(url);
        article.setSlug(generateSlugFromUrl(url));

        // Parse published date
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
            Date publishedDate = formatter.parse(publishedAt);
            article.setPublishedDate(publishedDate);
        } catch (Exception e) {
            article.setPublishedDate(new Date()); // Fallback to current date
        }

        // Process blocks to create article blocks
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> blocks = (List<Map<String, Object>>) articleData.get("blocks");
        // Summary will be generated by another API later
        article.setSummary("");

        // Store blocks data for later processing after article is saved
        article.setArticleblockSet(new HashSet<>());
        // We'll create and save ArticleBlock objects after the article is saved

        // Set default values
        article.setIsActive(true);
        article.setAuthor("API Crawler");

        // Set category - assuming we have a default category with ID 1
        Category category = new Category(1L);
        article.setCategoryId(category);

        // Extract generator from URL
        Generator generator = extractGeneratorFromUrl(url);
        article.setGeneratorId(generator);

        if (articleData.containsKey("top_image")) {
            article.setTopImageUrl((String) articleData.get("top_image"));
        }

        article.setIsBeingEdited(false);
        article.setIsActive(true);
        article.setStatus(ArticleStatus.DRAFT);

        return article;
    }

    private String generateSlugFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return "article-" + System.currentTimeMillis();
        }

        // Extract the last part of URL path
        String[] parts = url.split("/");
        String lastPart = parts[parts.length - 1];

        // Remove file extensions and clean up
        lastPart = lastPart.replaceAll("\\.(html|htm|php|asp|aspx)$", "");

        // Convert to slug format: lowercase, replace special chars with hyphens
        String slug = lastPart.toLowerCase()
                .replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
                .replaceAll("[èéẹẻẽêềếệểễ]", "e")
                .replaceAll("[ìíịỉĩ]", "i")
                .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
                .replaceAll("[ùúụủũưừứựửữ]", "u")
                .replaceAll("[ỳýỵỷỹ]", "y")
                .replaceAll("[đ]", "d")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-+|-+$", "");

        return slug.isEmpty() ? "article-" + System.currentTimeMillis() : slug;
    }

    // Summary generation method removed as it will be handled by a separate API

    private List<ArticleBlock> createArticleBlocksFromData(List<Map<String, Object>> blocks) {
        List<ArticleBlock> articleBlocks = new ArrayList<>();

        if (blocks == null || blocks.isEmpty()) {
            return articleBlocks;
        }

        for (Map<String, Object> blockData : blocks) {
            ArticleBlock articleBlock = new ArticleBlock();

            // Map fields from block data
            Object orderObj = blockData.get("order");
            if (orderObj instanceof Integer) {
                articleBlock.setOrderIndex((Integer) orderObj);
            } else if (orderObj instanceof String) {
                try {
                    articleBlock.setOrderIndex(Integer.parseInt((String) orderObj));
                } catch (NumberFormatException e) {
                    articleBlock.setOrderIndex(0);
                }
            } else {
                articleBlock.setOrderIndex(0);
            }

            articleBlock.setType((String) blockData.get("type"));
            articleBlock.setContent((String) blockData.get("content"));
            articleBlock.setSrc((String) blockData.get("src"));
            articleBlock.setAlt((String) blockData.get("alt"));
            articleBlock.setCaption((String) blockData.get("caption"));

            // Xử lý đúng cách cho các loại block khác nhau
            String type = articleBlock.getType();

            // Xử lý cho block loại heading
            if ("heading".equals(type)) {
                articleBlock.setText((String) blockData.get("text"));
                articleBlock.setTag((String) blockData.get("tag"));
            }
            // Xử lý cho block loại paragraph
            else if ("paragraph".equals(type)) {
                // Nếu cần thiết, có thể xử lý đặc biệt cho paragraph
            }
            // Xử lý cho block loại text (nếu có)
            else if ("text".equals(type)) {
                articleBlock.setText(articleBlock.getContent());
            }

            articleBlocks.add(articleBlock);
        }

        return articleBlocks;
    }

    private Generator extractGeneratorFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return new Generator(1L); // Default generator
        }

        // Extract domain from URL
        String domain = "";
        try {
            // Remove protocol (http://, https://)
            if (url.contains("://")) {
                domain = url.split("://")[1];
            } else {
                domain = url;
            }

            // Get domain (remove path and query parameters)
            if (domain.contains("/")) {
                domain = domain.split("/")[0];
            }

            // Remove www. prefix if exists
            if (domain.startsWith("www.")) {
                domain = domain.substring(4);
            }

            // Extract base domain (e.g., vnexpress.net, thanhnien.vn)
            if (domain.contains(".")) {
                String[] parts = domain.split("\\.");
                if (parts.length >= 2) {
                    // Get the main domain name (e.g., vnexpress, thanhnien)
                    domain = parts[parts.length - 2];
                }
            }
        } catch (Exception e) {
            return new Generator(1L); // Default generator on error
        }

        // Map domain to generator
        switch (domain.toLowerCase()) {
            case "vnexpress":
                return new Generator(1L); // Assuming VnExpress has ID 1
            case "thanhnien":
                return new Generator(2L); // Assuming Thanh Nien has ID 2
            case "tuoitre":
                return new Generator(3L); // Assuming Tuoi Tre has ID 3
            case "dantri":
                return new Generator(4L); // Assuming Dan Tri has ID 4
            default:
                // Try to find by name in database
                Optional<Generator> generatorOpt = generatorService.getGeneratorByName(domain);
                if (generatorOpt.isPresent()) {
                    return generatorOpt.get();
                }
                return new Generator(1L); // Default generator
        }
    }

}
