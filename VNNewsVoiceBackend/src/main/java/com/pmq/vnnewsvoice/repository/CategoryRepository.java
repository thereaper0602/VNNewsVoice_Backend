package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.Category;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CategoryRepository {
    Category addCategory(Category category);

    Optional<Category> getCategoryById(Long id);

    Category updateCategory(Category category);

    boolean deleteCategory(Long id);

    long countCategories();
}
