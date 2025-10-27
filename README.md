# Fashion Store Backend

> Enterprise-grade Spring Boot API built **completely from scratch** as a university diploma project. No scaffolding tools, no code generators, no templates - just clean, professional backend architecture following industry standards.

**[Live API → railway.app](https://fashion-store-backend-production-8473.up.railway.app/api)**

**Try it**: `admin@kounak.com` / `admin123`

## 🎯 Overview

This is a comprehensive RESTful API backend developed as a university diploma project in Ukraine. What began as an academic exercise has become a production-ready system handling authentication, authorization, order processing, payment simulation, and admin operations - everything needed to power a real e-commerce platform.

**Key Achievement**: The entire security architecture, database schema, business logic, and API design were implemented from scratch without using any boilerplate or templates.

## 🏗️ Architecture & Scale

The backend consists of:
- **22 REST controllers** for clean endpoint organization
- **15 service classes** encapsulating business logic
- **11 database models** with proper relationships
- **11 repositories** for data access abstraction
- **7 DTOs** for structured API communication
- **4 security components** for authentication and authorization
- **50+ API endpoints** covering all operations

## 💼 What Makes This Production-Ready

### Security Architecture
- **JWT token-based authentication** - Secure, stateless sessions
- **Role-based access control** - ADMIN and USER permissions
- **BCrypt password hashing** - Industry-standard encryption
- **CORS configuration** - Secure cross-origin requests
- **Protected endpoints** - Admin-only operations secured
- **Session management** - Secure cookies with httpOnly flags

### Database Design
- **11 interconnected tables** - Users, products, orders, cart, wishlist, addresses, payments, warehouse
- **Optimized queries** - JPA with custom SQL where needed
- **Transaction management** - ACID compliance
- **Automatic migrations** - Hibernate DDL updates
- **Connection pooling** - HikariCP for performance

### Business Logic
- **Order processing** - Complete lifecycle (PENDING → CONFIRMED → SHIPPED → DELIVERED → CANCELLED)
- **Cart management** - Persistent across sessions
- **Product inventory** - Size-based stock tracking
- **User management** - Registration, authentication, profile updates
- **Address management** - Multiple delivery addresses per user
- **Payment simulation** - Transaction processing
- **Admin operations** - Product CRUD, category management, statistics

### API Architecture
- **RESTful design** - Clean, intuitive endpoints
- **JSON communication** - Standard data format
- **Error handling** - Centralized exception management
- **Response standardization** - Consistent API responses
- **Request validation** - Input sanitization and validation

## 🛠️ Tech Stack

### Core Framework
- **Spring Boot 3.2.3** - Enterprise-grade framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database abstraction layer
- **Hibernate** - Object-relational mapping

### Security
- **JWT (jjwt 0.11.5)** - Token generation and validation
- **BCrypt** - Password hashing
- **CORS** - Cross-origin resource sharing
- **HttpOnly cookies** - XSS protection

### Database
- **MySQL 8.0** - Relational database
- **HikariCP** - High-performance connection pool
- **Hibernate** - ORM with automatic migrations

### Additional
- **Lombok** - Code generation for clean Java
- **ModelMapper** - Object transformation
- **Validation** - Input sanitization

### Deployment
- **Railway** - Cloud hosting with auto-deploy
- **Production database** - MySQL on cloud
- **Environment config** - Secure credential management

## 📁 Project Structure

```
src/main/java/com/kounak/backend/
├── controller/       # 22 REST controllers (Auth, Products, Orders, Cart, etc.)
├── service/          # 15 service classes (business logic)
├── model/            # 11 entity models (JPA entities)
├── repository/       # 11 data access repositories
├── dto/              # 7 data transfer objects
├── config/           # Configuration (Security, CORS, Database)
├── security/         # JWT authentication and filters
└── exception/        # Error handling
```

## 🚀 Getting Started

```bash
# Install dependencies
mvn clean install

# Run application
mvn spring-boot:run

# Application runs on http://localhost:8080
```

Configure `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fashionDB
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=your-secret-key-here
```

## 📚 API Documentation

### Authentication
```http
POST /api/auth/register  # Register new user
POST /api/auth/login      # Authenticate user
GET  /api/auth/user       # Get current user
```

### Products
```http
GET  /api/public/products      # List all products
GET  /api/public/products/{id} # Get product details
GET  /api/public/categories    # List categories
```

### Shopping
```http
GET    /api/cart                    # Get user cart
POST   /api/cart/add               # Add to cart
DELETE /api/cart/remove/{productId}# Remove from cart
```

### Orders
```http
POST /api/orders/create        # Create order
GET  /api/orders               # Get user orders
GET  /api/orders/{id}          # Get order details
PUT  /api/orders/{id}/cancel   # Cancel order
```

### Admin (ADMIN role required)
```http
GET    /api/admin/products      # Manage products
POST   /api/admin/products      # Create product
PUT    /api/admin/products/{id}# Update product
DELETE /api/admin/products/{id}# Delete product
```

## 🔒 Security Features

- **JWT tokens** with 48-hour expiry
- **Password encryption** with BCrypt
- **Role-based access** for admin operations
- **CORS protection** for cross-origin requests
- **SQL injection prevention** via JPA/Hibernate
- **XSS protection** with secure cookies
- **Request validation** on all inputs

## 📊 Database Schema

```
Users ──┬── Orders ── OrderDetails ── Products ─┬── Categories
        │                                         ├── Prices
        ├── Addresses                            ├── Images
        ├── Cart ── Products                      └── Warehouse ── Sizes
        │
        └── Wishlist ── Products
```

## 🚢 Deployment

Deployed on **Railway** with:
- Automatic builds from GitHub
- Production database connection
- Environment variable management
- Health monitoring
- SSL encryption

## 🎓 Project Origin

Originally developed as a **university diploma project** in Ukraine to demonstrate:
- REST API design and implementation
- Spring Boot best practices
- Security architecture (JWT, BCrypt, RBAC)
- Database design and optimization
- Production deployment

The project showcases enterprise-level backend development that can power real-world e-commerce applications.

## 👨‍💻 Author

**Kostya Kunak** - Full-stack developer and university student in Ukraine

Built completely from scratch - every controller, every service, every security feature written from the ground up following professional standards.

---

⭐ **Star this repo if you found it helpful!**
