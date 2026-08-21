package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;

public interface CategoryMapper {

	Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
	
	@Insert("insert into category (id,type,name,sort,status,create_time,update_time,create_user,update_user)"+"values(#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
	void addCategory(Category category);

	void updateCategory(Category category);
	
	void deleteById(Long id);

	List<Category> listByType(Integer type);
}
