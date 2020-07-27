# CategoryProduct

The Task is building a RESTful APIs from scratch using any of Java/Python/NodeJS
•	The Entities are "Category" and "Product".
•	The Category can have multiple child categories.
•	The Child category can have further child categories.
•	The Category can have multiple products and a product can have multiple categories. The Entities must get saved in the database (could be MongoDB/ PSQL/ MySQL) and be retrieved via POST and GET Methods respectively.
Business Logic:
needs to design a data model and create APIs to -
1.	Add a category
2.	Add Product mapped to a category or categories.
3.	Get all categories with all its child categories mapped to it.
Note : Each category object should look something like this {id : 1 , child_categories:[], ...}
4.	Get all products by a category.
5.	Update product details (name, price etc.)
