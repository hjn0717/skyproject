package com.sky.service;

import java.util.List;

import com.sky.entity.Setmeal;
import com.sky.vo.DishItemVO;

public interface SetmealService {

    List<Setmeal> list(Setmeal setmeal);
    List<DishItemVO> getDishItemBySetmealId(Long id);
}