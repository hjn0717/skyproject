package com.sky.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
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
	@PostMapping
	@ApiOperation("添加分类")
	public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
		categoryService.addCategory(categoryDTO);
		return Result.success();
	}
	@PostMapping
	@ApiOperation("修改分类")
	public Result updateCategory(@RequestBody CategoryDTO categoryDTO) {
		categoryService.updateCategory(categoryDTO);
		return Result.success();
	}
	@PostMapping("/status")
    @ApiOperation("启用和禁用分类")
    public Result updateStatus(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据id删除分类")
    public Result deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据类型查询分类列表")
    public Result<List<Category>> listByType(@RequestParam Integer type) {
        List<Category> list = categoryService.listByType(type);
        return Result.success(list);
    }
}
