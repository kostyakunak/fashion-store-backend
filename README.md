# Backend - Fashion Store API

> Full-featured REST API for fashion e-commerce platform

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-green.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Railway](https://img.shields.io/badge/Railway-Deployed-5bc0de.svg)](https://railway.app/)

## Live Demo

**API URL**: https://kounakwebstore-backend-production.up.railway.app/api

### Demo Account

Admin: **admin@kounak.com** / **admin123**

## 🎯 Key Features

### 🔐 Security
- Spring Security with JWT tokens
- Role-based access control (ADMIN, USER)
- BCrypt password hashing
- CSRF attack protection
- CORS configuration

### 📊 Database
- MySQL 8.0
- JPA/Hibernate for ORM
- Optimized SQL queries
- Automatic migrations

### 🔄 REST API
- RESTful architecture
- JSON data exchange
- CRUD operations for all entities
- Pagination and filtering

### 📦 Database Entities

#### Users
- Personal data
- Roles and permissions
- Active status

#### Products
- Name and description
- Categories
- Images
- Prices (current and original)

#### Categories
- Category hierarchy
- Product binding

#### Orders
- Order statuses (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- Order details
- Change history

#### Cart
- Add/remove products
- Quantity management
- User persistence

#### Wishlist
- Personal wishlist
- Quick access

#### Addresses
- Delivery addresses
- Main address
- Recipient registration

#### Warehouse
- Product quantity
- Available sizes
- Stock management

## 🏗️ Architecture

```
backend/
├── config/              # Configuration (Security, CORS, DB)
├── controller/          # REST controllers (22 controllers)
├── model/               # JPA entities (11 models)
├── repository/          # Data Access Layer (11 repositories)
├── service/             # Business logic (15 services)
├── dto/                 # Data Transfer Objects (7 DTOs)
├── security/            # JWT and Security (4 components)
└── exception/           # Error handling (3 exceptions)
```

### Controllers
- `AuthController` - Authentication
- `ProductController` - Products
- `CategoryController` - Categories
- `CartApiController` - Cart
- `OrderApiController` - Orders
- `AddressController` - Addresses
- `WishlistApiController` - Wishlist
- `PaymentController` - Payments
- `AdminStatusController` - Admin status
- And 13 more controllers...

### Services
- `AuthService` - Authorization logic
- `ProductService` - Product management
- `OrderService` - Order processing
- `CartService` - Cart management
- `UserService` - User management
- `AdminService` - Admin functions
- And 10 more services...

## 🚀 Setup

### Local Setup

```bash
# Clone
git clone https://github.com/yourusername/fashion-store-backend.git
cd fashion-store-backend

# Database setup (MySQL)
# Create database
mysql -u root -p
CREATE DATABASE fashionDB;

# Configure application.properties
# Set your database credentials

# Build and run
mvn clean install
mvn spring-boot:run
```

### Application Profile

In `application.properties`:
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/fashionDB
spring.datasource.username=root
spring.datasource.password=your_password

# JWT Secret
jwt.secret=your-secret-key-here

# Server
server.port=8080
```

## 📚 API Documentation

### Authentication

#### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1234567890"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "USER"
  }
}
```

### Products

#### Get all products
```http
GET /api/public/products
Authorization: Bearer {token}
```

#### Get product by ID
```http
GET /api/public/products/{id}
Authorization: Bearer {token}
```

#### Create product (Admin)
```http
POST /api/admin/products
Authorization: Bearer {admin_token}
Content-Type: application/json

{
  "name": "Product name",
  "description": "Description",
  "categoryId": 1,
  "images": ["url1", "url2"],
  "price": 99.99,
  "originalPrice": 149.99
}
```

### Orders

#### Create order
```http
POST /api/orders/create
Authorization: Bearer {token}
Content-Type: application/json

{
  "addressId": 1,
  "cartItems": [
    {
      "productId": 1,
      "sizeId": 1,
      "quantity": 2
    }
  ]
}
```

#### Get user orders
```http
GET /api/orders
Authorization: Bearer {token}
```

#### Cancel order
```http
PUT /api/orders/{orderId}/cancel
Authorization: Bearer {token}
```

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report
```

## 📊 Database

### ER Diagram

```
users ────────┬──── orders
               │      │
               │      └── order_details
               │              │
               │              └── products
               │                    │
               ├── addresses         ├── categories
               │                    ├── prices
               ├── cart             ├── warehouse
               │   │                └── images
               │   └── products          │
               │                         │
               └── wishlist            └── sizes
                   │
                   └── products
```

## 🔧 Configuration

### Security Configuration
- JWT tokens with 48-hour expiry
- BCrypt password encoding
- CORS for frontend
- Session cookies with secure flag

### Database Configuration
- Connection pool: HikariCP
- Batch processing: 50 operations
- Automatic transactions
- Timeout: 30 seconds

## 🚢 Deployment

### Railway
Project deploys to Railway automatically from GitHub:
1. Repository: `kounak/kounakwebstore-backend`
2. Build: `mvn clean install`
3. Run: `java -jar target/demo-0.0.1-SNAPSHOT.jar`

### Environment Variables
```bash
SPRING_DATASOURCE_URL=jdbc:mysql://host:port/db
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=password
JWT_SECRET=your-secret
PORT=8080
```

## 📝 Logging

```properties
# Debug logging for development
logging.level.com.kounak.backend=DEBUG
logging.level.org.springframework.web=DEBUG

# SQL queries
logging.level.org.hibernate.SQL=DEBUG
```

## 🎯 Statistics

- **Controllers**: 22
- **Models**: 11
- **Services**: 15
- **Repositories**: 11
- **DTOs**: 7
- **API Endpoints**: 50+

## 🏆 Features

✅ **Full security** - JWT, BCrypt, Spring Security  
✅ **Scalability** - Optimized queries  
✅ **Documented API** - RESTful principles  
✅ **Error handling** - Centralized error handling  
✅ **Production ready** - Ready for use  

## 📄 License

MIT License

## 👨‍💻 Author

**Kostya Kunak** - Built from scratch for diploma project

---

⭐ Thanks for your interest in the project!
