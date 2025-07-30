package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CategoryService {
    Category addCategory(Category category);

    Optional<Category> getCategoryById(Long id);

    List<Category> getCategories(Map<String, String> params);

    Category updateCategory(Category category);

    boolean deleteCategory(Long id);

    long countCategories();

    long countSearchCategories(Map<String, String> filters);
}
