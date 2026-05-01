# 👗 Clothes E-Commerce Platform

A full-featured **RESTful e-commerce backend** built with Spring Boot, featuring secure authentication, role-based access control, and a complete product management system. Designed to demonstrate enterprise-level backend development, security best practices, and scalable microservice architecture.

---

## 🎯 Problem & Purpose

Modern e-commerce platforms require robust, secure, and scalable backends. This project showcases a production-ready implementation handling:

- **Secure user authentication & authorization** with JWT tokens
- **Complex domain modeling** (products, categories, multiple user roles)
- **Role-based access control** with fine-grained authorization
- **Product variants & attributes** (Size, Color, Style, etc.)
- **Address management** with validation and user associations

---

## ✨ Key Features

### 🔐 Security & Authentication

- JWT-based authentication with stateless security
- Role-based access control (RBAC) with Spring Security
- Secure password handling and user validation
- Custom security filters for request validation

### 📦 Product Management

- Multi-level categorization (Categories → Subcategories → Products)
- Product variants via SKUs (Size, Color, Style, etc.)
- Product attributes and attribute types for flexible product configuration
- Dynamic product inventory management

### � Admin Features

- Product attribute management (Size, Color, Style, etc.)
- Subcategory management under main categories
- Address validation and management
- Complete product lifecycle (CRUD operations)

### 👤 User Management

- User registration and profile management
- Address book for multiple shipping addresses
- User status tracking (Active, Suspended, Deleted)
- User role assignment (CUSTOMER, ADMIN, SELLER)

### 🛒 Shopping Cart & Checkout

- Full-featured shopping cart with persistent storage
- Add/update/remove cart items with quantity management
- Cart status tracking for abandoned carts
- Seamless conversion from cart to order

### 📦 Order Management

- Complete order lifecycle (Submitted → Confirmed → Delivered → Received)
- Order item tracking with product SKU variants
- Admin order dashboard with pagination and filtering
- User order history and status tracking
- Order confirmation and delivery workflow

### 💳 Payment Processing

- Integrated payment service with transaction management
- Support for multiple payment providers and methods
- Payment status tracking (Completed, Pending, Failed)
- Unique transaction ID generation for order reconciliation
- Payment validation and order integration

---

## 🛠️ Technologies & Tools

| Category         | Stack                                |
| ---------------- | ------------------------------------ |
| **Backend**      | Java 21, Spring Boot 4.0.3           |
| **Security**     | JWT with JJWT, Spring Security 6     |
| **Database**     | MySQL 5.7+, JPA/Hibernate ORM        |
| **Build**        | Maven 3.6+, Spring Boot Maven Plugin |
| **API**          | RESTful API with JSON                |
| **Testing**      | JUnit 5, Spring Boot Test            |
| **Code Quality** | Clean Architecture, SOLID, Lombok    |

---

## 🚀 Quick Start

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 5.7 or higher

### Installation & Setup

1. **Clone and navigate to project**

   ```bash
   cd clothes
   ```

2. **Configure database in `application.properties`**

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3307/clothes
   spring.datasource.username=root
   spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=update
   ```

   _(Default configuration already set in project)_

3. **Build the project**

   ```bash
   mvn clean install
   ```

4. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

   Or use the Maven wrapper:

   ```bash
   ./mvnw spring-boot:run
   ```

5. **Access the API**
   - Base URL: `http://localhost:8080`
   - API endpoints available at `/api/**`

---

## 📋 Project Structure

```
src/main/java/com/ecom/clothes/
├── config/                  # Security configuration & JWT filters
├── controller/              # REST API endpoints
├── service/                 # Business logic & operations
├── repository/              # Data access layer
├── entity/                  # JPA entities & domain models
├── dto/                     # Request/Response DTOs
│   ├── common/
│   ├── request/
│   └── response/
└── exception/               # Custom exception handling
```

---

## 🎓 Key Takeaways for Reviewers

### Architecture & Design

✅ **Clean Architecture** - Separated concerns (controller → service → repository)  
✅ **Entity Relationships** - Complex JPA mappings (One-to-Many, Many-to-One, One-to-One)  
✅ **Exception Handling** - Custom exceptions with proper HTTP responses

