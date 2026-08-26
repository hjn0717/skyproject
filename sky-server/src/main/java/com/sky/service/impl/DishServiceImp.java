package com.sky.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;

public class DishServiceImp implements DishService{
	@Autowired
	private DishMapper dishMapper;
	private DishFlavorMapper dishFlavorMapper;
	@Override
    public void addDish(DishDTO dishDTO) {
		Dish dish = new Dish();
		BeanUtils.copyProperties(dishDTO, dish);
		dishMapper.addDish(dish);
		Long dishId = dish.getId();
		List<DishFlavor> flavorList = dishDTO.getFlavors();
		if (flavorList != null && !flavorList.isEmpty()) {
		    for (DishFlavor dishFlavor : flavorList) {
		        dishFlavor.setDishId(dishId);
		    }
		    dishFlavorMapper.addDishFlavorBatch(flavorList);
		}
	}
}
