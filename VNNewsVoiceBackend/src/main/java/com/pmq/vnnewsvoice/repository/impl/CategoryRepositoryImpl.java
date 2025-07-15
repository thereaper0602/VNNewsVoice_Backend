package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Category;
import com.pmq.vnnewsvoice.repository.CategoryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {
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