### Security

✅ **JWT Authentication** - Stateless, scalable security implementation  
✅ **Role-Based Authorization** - Fine-grained access control  
✅ **Security Filters** - Custom request validation pipeline

### Best Practices

✅ **DTOs** - Separation of API contracts from entities  
✅ **Repository Pattern** - Abstraction over data access  
✅ **Dependency Injection** - Spring managed dependencies  
✅ **RESTful Principles** - Proper HTTP methods and status codes

### Scalability

✅ **Stateless Design** - Ready for horizontal scaling  
✅ **Transaction Management** - Proper database transaction handling  
✅ **Pagination Ready** - Support for large datasets

---

## 🔌 API Endpoints

### Public Endpoints

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and receive JWT token
- `POST /api/auth/refresh` - Refresh JWT token
- `GET /api/auth/health` - Health check

### User Endpoints (Authenticated)

**Cart Management:**

- `GET /api/user/myCart` - Get current user's shopping cart
- `POST /api/user/addToCart` - Add product to cart
- `PUT /api/user/cartItem/{id}` - Update cart item quantity
- `GET /api/user/cartItem/{id}` - Get specific cart item details
- `DELETE /api/user/cartItem/{id}` - Remove item from cart
- `POST /api/user/clearCart` - Clear entire shopping cart

**Order Management:**

- `POST /api/user/orders` - Place order from cart with payment
- `POST /api/user/orders/{orderId}` - Confirm order delivery receipt

**Address Management:**

- `POST /api/addresses` - Create new address
- `PUT /api/addresses/{id}` - Update address
- `DELETE /api/addresses/{id}` - Delete address

### Admin Endpoints (@PreAuthorize)

**Products:**

- `GET /api/admin/products` - List all products (paginated)
- `GET /api/admin/products/search` - Search products
- `GET /api/admin/products/{id}` - Get product details
- `POST /api/admin/products` - Create product
- `PUT /api/admin/products/{id}` - Update product
- `DELETE /api/admin/products/{id}` - Delete product

**Categories:**

- `GET /api/admin/categories` - List categories
- `GET /api/admin/categories/{id}` - Get category details
- `POST /api/admin/categories` - Create category
- `PUT /api/admin/categories/{id}` - Update category
- `DELETE /api/admin/categories/{id}` - Delete category

**Subcategories:**

- `GET /api/admin/subcategories` - List subcategories
- `POST /api/admin/subcategories` - Create subcategory
- `PUT /api/admin/subcategories/{id}` - Update subcategory
- `DELETE /api/admin/subcategories/{id}` - Delete subcategory

**Product Attributes:**

- `GET /api/admin/productAttributes` - List product attributes (paginated)
- `POST /api/admin/productAttributes` - Create product attribute
- `PUT /api/admin/productAttributes/{id}` - Update product attribute
- `DELETE /api/admin/productAttributes/{id}` - Delete product attribute

**Product SKUs (Variants):**

- `GET /api/admin/productSkus` - List product SKUs (paginated)
- `POST /api/admin/productSkus` - Create product variant
- `PUT /api/admin/productSkus/{id}` - Update product variant
- `DELETE /api/admin/productSkus/{id}` - Delete product variant

**Carts (Admin View):**

- `GET /api/admin/carts` - List all user carts with pagination
- `GET /api/admin/cartItems` - List all cart items with pagination

**Orders (Admin Management):**

- `GET /api/admin/orders` - List all orders with pagination
- `PUT /api/admin/orders/{orderId}` - Update order status

**Addresses (Admin View):**

- `GET /api/admin/addresses` - List all addresses
- `GET /api/admin/addresses/{id}` - Get address details

---

## 🎯 What Makes This Project Impressive

