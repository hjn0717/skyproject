package com.sky.service;

import java.util.List;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

public interface DishService {
	public void addDish(DishDTO dishDTO);
	PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
	DishVO getDishByDishId(Long id);
	void updateDish(DishDTO dishDTO);
	public void deleteDishByDishIds(List<Long> ids);
}
