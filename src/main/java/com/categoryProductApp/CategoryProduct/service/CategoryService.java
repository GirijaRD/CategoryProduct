package com.categoryProductApp.CategoryProduct.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.categoryProductApp.CategoryProduct.model.Category;
import com.categoryProductApp.CategoryProduct.model.CategoryResponse;

public interface CategoryService {

	ResponseEntity<Object> createCategoryWithChild(Category category);

	ResponseEntity<Object> createMultipleCategory(List<Category> category);

	Category getCategoryById(Long id);

	List<CategoryResponse> getAllCategoriesWithChildren();
	
	

}
