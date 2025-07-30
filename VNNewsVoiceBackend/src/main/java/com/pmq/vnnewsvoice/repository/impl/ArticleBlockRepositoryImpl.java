package com.pmq.vnnewsvoice.repository.impl;


import com.pmq.vnnewsvoice.pojo.ArticleBlock;
import com.pmq.vnnewsvoice.repository.ArticleBlockRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ArticleBlockRepositoryImpl implements ArticleBlockRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ArticleBlock addArticleBlock(ArticleBlock articleBlock) {
        entityManager.persist(articleBlock);
        return articleBlock;
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockById(Long id) {
        return Optional.ofNullable(entityManager.find(ArticleBlock.class, id));
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockByArticleId(Long articleId) {
        String hql = "FROM ArticleBlock ab WHERE ab.articleId = :articleId";
        return entityManager.createQuery(hql, ArticleBlock.class)
                .setParameter("articleId", articleId)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockByArticleIdAndBlockId(Long articleId, Long blockId) {
        return Optional.empty();
    }

    @Override
    public Optional<ArticleBlock> getArticleBlockByArticleIdAndBlockType(Long articleId, String blockType) {
        return Optional.empty();
    }

    @Override
    public Optional<ArticleBlock> updateArticleBlock(ArticleBlock articleBlock) {
        ArticleBlock existingBlock = entityManager.find(ArticleBlock.class, articleBlock.getId());
        if (existingBlock != null) {
            // Cập nhật các thuộc tính của existingBlock từ articleBlock
            if ("paragraph".equals(articleBlock.getType())) {
                existingBlock.setContent(articleBlock.getContent());
            } else if ("image".equals(articleBlock.getType())) {
                existingBlock.setSrc(articleBlock.getSrc());
                existingBlock.setAlt(articleBlock.getAlt());
                existingBlock.setCaption(articleBlock.getCaption());
            } else if ("heading".equals(articleBlock.getType())) {
                existingBlock.setText(articleBlock.getText());
                existingBlock.setTag(articleBlock.getTag());
            }
            
            // Merge đối tượng đã được cập nhật
            entityManager.merge(existingBlock);
            return Optional.of(existingBlock);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteArticleBlock(Long id) {
        ArticleBlock articleBlock = entityManager.find(ArticleBlock.class, id);
        if (articleBlock != null) {
            entityManager.remove(articleBlock);
            return true;
        }
        return false;
    }

    @Override
    public long countArticleBlocks() {
        return 0;
    }

    @Override
    public long countArticleBlocksByArticleId(Long articleId) {
        String hql = "SELECT COUNT(ab) FROM ArticleBlock ab WHERE ab.articleId = :articleId";
        return entityManager.createQuery(hql, Long.class)
                .setParameter("articleId", articleId)
                .getSingleResult();
    }

    @Override
    public long countArticleBlocksByBlockType(String blockType) {
        String hql = "SELECT COUNT(ab) FROM ArticleBlock ab WHERE ab.type = :blockType";
        return entityManager.createQuery(hql, Long.class)
                .setParameter("blockType", blockType)
                .getSingleResult();
    }

    @Override
    public long countArticleBlocksByArticleIdAndBlockType(Long articleId, String blockType) {
        return 0;
    }

    @Override
    public List<ArticleBlock> getArticleBlocksByArticleId(Long articleId) {
        String hql = "FROM ArticleBlock ab WHERE ab.articleId.id = :articleId order by ab.orderIndex ASC";
        return entityManager.createQuery(hql, ArticleBlock.class)
                .setParameter("articleId", articleId)
                .getResultList();
    }
}
