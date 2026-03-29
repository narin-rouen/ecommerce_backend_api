
# 🛒 Online E-commerce Backend API

A **RESTful backend API** for an **Online E-commerce Application** built with **Java Spring Boot**.  
This system allows **admins to manage products, categories, orders, and payments**, while **users can browse products, manage carts, and purchase items**.

The project follows a **clean layered architecture** and implements modern backend practices such as **JWT authentication, password hashing, validation, pagination, filtering, and Stripe payment integration**.

---

# 📌 Problem Statement

This application provides a backend system for an online store where:

- **Admins** can manage products, categories, subcategories, orders, and payments.
- **Users** can browse products, add items to cart, place orders, and track purchase history.

The goal is to build a **scalable and secure REST API** that supports common e-commerce functionality.

---

# 🚀 Features

## 👨‍💼 Admin Features
- Create, update, delete, and view **products**
- View all products with:
  - Pagination
  - Search
  - Filtering
  - Sorting
- Manage **categories and subcategories**
- View **all orders**
- View **order details**
- View **all payments**
- View **payment details**
- Create and update **product SKUs**

---

## 👤 User Features
- Browse products with SKU details
- Pagination, search, filtering, and sorting
- Add products to **cart**
- View cart items
- Remove items from cart
- Checkout with **Stripe payment**
- View **purchase history**
- Track **orders**
- Manage **addresses (CRUD)**

---

## 🔐 Shared Features
- Register account ✅
- Login ✅
- Logout
- Refresh token ✅
- View profile ✅
- JWT-based authentication

---

# ⚙️ Non-Functional Requirements

- 🔐 **JWT Stateless Authentication**
- 🔑 **Password Hashing**
- 🌐 **RESTful API Design**
- 🔄 **CRUD Operations**
- ⚠️ **Global Error Handling**
- ✅ **Request Validation**
- 💳 **Stripe Payment Integration**

---

# 🏗 System Architecture

The project follows a **Layered Architecture**:

```

Client
↓
Controller (REST API)
↓
Service Layer (Business Logic)
↓
Repository Layer
↓
Database

```

### Layers

- **Controller** → Handles HTTP requests
- **Service** → Contains business logic
- **Repository** → Database operations
- **Entity** → Database models
- **DTO**
  - Request DTO
  - Response DTO
  - Common DTO

---

# 🗄 Database Design

## USER
| Field | Description |
|------|-------------|
| id | Primary key |
| profile_file_name | User profile image |
| first_name | First name |
| last_name | Last name |
| username | Username |
| email | Email |
| password | Encrypted password |
| date_of_birth | DOB |
| role | USER / ADMIN |
| status | Active / Disabled |
| phone_number | Contact number |
| created_at | Created time |
| updated_at | Updated time |

---

## ADDRESS
| Field | Description |
|------|-------------|
| id | Address ID |
| user_id | Owner |
| title | Home/Office |
| line1 | Address line 1 |
| line2 | Address line 2 |
| country | Country |
| city | City |
| postal_code | Zip code |
| landmark | Nearby location |
| phone_number | Contact |
| created_at | Created time |
| updated_at | Updated time |

---

## CATEGORY
| Field | Description |
|------|-------------|
| id | Category ID |
| name | Category name |
| description | Category description |
| created_at | Created time |
| updated_at | Updated time |

---

## SUBCATEGORY
| Field | Description |
|------|-------------|
| id | Subcategory ID |
| category_id | Parent category |
| name | Name |
| description | Description |
| created_at | Created time |
| updated_at | Updated time |

---

## PRODUCT
| Field | Description |
|------|-------------|
| id | Product ID |
| name | Product name |
| description | Description |
| summary | Short summary |
| image | Product image |
| category_id | Category |
| created_at | Created time |
| updated_at | Updated time |

---

## PRODUCT ATTRIBUTE
| Field | Description |
|------|-------------|
| id | Attribute ID |
| type | SIZE / COLOR |
| value | Attribute value |
| created_at | Created time |
| updated_at | Updated time |

---

## PRODUCT SKU
| Field | Description |
|------|-------------|
| id | SKU ID |
| product_id | Product |
| size_attribute_id | Size |
| color_attribute_id | Color |
| sku | SKU code |
| price | Price |
| quantity | Stock |
| created_at | Created time |
| deleted_at | Soft delete |

---

## WISHLIST
| Field | Description |
|------|-------------|
| id | Wishlist ID |
| user_id | User |
| product_id | Product |
| created_at | Created time |
| deleted_at | Soft delete |

---

## CART
| Field | Description |
|------|-------------|
| id | Cart ID |
| user_id | User |
| total | Total price |
| created_at | Created time |
| updated_at | Updated time |

---

## CART ITEM
| Field | Description |
|------|-------------|
| id | Cart item ID |
| cart_id | Cart |
| product_id | Product |
| product_sku_id | SKU |
| quantity | Quantity |
| created_at | Created time |
| updated_at | Updated time |

