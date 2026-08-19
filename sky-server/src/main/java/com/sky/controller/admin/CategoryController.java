package com.sky.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "分类相关接口")
@RequestMapping("/admin/category")
public class CategoryController {
	
	private CategoryService categoryService;
	
	@GetMapping("/page")
	@ApiOperation("员工分页查询")
	public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
		PageResult pageResult= categoryService.pageQuery(categoryPageQueryDTO);
		return Result.success(pageResult);
	}
}
