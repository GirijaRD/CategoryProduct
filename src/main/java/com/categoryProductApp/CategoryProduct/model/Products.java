package com.categoryProductApp.CategoryProduct.model;

import java.util.List;

import lombok.Data;

@Data
public class Products{

	private long productId;
	private String productName;
	
	private List<Long> categoryId;
	

}