---

## ORDER DETAILS
| Field | Description |
|------|-------------|
| id | Order ID |
| user_id | User |
| payment_id | Payment |
| total | Total price |
| created_at | Created time |
| updated_at | Updated time |

---

## ORDER ITEM
| Field | Description |
|------|-------------|
| id | Order item ID |
| order_id | Order |
| product_id | Product |
| product_sku_id | SKU |
| quantity | Quantity |
| created_at | Created time |
| updated_at | Updated time |

---

## PAYMENT DETAIL
| Field | Description |
|------|-------------|
| id | Payment ID |
| order_id | Order |
| amount | Paid amount |
| provider | Payment gateway |
| status | Payment status |
| created_at | Created time |
| updated_at | Updated time |

---

# 🔗 API Design

## 🔐 Authentication

| Endpoint | Method | Status |
|--------|--------|--------|
| `/api/auth/register` | POST | ✅ |
| `/api/auth/login` | POST | ✅ |
| `/api/auth/refresh` | POST | ✅ |
| `/api/auth/logout` | POST | |
| `/api/auth/me` | GET | ✅ |

---

## 👤 User

| Endpoint | Method | Description |
|--------|--------|-------------|
| `/api/user/me` | POST | Update user profile |

---

## 📦 Product (Admin)

| Endpoint | Method |
|--------|--------|
| `/api/admin/products` | GET |
| `/api/admin/products` | POST |
| `/api/admin/products/{id}` | GET |
| `/api/admin/products/{id}` | PUT |
| `/api/admin/products/{id}` | DELETE |
| `/api/admin/products/search` | GET |
| `/api/admin/products/deleted` | GET |

---

## 📍 Address

| Endpoint | Method |
|--------|--------|
| `/api/user/addresses` | GET |
| `/api/user/addresses` | POST |
| `/api/user/addresses` | PUT |
| `/api/user/addresses` | DELETE |
| `/api/admin/addresses` | GET |

---

## 🗂 Category

| Endpoint | Method |
|--------|--------|
| `/api/admin/categories` | GET |
| `/api/admin/categories` | POST |
| `/api/admin/categories/{id}` | GET |
| `/api/admin/categories/{id}` | PUT |
| `/api/admin/categories/{id}` | DELETE |

---

## 🧾 Subcategory

| Endpoint | Method |
|--------|--------|
| `/api/admin/subcategories` | GET |
| `/api/admin/subcategories` | POST |
| `/api/admin/subcategories/{id}` | GET |
| `/api/admin/subcategories/{id}` | PUT |
| `/api/admin/subcategories/{id}` | DELETE |
| `/api/admin/subcategories/search` | GET |
| `/api/admin/subcategories/deleted` | GET |

---

## 🧾 Orders

| Endpoint | Method |
|--------|--------|
| `/api/admin/orders` | GET |
| `/api/admin/orders/{id}` | GET |
| `/api/user/orders` | POST |
| `/api/user/orders` | GET |
| `/api/user/orders/{id}` | GET |

---

## 🛒 Cart

### Cart Items
| Endpoint | Method |
|--------|--------|
| `/api/admin/cartItems` | GET |
| `/api/user/addToCart` | POST |
| `/api/user/cartItems/{id}` | PUT |
| `/api/user/cartItems/{id}` | DELETE |

### Cart
| Endpoint | Method |
|--------|--------|
| `/api/user/carts` | GET |
| `/api/user/carts` | POST |

---

# 🧰 Tech Stack

### Backend
- ☕ **Java**
- 🌱 **Spring Boot**
- 🔐 **Spring Security**
- 🪪 **JWT Authentication**
- 🗄 **Spring Data JPA**

### Database
- 🐬 **MySQL**

### Tools
- 🧑‍💻 **Eclipse IDE**
- 🌐 **Apache Server**
- 📦 **Maven**

### Dependencies
- Spring Security
- JSON Web Token
- Spring Data JPA
- MySQL Connector
- Lombok
- Validation
- Spring Web

---

# 📈 Future Improvements

- Product reviews & ratings ⭐
- Wishlist API improvements
- Email notifications 📧
- Order tracking
- Admin dashboard analytics 📊
- Image storage using cloud (AWS S3)

---

# 👨‍💻 Author

Developed as a **backend e-commerce system project** using **Spring Boot and RESTful architecture**.

If you like this project, consider giving it a ⭐ on GitHub!

---

If you want, I can also help you add these sections to make the README **look much more professional for GitHub**:

* 📷 **API screenshots (Postman)**
* 🐳 **Docker setup**
* ▶️ **How to run the project locally**
* 📂 **Project folder structure**
* 🔑 **Example JWT authentication flow**

These make a **huge difference for recruiters reviewing your repo**.
