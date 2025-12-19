Here is a professional `README.md` for the GameSlopAPI project, based on the provided source code.

---

# GameSlopAPI - E-Commerce Backend

## Project Overview

GameSlopAPI is a comprehensive RESTful API built with Java and Spring Boot. It serves as the backend for an e-commerce platform (specifically modeled for a video game store, though adaptable for other domains like clothing or groceries). The application manages product inventories, categories, user profiles, and persistent shopping carts, utilizing a MySQL database for data storage.

## Tech Stack

* **Java**: Version 17
* **Framework**: Spring Boot 2.7.3
* **Database**: MySQL 8.0
* **Security**: Spring Security & JSON Web Tokens (JWT)
* **Data Access**: JDBC with `PreparedStatement`
* **Build Tool**: Maven

## Features

* **User Management**: Registration and login with password hashing (BCrypt).
* **Product Catalog**: Full browsing capability with advanced filtering options.
* **Shopping Cart**: Database-persisted shopping carts that survive user sessions.
* **Role-Based Access Control**: Granular permissions distinguishing between standard `USER` and `ADMIN` roles.

## API Endpoints

### Products

The `ProductsController` allows for retrieving and managing inventory.

* `GET /products`: Search for products with optional filters.
* `GET /products/{id}`: Retrieve a specific product.
* `POST /products`: Create a new product (**Admin Only**).
* `PUT /products/{id}`: Update an existing product (**Admin Only**).
* `DELETE /products/{id}`: Remove a product (**Admin Only**).

### Categories

* `GET /categories`: List all categories.
* `GET /categories/{id}`: Get a specific category.
* `GET /categories/{id}/products`: Get all products associated with a specific category ID.
* `POST /categories`: Create a category (**Admin Only**).

### Shopping Cart

* `GET /cart`: Retrieve the current user's cart.
* `POST /cart/products/{productId}`: Add an item to the cart.
* `PUT /cart/products/{productId}`: Update the quantity of an item.
* `DELETE /cart`: Clear the entire cart.

## Database Design

The database schema includes the following primary tables:

* `users`: Stores credentials and roles.
* `profiles`: Stores user demographic data (address, phone, etc.).
* `products`: Stores inventory data including stock and price.
* `categories`: Grouping mechanism for products.
* `shopping_cart`: Join table between `users` and `products` with a `quantity` field.
* `orders` & `order_line_items`: Architecture for processing completed purchases.

![Database Schema](src/main/resources/GameSlopAPI-EER.png)
### Authentication

* `POST /login`: Authenticates a user and returns a JWT.
* `POST /register`: Registers a new user and creates an associated profile.

## Product Search

The product search functionality supports dynamic SQL generation based on the parameters provided.

* **Endpoint**: `GET /products`
* **Query Parameters**:
* `cat` (Integer): Filter by Category ID.
* `minPrice` (BigDecimal): Filter by minimum price.
* `maxPrice` (BigDecimal): Filter by maximum price.
* `subCategory` (String): Filter by sub-category name.


* **Logic**: The backend sets default values (e.g., `-1` for IDs, empty strings for text) to handle optional parameters effectively within the SQL `WHERE` clause.

## Authentication & Authorization

Security is implemented using `Spring Security` and stateless `JWT` authentication.


**Access**:
* Public: `GET` requests on Products and Categories.
* Authenticated (`ROLE_USER` or `ROLE_ADMIN`): Shopping cart operations.
* Admin (`ROLE_ADMIN`): `POST`, `PUT`, `DELETE` operations on Products and Categories.




## Bugs Fixed

* **Product Search filtering**: Adjusted the SQL logic in `MySqlProductDao` to correctly handle combinations of min/max price when one or both are missing, preventing SQL syntax errors or incorrect result sets.

## Future Improvements

* **Order Checkout**: Implement the `OrdersController` to move items from the `shopping_cart` table to `orders` and `order_line_items`.
