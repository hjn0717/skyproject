package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.Setmeal;

@Mapper
public interface SetmealMapper {

    List<Setmeal> list(Setmeal setmeal);
    
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);
}