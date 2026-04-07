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

### Admin-Only Endpoints (@PreAuthorize)

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

**Addresses:**

- `GET /api/admin/addresses` - List all addresses
- `GET /api/admin/addresses/{id}` - Get address details
- `POST /api/addresses` - Create address
- `PUT /api/addresses/{id}` - Update address
- `DELETE /api/addresses/{id}` - Delete address

---

## 🎯 What Makes This Project Impressive

1. **Modern Java & Spring Stack** - Uses Java 21 and Spring Boot 4.0.3, showcasing up-to-date technology adoption
2. **Production-Ready Code** - Follows industry best practices: clean architecture, separation of concerns, proper exception handling
3. **Comprehensive RBAC** - JWT-based authentication + Spring Security role-based authorization with `@PreAuthorize` annotations
4. **Domain-Driven Design** - Well-structured entities with proper JPA relationships and Hibernate mapping
5. **RESTful API Design** - Proper HTTP methods, status codes, pagination, and search functionality
6. **Stateless Architecture** - Session-less design ready for horizontal scaling and cloud deployment
7. **Code Quality** - Uses Lombok for boilerplate reduction, proper logging, DTOs for API contracts

## 📈 Future Enhancements

This project can be extended with:

- Shopping cart & order management functionality
- Payment processing integration (Stripe, PayPal)
- Wishlist feature
- Product reviews and ratings
- Email notifications
- Admin dashboard UI
- API documentation (Swagger/OpenAPI)

---

## 📝 License

This project is open source and available under the MIT License.

---

## 📧 Contact

Feel free to reach out for questions about the implementation or design decisions!
