package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Article;
import com.pmq.vnnewsvoice.repository.ArticleRepository;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class ArticleRepositoryImpl implements ArticleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Article addArticle(Article article) {
        entityManager.persist(article);
        return article;
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return Optional.ofNullable(entityManager.find(Article.class, id));
    }

    @Override
    public Optional<Article> getArticleBySlugAndId(String slug, Long id)
    {
        String hql = "FROM Article a WHERE a.slug = :slug AND a.id = :id";
        List<Article> articles = entityManager.createQuery(hql, Article.class)
                .setParameter("slug", slug)
                .setParameter("id", id)
                .getResultList();
        return articles.isEmpty() ? Optional.empty() : Optional.of(articles.get(0));
    }

    @Override
    public List<Article> getArticlesByCategoryId(Long categoryId) {
        String hql = "FROM Article a WHERE a.categoryId.id = :categoryId ORDER BY a.publishedDate DESC";
        return entityManager.createQuery(hql, Article.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public List<Article> searchArticles(Map<String, String> filters, Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> query = builder.createQuery(Article.class);
        Root<Article> root = query.from(Article.class);
        query.select(root);

        // Build predicates (ensure you use jakarta.persistence.criteria.Predicate)
        List<Predicate> predicates = buildSearchPredicates(filters, builder, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }
        // Sort by publishedDate in descending order
        query.orderBy(builder.desc(root.get("publishedDate")));

        jakarta.persistence.TypedQuery<Article> q = entityManager.createQuery(query);

        // Pagination
        if (params != null) {
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "10"));
            // Ensure page is at least 1 to avoid negative start value
            page = Math.max(1, page);
            int start = (page - 1) * pageSize;
            q.setFirstResult(start);
            q.setMaxResults(pageSize);
        }

        return q.getResultList();
    }

    @Override
    public List<Article> getArticles(Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> query = builder.createQuery(Article.class);
        Root<Article> root = query.from(Article.class);
        query.select(root);

        // Build predicates (ensure you use jakarta.persistence.criteria.Predicate)
        List<Predicate> predicates = buildSearchPredicates(params, builder, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }
        query.orderBy(builder.desc(root.get("publishedDate")));

        jakarta.persistence.TypedQuery<Article> q = entityManager.createQuery(query);

        // Pagination
        if (params != null) {
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "10"));
            // Ensure page is at least 1 to avoid negative start value
            page = Math.max(1, page);
            int start = (page - 1) * pageSize;
            q.setFirstResult(start);
            q.setMaxResults(pageSize);
        }

        return q.getResultList();
    }

    @Override
    public Article updateArticle(Article article) {
        Article existingArticle = entityManager.find(Article.class, article.getId());
        if (existingArticle != null) {
            entityManager.merge(article);
        }
        return existingArticle;
    }

    @Override
    public boolean deleteArticle(Long id) {
        Article article = entityManager.find(Article.class, id);
        if (article != null) {
            article.setDeletedAt(new Date());
            article.setIsActive(false);
            entityManager.merge(article);
            return true;
        }
        return false;
    }

    @Override
    public long countArticles() {
        String hql = "SELECT COUNT(a) FROM Article a";
        return entityManager.createQuery(hql, Long.class).getSingleResult();
    }

    @Override
    public long countSearchArticles(Map<String, String> filters) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Article> root = query.from(Article.class);
        query.select(builder.count(root));

        // Build predicates
        List<Predicate> predicates = buildSearchPredicates(filters, builder, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<Predicate> buildSearchPredicates(Map<String, String> filters,CriteriaBuilder builder, Root<Article> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filters != null) {
            if(filters.containsKey("title")) {
                String title = filters.get("title");
                predicates.add(builder.like(
                        builder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                ));
            }
            if (filters.containsKey("categoryId") && !filters.get("categoryId").isEmpty()) {
                String categoryIdStr = filters.get("categoryId");
                if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                    Long categoryId = Long.parseLong(categoryIdStr);
                    predicates.add(builder.equal(root.get("categoryId").get("id"), categoryId));
                }
            }

            if (filters.containsKey("author") && !filters.get("author").isEmpty()) {
                String author = filters.get("author");
                predicates.add(builder.like(
                        builder.lower(root.get("author")),
                        "%" + author.toLowerCase() + "%"
                ));
            }

            if(filters.containsKey("slug")) {
                String slug = filters.get("slug");
                predicates.add(builder.equal(
                        builder.lower(root.get("slug")),
                        slug.toLowerCase()
                ));
            }

            if (filters.containsKey("isActive")) {
                Boolean isActive = Boolean.parseBoolean(filters.get("isActive"));
                predicates.add(builder.equal(root.get("isActive"), isActive));
            }

            if (filters.containsKey("publishedDate")) {
                String publishedDate = filters.get("publishedDate");
                predicates.add(builder.equal(root.get("publishedDate"), publishedDate));
            }

            if (filters.containsKey("generatorId")) {
                String generatorIdStr = filters.get("generatorId");
                if (generatorIdStr != null && !generatorIdStr.isEmpty()) {
                    Long generatorId = Long.parseLong(generatorIdStr);
                    predicates.add(builder.equal(root.get("generatorId").get("id"), generatorId));
                }
            }

            if(filters.containsKey("fromDate") && filters.containsKey("toDate")) {
                String fromDate = filters.get("fromDate");
                String toDate = filters.get("toDate");
                predicates.add(builder.between(
                        root.get("publishedDate"),
                        java.sql.Date.valueOf(fromDate),
                        java.sql.Date.valueOf(toDate)
                ));
            }

            if(filters.containsKey("publishedDates") && !filters.get("publishedDates").isEmpty()) {
                int days = Integer.parseInt(filters.get("publishedDates"));
                Calendar calendar = Calendar.getInstance();
                Date endDate = calendar.getTime();

                calendar.add(Calendar.DAY_OF_YEAR, -days);
                Date startDate = calendar.getTime();

                predicates.add(builder.between(
                    root.get("publishedDate"),
                    new java.sql.Date(startDate.getTime()),
                    new java.sql.Date(endDate.getTime())
                ));
            }

            if (filters.containsKey("status") && !filters.get("status").isEmpty()) {
                String status = filters.get("status");
                predicates.add(builder.equal(root.get("status"), status));
            }

        }

        return predicates;
    }

}
