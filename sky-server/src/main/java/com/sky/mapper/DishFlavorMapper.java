package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.DishFlavor;

@Mapper

public interface DishFlavorMapper {

	List<DishFlavor> selectFlavorByDishId(Long id);
	
	@Select("select * from dish_flavor where dish_id = #{id}")
	void addDishFlavorBatch(List<DishFlavor> flavorList);
	
	@Delete("delete from dish_flavor where dish_id = #{id}")
	void deleteFlavorByDishId(Long id);
	
} 
