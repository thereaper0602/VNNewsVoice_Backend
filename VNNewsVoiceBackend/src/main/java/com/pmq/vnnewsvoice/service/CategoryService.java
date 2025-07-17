package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.Category;

import java.util.Optional;

public interface CategoryService {
    Category addCategory(Category category);

    Optional<Category> getCategoryById(Long id);

    Category updateCategory(Category category);

    boolean deleteCategory(Long id);

    long countCategories();
}
