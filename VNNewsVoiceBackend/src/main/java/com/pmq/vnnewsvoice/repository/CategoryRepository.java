package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.Category;
import jakarta.persistence.criteria.Predicate;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CategoryRepository {

    Category addCategory(Category category);

    Optional<Category> getCategoryById(Long id);

    Category updateCategory(Category category);

    List<Category> getCategories(Map<String, String> params);

    boolean deleteCategory(Long id);

    long countCategories();

    long countSearchCategories(Map<String, String> filters);

    List<Predicate> buildSearchPredicates(Map<String, String> filters, jakarta.persistence.criteria.CriteriaBuilder builder, jakarta.persistence.criteria.Root<Category> root);
}
