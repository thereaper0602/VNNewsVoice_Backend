package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Category;
import com.pmq.vnnewsvoice.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Category addCategory(Category category) {
        entityManager.persist(category);
        return category;
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return Optional.ofNullable(entityManager.find(Category.class, id));
    }

    @Override
    public Category updateCategory(Category category) {
        Category existingCategory = entityManager.find(Category.class, category.getId());
        if (existingCategory != null) {
            existingCategory.setName(category.getName());
            existingCategory.setDescription(category.getDescription());
            entityManager.merge(existingCategory);
            return existingCategory;
        }
        return null;
    }

    @Override
    public List<Category> getCategories(Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        query.select(root);

        // Apply search predicates if any
        List<Predicate> predicates = buildSearchPredicates(params, builder, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public boolean deleteCategory(Long id) {
        // soft delete logic
        Category category = entityManager.find(Category.class, id);
        if (category != null) {
            category.setIsActive(false); // Assuming isActive is a field in Category for soft delete
            entityManager.merge(category);
            return true;
        }
        return false;
    }

    @Override
    public long countCategories() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Category> root = query.from(Category.class);
        query.select(builder.count(root));

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public long countSearchCategories(Map<String, String> filters) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Category> root = query.from(Category.class);
        query.select(builder.count(root));

        // Build predicates
        List<Predicate> predicates = buildSearchPredicates(filters, builder, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<Predicate> buildSearchPredicates(Map<String, String> filters, CriteriaBuilder builder, Root<Category> root) {
        List<Predicate> predicates = new ArrayList<>();

        // Example filter: if filters contain a "name" key, add a predicate for it
        if (filters.containsKey("name")) {
            String name = filters.get("name");
            predicates.add(builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (filters.containsKey("isActive")) {
            boolean isActive = Boolean.parseBoolean(filters.get("is_active"));
            predicates.add(builder.equal(root.get("is_active"), isActive));
        }

        return predicates;
    }
}
