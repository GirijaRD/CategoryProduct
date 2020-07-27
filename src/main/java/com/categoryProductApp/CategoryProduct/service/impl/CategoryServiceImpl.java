package com.categoryProductApp.CategoryProduct.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.categoryProductApp.CategoryProduct.DAO.CategoryDao;
import com.categoryProductApp.CategoryProduct.DAO.ProductDao;
import com.categoryProductApp.CategoryProduct.model.Category;
import com.categoryProductApp.CategoryProduct.model.CategoryResponse;
import com.categoryProductApp.CategoryProduct.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	CategoryDao categoryDao;
	@Autowired
	ProductDao productDao;
	
	@Override
	public ResponseEntity<Object> createCategoryWithChild(Category category) {
		// TODO Auto-generated method stub
		return categoryDao.insertCategoryWithChild(category);
	}
	@Override
	public Category getCategoryById(Long id) {
		// TODO Auto-generated method stub
		return categoryDao.getCategoryById(id);
	}
	@Override
	public List<CategoryResponse> getAllCategoriesWithChildren() {
		// TODO Auto-generated method stub
		return categoryDao.getAllCategoriesWithChildren();
	}
	@Override
	public ResponseEntity<Object> createMultipleCategory(List<Category> category) {
		// TODO Auto-generated method stub
		return categoryDao.insertMultipleCategory(category);
	}
	
	
}
