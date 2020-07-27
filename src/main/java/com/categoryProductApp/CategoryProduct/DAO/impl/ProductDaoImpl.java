package com.categoryProductApp.CategoryProduct.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.categoryProductApp.CategoryProduct.DAO.ProductDao;
import com.categoryProductApp.CategoryProduct.model.ProductResponse;
import com.categoryProductApp.CategoryProduct.model.Products;

@Repository
public class ProductDaoImpl implements ProductDao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public long insertProduct(Products product) {
		// TODO Auto-generated method stub
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = con.prepareStatement(DaoConstant.INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, product.getProductName());
				return ps;
			}
		}, generatedKeyHolder);
		
		return generatedKeyHolder.getKey().longValue();
	}

	@Override
	public int[] insertCategoryWiseProduct(long productId, List<Long> categoryIdList) {
		// TODO Auto-generated method stub
		String param = categoryIdList.toString().substring(1, categoryIdList.toString().length()-1);
		String FETCH_CATEGORY_BY_IDS = "select category_id from category where category_id in ("+param+")";
		List<Long> categoryIdListTemp = jdbcTemplate.query(FETCH_CATEGORY_BY_IDS , new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return new Long(rs.getLong("category_id"));
			}
			
		});
		
		if (categoryIdListTemp != null && !categoryIdListTemp.isEmpty()) {
			int[] insertCnt = jdbcTemplate.batchUpdate(DaoConstant.INSERT_CATEGORY_PRODUCT, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setLong(1, categoryIdListTemp.get(i));
					ps.setLong(2, productId);
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return categoryIdListTemp.size();
				}
			});
			return insertCnt;
		}
		
		return null;
	
		
	}

	@Override
	public List<ProductResponse> getCategoryWiseProduct(Long categoryId) {
		// TODO Auto-generated method stub
		 return jdbcTemplate.query(DaoConstant.FETCH_CATEGORY_WISE_PRODUCT_LIST, new Object[] {categoryId}, new RowMapper<ProductResponse>() {

			@Override
			public ProductResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductResponse product = new ProductResponse();
				product.setProductId(rs.getLong("product_id"));
				product.setProductName(rs.getString("product_name"));
				return product;
			}
			
		});
	}

	@Override
	public ResponseEntity<Object> updateProduct(Long productId, Products product) {
		// TODO Auto-generated method stub
		
		int rowUpdated = jdbcTemplate.update(DaoConstant.UPDATE_PRODUCT_DETAILS, new Object[] {product.getProductName(), productId});
		if (rowUpdated > 0) {
			return ResponseEntity.accepted().body(" Product with category updated Successfully ");
		}else {
			return ResponseEntity.badRequest().body(" Failed to update Product  ");
		}
	}


}