1. **Modern Java & Spring Stack** - Uses Java 21 and Spring Boot 4.0.3, showcasing up-to-date technology adoption
2. **Production-Ready Code** - Follows industry best practices: clean architecture, separation of concerns, proper exception handling
3. **Comprehensive RBAC** - JWT-based authentication + Spring Security role-based authorization with `@PreAuthorize` annotations
4. **Domain-Driven Design** - Well-structured entities with proper JPA relationships and Hibernate mapping
5. **RESTful API Design** - Proper HTTP methods, status codes, pagination, and search functionality
6. **Stateless Architecture** - Session-less design ready for horizontal scaling and cloud deployment
7. **Complete E-Commerce Flow** - Full implementation of user authentication → product browsing → cart management → order placement → payment
8. **Code Quality** - Uses Lombok for boilerplate reduction, proper logging, DTOs for API contracts

---

## 🏆 Technical Challenges & Learning Outcomes

### Complex Entity Relationships & Transactions

- **Challenge:** Handling multi-level relationships (User → Cart → CartItem → Product SKU) with proper cascade behavior and orphan removal
- **Solution:** Strategic use of JPA cascade types, fetch strategies (LAZY loading), and transactional boundaries
- **Learning:** Deep understanding of ORM performance optimization and preventing N+1 query problems

### JWT Token Security & Refresh Flow

- **Challenge:** Implementing stateless authentication while managing token expiration and refresh scenarios
- **Solution:** Custom `JwtAuthenticationFilter` intercepting requests, JJWT library for token generation/validation
- **Learning:** Security best practices including token lifecycle, stored procedure optimization, and authentication pipeline design

### Order-to-Payment Integration

- **Challenge:** Ensuring atomic transactions when converting cart to order while processing payment
- **Solution:** `@Transactional` annotations with proper rollback strategies and transaction isolation levels
- **Learning:** Database transaction semantics, idempotency patterns, and data consistency in financial operations

### Dynamic Product Configuration with SKUs & Attributes

- **Challenge:** Supporting product variants (Size, Color, Style) with flexible attribute management without tight coupling
- **Solution:** Polymorphic product modeling with SKU layer and attribute type abstraction
- **Learning:** Domain-driven design principles, avoiding schema rigidity, enabling future scalability

### Role-Based Access Control at Scale

- **Challenge:** Fine-grained authorization logic across diverse endpoints (User vs Admin vs Seller operations)
- **Solution:** Spring Security's `@PreAuthorize` with custom permission evaluators, clean separation of concerns
- **Learning:** Security architecture, authorization strategies, and permission modeling patterns

---

## 📈 Current Progress vs. Roadmap

| Feature                        | Status         | Notes                                                |
| ------------------------------ | -------------- | ---------------------------------------------------- |
| Authentication & Authorization | ✅ Complete    | JWT + Spring Security with role-based access         |
| Product Management             | ✅ Complete    | Full CRUD with categories, subcategories, attributes |
| Shopping Cart                  | ✅ Complete    | Add/update/remove items, cart persistence            |
| Order Management               | ✅ Complete    | Order lifecycle with status tracking                 |
| Payment Processing             | ✅ Complete    | Transaction management and provider integration      |
| User Management                | ✅ Complete    | Registration, profiles, address book                 |
| Wishlist                       | 🔄 In Progress | Entity created, API endpoints pending                |
| Product Reviews & Ratings      | 📋 Planned     | Schema design phase                                  |
| Email Notifications            | 📋 Planned     | Email service integration planned                    |
| Swagger/OpenAPI Docs           | 📋 Planned     | API documentation automation                         |
| Admin Dashboard UI             | 📋 Future      | Separate frontend project                            |

---

## 🔮 Planned Enhancements

- **Wishlist Feature** - Save favorite products for later purchase
- **Product Reviews & Ratings** - Customer feedback system with rating aggregation
- **Email Notifications** - Order confirmation, shipping updates, promotional emails
- **API Documentation** - Swagger/OpenAPI for interactive API exploration
- **Advanced Search** - Elasticsearch integration for fast product discovery
- **Inventory Management** - Real-time stock tracking and low-stock alerts
- **Discount & Coupon System** - Flexible promotion engine
- **Analytics Dashboard** - Sales trends, user behavior, revenue metrics

---

## 📝 License

This project is open source and available under the MIT License.

---

## 📧 Contact

Feel free to reach out for questions about the implementation or design decisions!
