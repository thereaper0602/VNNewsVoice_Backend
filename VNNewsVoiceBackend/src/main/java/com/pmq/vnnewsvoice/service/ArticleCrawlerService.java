package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.Article;

import java.util.List;

public interface ArticleCrawlerService {
    /**
     * Fetch articles from external API
     * @return List of articles
     */
    List<Article> fetchArticlesFromAPI();
    
    /**
     * Save fetched articles to database
     * @param articles List of articles to save
     * @return Number of articles saved
     */
    int saveArticlesToDatabase(List<Article> articles);
    
    /**
     * Fetch and save articles in one operation
     * @return Number of articles saved
     */
    int fetchAndSaveArticles();
}