package com.sky.service;

import java.util.List;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

public interface CategoryService {

	PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
	void addCategory(CategoryDTO categoryDTO);
	void updateCategory(CategoryDTO categoryDTO);
	void deleteCategory(Long id); 
    List<Category> listByType(Integer type);
}
