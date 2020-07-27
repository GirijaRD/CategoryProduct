package com.categoryProductApp.CategoryProduct.DAO;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.categoryProductApp.CategoryProduct.model.Category;
import com.categoryProductApp.CategoryProduct.model.CategoryResponse;

public interface CategoryDao {

	ResponseEntity<Object> insertCategoryWithChild(Category category);

	ResponseEntity<Object> insertMultipleCategory(List<Category> categoryList);

	Category getCategoryById(Long id);

	List<CategoryResponse> getAllCategoriesWithChildren();

}
