package com.categoryProductApp.CategoryProduct.model;

import lombok.Data;

@Data
public class CategoryResponse {

	private Long categoryId;
	private String categoryName;
	private String childCategoryId;
	private String childCategoryName;

}
