package com.sky.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
public class DishController {
	@Autowired
	private DishService dishService;

	/**

	* 新增菜品接口

	* @param dishDTO

	* @return

	*/

	@ApiOperation("新增菜品接口")
	@PostMapping
	public Result addDish(@RequestBody DishDTO dishDTO){

		//调用新增菜品的方法
	
		dishService.addDish(dishDTO);
	
		return Result.success();
	}
	
	@ApiOperation("菜品分页查询")
	@GetMapping("/page")
	public Result<PageResult> pageDish(DishPageQueryDTO dishPageQueryDTO) {
	    PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
	    return Result.success(pageResult);
	}
	
	@ApiOperation("通过ID获取菜品信息") 
	@GetMapping("/{id}")
	public Result<DishVO> getDishByDishId(@PathVariable Long id) {
	    DishVO dishVO = dishService.getDishByDishId(id);
	    return Result.success(dishVO);
	}
	
	@ApiOperation("更新菜品信息") 
	@PutMapping
	public Result updateDish(@RequestBody DishDTO dishDTO) {
	    dishService.updateDish(dishDTO);
	    return Result.success();
	}
	
	@ApiOperation("删除菜品信息") 
	@DeleteMapping
	public Result deleteDish(@RequestBody @RequestParam List<Long> ids) {
	    dishService.deleteDishByIds(ids);
	    return Result.success();
	}
}
