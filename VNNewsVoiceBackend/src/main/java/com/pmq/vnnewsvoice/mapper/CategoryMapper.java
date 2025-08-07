package com.pmq.vnnewsvoice.mapper;

import com.pmq.vnnewsvoice.dto.CategoryDto;
import com.pmq.vnnewsvoice.pojo.Category;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CategoryMapper implements Serializable {
    public CategoryDto toDto(Category category){
        if(category == null){
            return null;
        }
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categoryDto.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setIsActive(category.getIsActive());
        return categoryDto;
    }

}
