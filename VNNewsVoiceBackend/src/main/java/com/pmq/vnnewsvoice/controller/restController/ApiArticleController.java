package com.pmq.vnnewsvoice.controller.restController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmq.vnnewsvoice.dto.ArticleBlockDto;
import com.pmq.vnnewsvoice.dto.ArticleDto;
import com.pmq.vnnewsvoice.helpers.PaginationHelper;
import com.pmq.vnnewsvoice.mapper.ArticleBlockMapper;
import com.pmq.vnnewsvoice.mapper.ArticleMapper;
import com.pmq.vnnewsvoice.pojo.Article;
import com.pmq.vnnewsvoice.pojo.ArticleBlock;
import com.pmq.vnnewsvoice.service.ArticleBlockService;
import com.pmq.vnnewsvoice.service.ArticleService;
import com.pmq.vnnewsvoice.utils.Pagination;
import com.pmq.vnnewsvoice.pojo.Category;

@RestController
@RequestMapping("/api")
public class ApiArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleBlockService articleBlockService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired ArticleBlockMapper articleBlockMapper;

    @Autowired
    private PaginationHelper paginationHelper;

    @GetMapping("/articles")
    public ResponseEntity<Map<String, Object>> getArticleList(
            @RequestParam Map<String, String> params
    ){
        Map<String, String> filters = new HashMap<>();

        if(params.containsKey("name")){
            filters.put("name", params.get("name"));
        }

        if(params.containsKey("categoryId")){
            filters.put("categoryId", params.get("categoryId"));
        }

        if(params.containsKey("page")){
            filters.put("page", params.get("page"));
        }

        if(params.containsKey("generatorId")){
            filters.put("generatorId", params.get("generatorId"));
        }
        filters.put("status", "PUBLISHED");

        List<Article> articles = articleService.getArticles(filters);

        Pagination pagination = paginationHelper.createPagination(params, articleService.countSearchArticles(filters), 10);

        List<ArticleDto> articleDtos = articles.stream().map(articleMapper::toDto).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articleDtos);
        response.put("pagination", pagination);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles/{slug}_{id}")
    public ResponseEntity<Map<String, Object>> getArticleDetail(
            @PathVariable("slug") String slug,
            @PathVariable("id") Long id
    ){
        Optional<Article> articleOptional = articleService.getArticleBySlugAndId(slug, id);
        if(articleOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Article article = articleOptional.get();
        ArticleDto articleDto = articleMapper.toDto(article);
        List<ArticleBlock> articleBlocks = articleBlockService.getArticleBlocksByArticleId(article.getId());
        List<ArticleBlockDto> articleBlocksDto = articleBlocks.stream().map(articleBlockMapper::toDto).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("article", articleDto);
        response.put("blocks", articleBlocksDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("articles/{slug}_{id}/related")
    public ResponseEntity<Map<String, Object>> getRelatedArticles(
        @PathVariable("slug") String slug,
        @PathVariable("id") Long id,
        @RequestParam(value = "limit", defaultValue = "10") int limit
    ){
        Optional<Article> articleOptional = articleService.getArticleBySlugAndId(slug, id);
        if(articleOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Map<String, String> filters = new HashMap<>();

        Article article = articleOptional.get();
        Category category = article.getCategoryId();
        if(category == null){
            return ResponseEntity.ok(Map.of("relatedArticles", List.of()));
        }

        filters.put("categoryId", category.getId().toString());
        filters.put("status", "PUBLISHED");

        

        List<Article> relatedArticles = articleService.getArticles(filters);

        relatedArticles = relatedArticles.stream()
                .filter(a -> !a.getId().equals(article.getId()))
                .limit(limit)
                .toList();
        List<ArticleDto> relatedArticlesDto = relatedArticles.stream().map(articleMapper::toDto).toList();
        return ResponseEntity.ok(Map.of("relatedArticles", relatedArticlesDto));
    }

    




}
