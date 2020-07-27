package com.categoryProductApp.CategoryProduct.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.categoryProductApp.CategoryProduct.DAO.CategoryDao;
import com.categoryProductApp.CategoryProduct.RowMapper.CategoryRowMapper;
import com.categoryProductApp.CategoryProduct.model.Category;
import com.categoryProductApp.CategoryProduct.model.CategoryResponse;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class CategoryDaoImpl implements CategoryDao {

	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Override
	public List<CategoryResponse> getAllCategoriesWithChildren() {
		// TODO Auto-generated method stub
		try {
			return jdbcTemplate.query(DaoConstant.FETCH_ALL_CATEGORIES_LIST_WITH_CHILDREN, new RowMapper<CategoryResponse>() {
	
				@Override
				public CategoryResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					CategoryResponse category = new CategoryResponse();
					category.setCategoryId(rs.getLong("parent_id"));
					category.setCategoryName(rs.getString("parent"));
					category.setChildCategoryId(rs.getString("child_id"));
					category.setChildCategoryName(rs.getString("child"));
					return category;
				}
				
			});
		}catch (Exception e) {
			log.error("Failed to fetch all categories with children",e.getMessage());
			return null;
		}
	}
	
	@Override
	public ResponseEntity<Object> insertMultipleCategory(List<Category> categoryList) {
		// TODO Auto-generated method stub
		
		int[] a = jdbcTemplate.batchUpdate(DaoConstant.INSERT_CATEGORY, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				Category category = categoryList.get(i);
				ps.setString(1, category.getCategoryName());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return categoryList.size();
			}
		});
		
		if (a!=null && a.length > 0) {
			return ResponseEntity.accepted().body(" Categories created Successfully ");
		}else {
			return ResponseEntity.accepted().body(" Failed to create Categories ");
		}
	}
	@Override
	public Category getCategoryById(Long id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(DaoConstant.FETCH_CATEGORY_BY_ID, new CategoryRowMapper(), new Object[] {id});
	}
	@Override
	@Transactional
	public ResponseEntity<Object> insertCategoryWithChild(Category category) {
		// TODO Auto-generated method stub
//		int categoryId = jdbcTemplate.update(DaoConstant.INSERT_CATEGORY, new Object[] {category.getCategoryName()});
		
		try {
				
			KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					// TODO Auto-generated method stub
					PreparedStatement ps = con.prepareStatement(DaoConstant.INSERT_CATEGORY_WITH_CHILD, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, category.getCategoryName());
					return ps;
				}
			}, generatedKeyHolder);
			
			long categoryId = generatedKeyHolder.getKey().longValue();
			if (categoryId > 0 ) {
				category.setCategoryId(categoryId);
				if (category.getParentCategoryId()!= null) {				
					
					Category cat = getCategoryTempById(category.getParentCategoryId());
					if (cat!=null) {
						Set<String> childCategoryIdSet = new TreeSet<>();
						if (cat.getChildCategoryIds() != null && !cat.getChildCategoryIds().isEmpty()) 
							childCategoryIdSet.addAll(Arrays.asList(cat.getChildCategoryIds().split(",")));
	//					
						childCategoryIdSet.add(category.getCategoryId().toString());
						String childCategoryIds = childCategoryIdSet.stream().collect(Collectors.joining(","));
						jdbcTemplate.update(DaoConstant.UPDATE_CHILD_CATEGORIES_DETAILS, 
								new Object[] {childCategoryIds, category.getParentCategoryId()});
						
						List<Category> parentCategoryList = getCategoryListByParentId(category.getParentCategoryId());
						if (parentCategoryList != null && !parentCategoryList.isEmpty()) {
							
							jdbcTemplate.batchUpdate(DaoConstant.UPDATE_CHILD_CATEGORIES_DETAILS, new BatchPreparedStatementSetter() {
								
								@Override
								public void setValues(PreparedStatement ps, int i) throws SQLException {
									// TODO Auto-generated method stub
									Category categ = parentCategoryList.get(i);
									
									Set<String> childCategoryIdSet1 =  new TreeSet<>(Arrays.asList(categ.getChildCategoryIds().split(",")));
	//								if (childCategoryIds != null && !childCategoryIds.isEmpty()) 
									childCategoryIdSet1.add(category.getCategoryId().toString());
									String childCategoryIds1 = childCategoryIdSet1.stream().collect(Collectors.joining(","));
									
									ps.setString(1, childCategoryIds1);
									ps.setLong(2, categ.getCategoryId());
								}
								
								@Override
								public int getBatchSize() {
									// TODO Auto-generated method stub
									return parentCategoryList.size();
								}
							});
							
						}
					}else {
						return ResponseEntity.notFound().build();
					}
				
				}
				log.info("Category created Successfully. ",category);
				return ResponseEntity.accepted().body(" Category created Successfully: "+category);
			}else {
				log.error("Failed to create category");
				return ResponseEntity.unprocessableEntity().body(" Failed to create Category ");
			}	
		}catch (Exception e) {
			log.error("Failed to create category",e.getMessage());
			return ResponseEntity.unprocessableEntity().body(e.getMessage());
		}
		
	}

	private List<Category> getCategoryListByParentId(Long parentCategoryId) {
		String FETCH_ALL_CATEGORIES_FOR_PARENT = "SELECT c.category_id parent_id, c.child_category_id\r\n" + 
				"FROM category p\r\n" + 
				"inner join category c on FIND_IN_SET(p.category_id, c.child_category_id) \r\n" + 
				"where p.category_id = ?\r\n" + 
				"order by parent_id";
		
		try {
		
		return jdbcTemplate.query(FETCH_ALL_CATEGORIES_FOR_PARENT, new Object[] {parentCategoryId},new RowMapper<Category>() {

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category category = new Category();
				category.setCategoryId(rs.getLong("parent_id"));
				category.setChildCategoryIds(rs.getString("child_category_id"));
				return category;
			}
			
		});
		}catch (Exception e) {
			log.error("Failed to fetch all categories by parent_id",e.getMessage());
			return null;
		}
	}

	public Category getCategoryTempById(Long id) {
		// TODO Auto-generated method stub
		try {
			return jdbcTemplate.queryForObject(DaoConstant.FETCH_CATEGORY_WITH_PARENT_BY_ID, new CategoryRowMapper(), new Object[] {id});
		} catch (Exception e) {
			log.error("Failed to get category by id",e.getMessage());
			return null;
		}
		
	}
}
