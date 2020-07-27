package com.categoryProductApp.CategoryProduct.DAO;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.categoryProductApp.CategoryProduct.model.ProductResponse;
import com.categoryProductApp.CategoryProduct.model.Products;

public interface ProductDao {

	long insertProduct(Products product);

	int[] insertCategoryWiseProduct(long productId, List<Long> categoryIdList);

	List<ProductResponse> getCategoryWiseProduct(Long categoryId);

	ResponseEntity<Object> updateProduct(Long productId, Products product);

}
