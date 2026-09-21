package com.sky.service;

import java.util.List;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

public interface SetmealService {

    List<Setmeal> list(Setmeal setmeal);
    List<DishItemVO> getDishItemBySetmealId(Long id);
    void addSetmeal(SetmealDTO setmealDTO);
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    void deleteSetmealByIds(List<Long> ids);
    SetmealVO getSetmealById(Long id);
    void updateSetmeal(SetmealDTO setmealDTO);
    void startOrStop(Integer status, Long id);
}

