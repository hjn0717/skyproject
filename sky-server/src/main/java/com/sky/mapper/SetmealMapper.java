package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.sky.autoZhuJie.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;

@Mapper
public interface SetmealMapper {

    List<Setmeal> list(Setmeal setmeal);
    
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);
    
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @AutoFill(OperationType.UPDATE)
    void updateSetmeal(Setmeal setmeal);

    void deleteByIds(List<Long> ids);
}