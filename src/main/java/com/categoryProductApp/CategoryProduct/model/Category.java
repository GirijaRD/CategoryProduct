package com.categoryProductApp.CategoryProduct.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Category implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long categoryId;
	private String categoryName;
	private Long parentCategoryId;
	private String childCategoryIds;

}
