package com.categoryProductApp.CategoryProduct.DAO.impl;

public class DaoConstant {
	
	public static final String FETCH_ALL_CATEGORIES_LIST_WITH_CHILDREN = "SELECT c.category_id parent_id, c.category_name parent, p.category_id child_id, p.category_name child  \r\n" + 
																			"FROM category p\r\n" + 
																			"inner join category c on FIND_IN_SET(p.category_id, c.child_category_id) \r\n" + 
																			"order by parent_id, child_id";
	public static final String INSERT_CATEGORY = "insert into category(category_name) values (?)";
	public static final String FETCH_CATEGORY_BY_ID = "select category_id, category_name from category where category_id = ?";
	public static final String FETCH_CATEGORY_BY_NAME = "select category_id, category_name from category where category_name = ?";
	public static final String INSERT_PRODUCT = "insert into product(product_name) values (?)";
	public static final String INSERT_CATEGORY_PRODUCT = "insert into category_product(category_id,product_id) values (?, ?)";
	
	public static final String FETCH_CATEGORY_WISE_PRODUCT_LIST = "select p.product_id, p.product_name from product p\r\n" + 
																	"inner join category_product cp on p.product_id = cp.product_id\r\n" + 
																	"inner join category c on cp.category_id = c.category_id\r\n" + 
																	"where c.category_id = ?";
	public static final String UPDATE_PRODUCT_DETAILS = "UPDATE product SET product_name=? WHERE product_id=?";
	public static final String INSERT_CATEGORY_WITH_CHILD = "insert into category(category_name) values (?)";
	public static final String UPDATE_CHILD_CATEGORIES_DETAILS = "UPDATE category SET child_category_id=? WHERE category_id=?";
	public static final String FETCH_CATEGORY_WITH_PARENT_BY_ID = "select category_id, category_name,child_category_id from category where category_id = ?";
	
}
