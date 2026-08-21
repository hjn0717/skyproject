package com.sky.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
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

	@Override
	public void addCategory(CategoryDTO categoryDTO) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDTO, category);
		category.setStatus(1);
		category.setCreateTime(LocalDateTime.now());
		category.setCreateUser(BaseContext.getCurrentId());
		category.setUpdateTime(LocalDateTime.now());
		category.setUpdateUser(BaseContext.getCurrentId());
		categoryMapper.addCategory(category);
	}

	@Override
	public void updateCategory(CategoryDTO categoryDTO) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDTO, category);
		category.setUpdateTime(LocalDateTime.now());
		category.setUpdateUser(BaseContext.getCurrentId());
		categoryMapper.updateCategory(category);
	}
	@Override
    public void deleteCategory(Long id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public List<Category> listByType(Integer type) {
        return categoryMapper.listByType(type);
    }
	
}
