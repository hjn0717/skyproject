package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.vo.DishItemVO;

@Mapper
public interface SetMealDishMapper {
	@Select("select * from setmeal_dish where dish_id = #{id}")
	List<Setmeal> selectSetMealDIshByDishId(Long id);
	
	@Select("select sd.name, sd.copies, d.image, d.description " +"from setmeal_dish sd left join dish d on sd.dish_id = d.id " + "where sd.setmeal_id = #{setmealId}")
	List<DishItemVO> getDishItemBySetmealId(Long setmealId);
	
	void addSetmealDishBatch(List<SetmealDish> setmealDishList);

    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getSetmealDishBySetmealId(Long setmealId);

    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteSetmealDishBySetmealId(Long setmealId);
}
