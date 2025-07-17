package com.pmq.vnnewsvoice.service.impl;


import com.pmq.vnnewsvoice.pojo.Category;
import com.pmq.vnnewsvoice.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public Category addCategory(Category category) {
        return null;
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return Optional.empty();
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public boolean deleteCategory(Long id) {
        return false;
    }

    @Override
    public long countCategories() {
        return 0;
    }
}
