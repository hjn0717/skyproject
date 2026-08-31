package com.sky.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.sky.autoZhuJie.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;

public interface DishMapper {
	@AutoFill(OperationType.INSERT)
	void addDish(Dish dish);
	
	Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);  
	
	@Select("select * from dish where id = #{id}")
	Dish selectDishInfoById(Long id);
	
	Dish selectDishInfoByDishId(Long id);
	
	@AutoFill(OperationType.UPDATE)
	void updateDish(Dish dish);
	
	@Delete("delete from dish_flavor where dish_id = #{id}")
	void deleteFlavorByDishId(Long id);
}
