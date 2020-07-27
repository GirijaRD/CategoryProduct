package com.categoryProductApp.CategoryProduct.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.categoryProductApp.CategoryProduct.model.Category;

public class CategoryRowMapper implements RowMapper<Category> {

	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Category category = new Category();
		category.setCategoryId(rs.getLong("category_id"));
		category.setCategoryName(rs.getString("category_name"));
		category.setChildCategoryIds(rs.getString("child_category_id"));
		return category;
	}

}
