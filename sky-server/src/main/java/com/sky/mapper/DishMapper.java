package com.sky.mapper;

import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;

import com.sky.autoZhuJie.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;

public interface DishMapper {
	@AutoFill(OperationType.INSERT)
	void addDish(Dish dish);
}
