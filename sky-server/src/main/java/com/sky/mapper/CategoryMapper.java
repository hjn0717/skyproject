package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;

public interface CategoryMapper {

	Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
	
}
