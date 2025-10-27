# Fashion Store Backend

Spring Boot REST API for e-commerce platform. This project was developed from scratch as part of a university diploma and is now a production-ready backend system.

## Live Demo

**API URL**: https://kounakwebstore-backend-production.up.railway.app/api

**Demo Account**: `admin@kounak.com` / `admin123`

## Tech Stack

- **Spring Boot 3.2.3** - Framework
- **Spring Security** - Authentication
- **JWT** - Token-based auth
- **MySQL** - Database
- **JPA/Hibernate** - ORM

## Key Features

- RESTful API architecture
- JWT authentication
- Role-based access (ADMIN, USER)
- Product management
- Order processing
- Cart and wishlist management
- Admin panel support

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

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register
- `POST /api/auth/login` - Login

### Products
- `GET /api/public/products` - Get all products
- `GET /api/public/products/{id}` - Get product

### Cart
- `GET /api/cart` - Get cart
- `POST /api/cart/add` - Add to cart

### Orders
- `POST /api/orders/create` - Create order
- `GET /api/orders` - Get orders

### Admin
- `GET /api/admin/products` - Manage products
- `POST /api/admin/products` - Create product

## Author

**Kostya Kunak** - Built from scratch

---

⭐ Star this repo if you find it useful!
