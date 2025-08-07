package com.pmq.vnnewsvoice.controller.restController;


import com.pmq.vnnewsvoice.dto.CategoryDto;
import com.pmq.vnnewsvoice.mapper.CategoryMapper;
import com.pmq.vnnewsvoice.pojo.Category;
import com.pmq.vnnewsvoice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiCategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getCategoryList(
            @RequestParam Map<String, String> params
    ){
        List<Category> categories = categoryService.getCategories(params);

        List<CategoryDto> categoryDtos = categories.stream().map(categoryMapper::toDto).toList();

        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }


}
