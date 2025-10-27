# Fashion Store Backend

Spring Boot REST API for e-commerce platform. This project was developed **completely from scratch** as a university diploma project in Ukraine and is now a production-ready backend system deployed on Railway.

## Live Demo

**API**: https://kounakwebstore-backend-production.up.railway.app/api

**Demo Account**: `admin@kounak.com` / `admin123`

## About This Project

This is a large-scale backend API system built following professional best practices. No templates or pre-built solutions were used - the entire API, security implementation, and database architecture were built from the ground up.

Originally developed as a university diploma project, it now serves as a fully functional backend for a production e-commerce platform with real-world features including authentication, authorization, order processing, and admin capabilities.

## Tech Stack

- **Spring Boot 3.2.3** - Enterprise framework
- **Spring Security** - Authentication & authorization
- **JWT** - Token-based security
- **MySQL** - Production database
- **JPA/Hibernate** - Object-relational mapping
- **BCrypt** - Password encryption
- **Deployed on Railway** - Reliable cloud hosting

## Key Features

### Security
- JWT token-based authentication
- Role-based access control (ADMIN, USER)
- Password hashing with BCrypt
- CORS configuration
- Protected API endpoints

### Core Functionality
- RESTful API architecture
- Product management system
- Shopping cart persistence
- Order processing with multiple statuses
- User management
- Address management
- Wishlist functionality
- Admin panel API support

### Database
- MySQL with optimized queries
- 11 database tables
- JPA entities and repositories
- Automatic migrations
- Transaction management

## API Structure

- **22 Controllers** - REST endpoints
- **11 Models** - Database entities
- **15 Services** - Business logic
- **11 Repositories** - Data access layer
- **7 DTOs** - Data transfer objects
- **50+ API Endpoints**

## Getting Started

```bash
mvn clean install
mvn spring-boot:run
```

Configure `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fashionDB
spring.datasource.username=root
spring.datasource.password=your_password
```

## Key Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Products
- `GET /api/public/products` - List all products
- `GET /api/public/products/{id}` - Get product details

### Cart & Orders
- `GET /api/cart` - Get user cart
- `POST /api/cart/add` - Add to cart
- `POST /api/orders/create` - Create order
- `GET /api/orders` - Get user orders

### Admin (requires ADMIN role)
- `GET /api/admin/products` - Manage products
- `POST /api/admin/products` - Create product
- `PUT /api/admin/products/{id}` - Update product
- `DELETE /api/admin/products/{id}` - Delete product

## Deployment

Automatically deployed on Railway with:
- Automatic builds from GitHub
- Environment-based configuration
- Database connection pooling
- Health monitoring

## Author

**Kostya Kunak** - Full-stack developer, university student in Ukraine

Built completely from scratch, no templates or skeletons used.

---

⭐ Star this repo if you find it useful!
