package com.categoryProductApp.CategoryProduct.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.categoryProductApp.CategoryProduct.model.ProductResponse;
import com.categoryProductApp.CategoryProduct.model.Products;
import com.categoryProductApp.CategoryProduct.service.ProductService;

@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	ProductService productService;
	

	@PostMapping("/create")
	public ResponseEntity<Object> createUser(@RequestBody Products product) {
	    return productService.createProduct(product);
	}
	
	@GetMapping("/details/{categoryId}")
	public List<ProductResponse> getCategoryWiseProduct(@PathVariable Long categoryId) {
		List<ProductResponse> productList = productService.getCategoryWiseProduct(categoryId);
		return productList;
	}
	
	@GetMapping("/detail/{id}")
	public Products getProduct(@PathVariable Long id) {
		return productService.getProduct(id);
	}
	
	@PutMapping("/update/{productId}")
	public ResponseEntity<Object> updateProduct(@PathVariable Long productId, @RequestBody Products product) {
	    return productService.updateProduct(productId, product);
	}
}
