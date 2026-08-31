package com.sky.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;

public class DishServiceImp implements DishService{
	
	@Autowired
	private DishMapper dishMapper;
	private DishFlavorMapper dishFlavorMapper;
	private SetMealDishMapper setMealDishMapper;
	
	@Override
	@Transactional
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
	
	@Override
	public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
	    PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
	    Page<DishVO> dishVOPage = dishMapper.pageQuery(dishPageQueryDTO);
	    return new PageResult(dishVOPage.getTotal(),dishVOPage.getResult());
	}
	
	@Override
	public DishVO getDishByDishId(Long id) {
	    //通过dishId获取菜品信息
	    Dish dish = dishMapper.selectDishInfoByDishId(id);
	    List<DishFlavor> dishFlavorList = dishFlavorMapper.selectFlavorByDishId(dish.getId());
	    DishVO dishVO = new DishVO();
	    BeanUtils.copyProperties(dish, dishVO);
	    if (dishFlavorList != null && dishFlavorList.size() > 0) {
	    	dishVO.setFlavors(dishFlavorList);
	    }
	    return dishVO;
	}
	
	@Override
	@Transactional
	public void updateDish(DishDTO dishDTO) {
	    Dish dish = new Dish();
	    BeanUtils.copyProperties(dishDTO, dish);
	    dishMapper.updateDish(dish);
	    dishFlavorMapper.deleteFlavorByDishId(dishDTO.getId());
	    List<DishFlavor> flavorList = dishDTO.getFlavors();
	    if (flavorList != null && flavorList.size() > 0) {
	    	dishFlavorMapper.addDishFlavorBatch(flavorList);
	    }
	}

	@Transactional
	public void deleteDishByDishIds(List<Long> ids) {
	    if (ids != null && ids.size() > 0) {
		    for (Long id : ids) {
			    Dish dish = dishMapper.selectDishInfoById(id);
			    if (dish != null) {
				    if (dish.getStatus().equals(StatusConstant.ENABLE)) {
				    	throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
				    }
			    }
		    }
		    for (Long id : ids) {
		        List<Setmeal> setmealList = setMealDishMapper.selectSetMealDIshByDishId(id);
		        if (setmealList != null && setmealList.size() > 0) {
		        throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
		        }
		    }
		    for (Long id : ids) {
		        dishMapper.deleteFlavorByDishId(id);
		        dishFlavorMapper.deleteFlavorByDishId(id);
		    }
	    }
	}
}
