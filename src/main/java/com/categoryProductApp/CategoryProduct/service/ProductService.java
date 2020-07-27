package com.categoryProductApp.CategoryProduct.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.categoryProductApp.CategoryProduct.model.ProductResponse;
import com.categoryProductApp.CategoryProduct.model.Products;

public interface ProductService {

	ResponseEntity<Object> createProduct(Products product);

	List<ProductResponse> getCategoryWiseProduct(Long categoryId);

	Products getProduct(Long id);

	ResponseEntity<Object> updateProduct(Long productId, Products product);

}
