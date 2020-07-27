package com.categoryProductApp.CategoryProduct.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.categoryProductApp.CategoryProduct.model.Category;
import com.categoryProductApp.CategoryProduct.model.CategoryResponse;
import com.categoryProductApp.CategoryProduct.service.CategoryService;

@RestController
@RequestMapping("category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	

	@PostMapping("/create")
	public ResponseEntity<Object> createCategoryWithChild(@RequestBody Category category) {
	    return categoryService.createCategoryWithChild(category);
	}
	@GetMapping("/details/{id}")
	public Category getCategoryById(@PathVariable Long id) {
		return categoryService.getCategoryById(id);
	}
	@GetMapping("/allCategoryWithChildren")
	public List<CategoryResponse> getCategoriesWithChildren() {
		return categoryService.getAllCategoriesWithChildren();
	}
	
}
