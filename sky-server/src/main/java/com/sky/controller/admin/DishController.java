package com.sky.controller.admin;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private RedisTemplate redisTemplate;
    
    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    @ApiOperation("新增菜品接口")
    @PostMapping
    public Result addDish(@RequestBody DishDTO dishDTO) {
        dishService.addDish(dishDTO);
        cleanCache("dish_*");
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
        cleanCache("dish_*");
        return Result.success();
    }

    @ApiOperation("删除菜品信息")
    @DeleteMapping
    public Result deleteDish(@RequestBody @RequestParam List<Long> ids) {
        dishService.deleteDishByDishIds(ids);
        cleanCache("dish_*");
        return Result.success();
    }

    @ApiOperation("菜品起售停售")
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        dishService.startOrStop(status, id);
        cleanCache("dish_*");
        return Result.success();
    }
}
