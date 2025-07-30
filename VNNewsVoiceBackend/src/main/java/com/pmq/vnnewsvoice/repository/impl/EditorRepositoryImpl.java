package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Editor;
import com.pmq.vnnewsvoice.repository.EditorRepository;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
@Transactional
public class EditorRepositoryImpl implements EditorRepository {

    @PersistenceContext
    private jakarta.persistence.EntityManager entityManager;

    @Override
    public Editor addEditor(Editor editor) {
        entityManager.persist(editor);
        return editor;
    }

    @Override
    public Optional<Editor> getEditorById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(entityManager.find(Editor.class, id));
    }

    @Override
    public Optional<Editor> getEditorByUserId(Long userId) {
        if (userId == null) {
            return Optional.empty();
        }
        String hql = "FROM Editor e WHERE e.userId.id = :userId";
        List<Editor> editors = entityManager.createQuery(hql, Editor.class)
                .setParameter("userId", userId)
                .getResultList();
        return editors.isEmpty() ? Optional.empty() : Optional.of(editors.get(0));
    }

    @Override
    public List<Editor> getEditors(Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        jakarta.persistence.criteria.CriteriaQuery<Editor> query = builder.createQuery(Editor.class);
        Root<Editor> root = query.from(Editor.class);
        query.select(root);

        // Apply search predicates if any
        List<Predicate> predicates = buildSearchPredicates(params, builder, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(query).getResultList();
    }


    @Override
    public Editor updateEditor(Editor editor) {
        if (editor == null || editor.getId() == null) {
            throw new IllegalArgumentException("Editor or Editor ID cannot be null");
        }
        Editor existingEditor = entityManager.find(Editor.class, editor.getId());
        if (existingEditor != null) {
            entityManager.merge(existingEditor);
            return existingEditor;
        }
        return null;
    }

    @Override
    public boolean deleteEditor(Long id) {
        if (id == null) {
            return false;
        }
        Editor editor = entityManager.find(Editor.class, id);
        if (editor != null) {
            entityManager.remove(editor);
            return true;
        }
        return false;
    }

    @Override
    public long countEditors() {
        String hql = "SELECT COUNT(e) FROM Editor e";
        return entityManager.createQuery(hql, Long.class).getSingleResult();
    }

    @Override
    public long countSearchEditors(Map<String, String> filters) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        jakarta.persistence.criteria.CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Editor> root = query.from(Editor.class);
        query.select(builder.count(root));

        // Build predicates
        List<Predicate> predicates = buildSearchPredicates(filters, builder, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<Predicate> buildSearchPredicates(Map<String, String> filters, CriteriaBuilder builder, Root<Editor> root) {
        List<Predicate> predicates = new java.util.ArrayList<>();
        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("username")){
                String keyword = "%" + filters.get("username").replace("_", "\\_").replace("%", "\\%") + "%";
                predicates.add(builder.like(root.get("userId").get("username"), keyword, '\\'));
            }
            if (filters.containsKey("email")) {
                String keyword = "%" + filters.get("email").replace("_", "\\_").replace("%", "\\%") + "%";
                predicates.add(builder.like(root.get("userId").get("email"), keyword, '\\'));
            }
        }
        return predicates;
    }
}
