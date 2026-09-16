package com.sky.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        return setmealMapper.list(setmeal);
    }

    @Override
    public List<DishItemVO> getDishItemBySetmealId(Long id) {
        return setMealDishMapper.getDishItemBySetmealId(id);
    }
}