package com.categoryProductApp.CategoryProduct.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.categoryProductApp.CategoryProduct.DAO.CategoryDao;
import com.categoryProductApp.CategoryProduct.DAO.ProductDao;
import com.categoryProductApp.CategoryProduct.model.ProductResponse;
import com.categoryProductApp.CategoryProduct.model.Products;
import com.categoryProductApp.CategoryProduct.service.CategoryService;
import com.categoryProductApp.CategoryProduct.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	CategoryDao categoryDao;
	@Autowired
	ProductDao productDao;
	
	@Autowired
	CategoryService categoryService;
	
	@Override
	@Transactional
	public ResponseEntity<Object> createProduct(Products product) {
		// TODO Auto-generated method stub
		if (product!=null ) {
			long productId = productDao.insertProduct(product);
			if (productId > 0 && product.getCategoryId() != null && !product.getCategoryId().isEmpty()) {
				int[] cnt = productDao.insertCategoryWiseProduct(productId, product.getCategoryId());
				if (cnt!=null && cnt.length > 0) {
					return ResponseEntity.accepted().body(" Product with category created Successfully ");
				}else {
					return ResponseEntity.badRequest().body(" Failed to create Category wise Product  ");
				}
			}else {
				return ResponseEntity.badRequest().body(" Failed to create Product ");
			}
		}else {
			return ResponseEntity.badRequest().body(" Please provice valid Product details ");
		}
	}
	@Override
	public List<ProductResponse> getCategoryWiseProduct(Long categoryId) {
		// TODO Auto-generated method stub
		return productDao.getCategoryWiseProduct(categoryId);
	}
	@Override
	public Products getProduct(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResponseEntity<Object> updateProduct(Long productId, Products product) {
		// TODO Auto-generated method stub
		return productDao.updateProduct(productId, product);
	}

}
