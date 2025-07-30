package com.pmq.vnnewsvoice.service.impl;


import com.pmq.vnnewsvoice.pojo.Category;
import com.pmq.vnnewsvoice.repository.CategoryRepository;
import com.pmq.vnnewsvoice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.addCategory(category);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return categoryRepository.getCategoryById(id);
    }

    @Override
    public List<Category> getCategories(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return categoryRepository.getCategories(Map.of());
        }
        return categoryRepository.getCategories(params);
    }

    @Override
    public Category updateCategory(Category category) {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("Invalid category data");
        }
        return categoryRepository.updateCategory(category);
    }

    @Override
    public boolean deleteCategory(Long id) {
        if (id == null) {
            return false;
        }
        return categoryRepository.deleteCategory(id);
    }

    @Override
    public long countCategories() {
        return categoryRepository.countCategories();
    }

    @Override
    public long countSearchCategories(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return categoryRepository.countCategories();
        }
        return categoryRepository.countSearchCategories(filters);
    }
}
