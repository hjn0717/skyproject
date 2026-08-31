package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.Setmeal;

@Mapper
public interface SetMealDishMapper {
	@Select("select * from setmeal_dish where dish_id = #{id}")
	List<Setmeal> selectSetMealDIshByDishId(Long id);
}
