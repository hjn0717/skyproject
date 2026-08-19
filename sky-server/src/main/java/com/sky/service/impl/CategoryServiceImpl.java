package com.sky.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;

public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) { 
		PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
		Page<Category> categoryPage=categoryMapper.pageQuery(categoryPageQueryDTO);
		return new PageResult(categoryPage.getTotal(),categoryPage.getResult());
	}
	
}
