# Backend - Fashion Store API

> Полнофункциональный REST API для интернет-магазина модной одежды

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-green.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Railway](https://img.shields.io/badge/Railway-Deployed-5bc0de.svg)](https://railway.app/)

## 🚀 Живой API

**Production URL**: https://kounakwebstore-backend-production.up.railway.app/api

### Демо Аккаунт

Админ: **admin@kounak.com** / **admin123**

## 🎯 Основные Возможности

### 🔐 Безопасность
- Spring Security с JWT токенами
- Role-based access control (ADMIN, USER)
- Bcrypt для хеширования паролей
- Защита от CSRF атак
- CORS конфигурация

### 📊 База Данных
- MySQL 8.0
- JPA/Hibernate для ORM
- Оптимизированные SQL запросы
- Автоматические миграции

### 🔄 REST API
- RESTful архитектура
- JSON для обмена данными
- CRUD операции для всех сущностей
- Пагинация и фильтрация

### 📦 Сущности БД

#### Users (Пользователи)
- Личные данные
- Роли и права доступа
- Статус активности

#### Products (Товары)
- Название и описание
- Категории
- Изображения
- Цены (текущая и оригинальная)

#### Categories (Категории)
- Иерархия категорий
- Привязка товаров

#### Orders (Заказы)
- Статусы заказа (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- Детали заказов
- История изменений

#### Cart (Корзина)
- Добавление/удаление товаров
- Управление количеством
- Сохранение для пользователя

#### Wishlist (Список желаний)
- Персональные желания
- Быстрый доступ

#### Addresses (Адреса)
- Доставка
- Основной адрес
- Регистрация получателя

#### Warehouse (Склад)
- Количество товаров
- Размеры в наличии
- Управление остатками

## 🏗️ Архитектура

```
backend/
├── config/              # Конфигурация (Security, CORS, DB)
├── controller/          # REST контроллеры (22 контроллера)
├── model/               # JPA сущности (11 моделей)
├── repository/          # Data Access Layer (11 репозиториев)
├── service/             # Бизнес логика (15 сервисов)
├── dto/                 # Data Transfer Objects (7 DTO)
├── security/            # JWT и Security (4 компонента)
└── exception/           # Обработка ошибок (3 исключения)
```

### Контроллеры
- `AuthController` - Аутентификация
- `ProductController` - Товары
- `CategoryController` - Категории
- `CartApiController` - Корзина
- `OrderApiController` - Заказы
- `AddressController` - Адреса
- `WishlistApiController` - Wishlist
- `PaymentController` - Платежи
- `AdminStatusController` - Админ статус
- И еще 13 контроллеров...

### Сервисы
- `AuthService` - Логика авторизации
- `ProductService` - Управление товарами
- `OrderService` - Обработка заказов
- `CartService` - Корзина
- `UserService` - Пользователи
- `AdminService` - Админ функции
- И еще 10 сервисов...

## 🚀 Запуск

### Локально

```bash
# Клонирование
git clone https://github.com/yourusername/kounak.git
cd kounak/kounakwebstore-backend

# Настройка базы данных (MySQL)
# Создайте базу данных
mysql -u root -p
CREATE DATABASE fashionDB;

# Настройте application.properties
# Укажите свои креденшелы для БД

# Сборка и запуск
mvn clean install
mvn spring-boot:run
```

### Профиль Application

В `application.properties`:
```properties
# База данных
spring.datasource.url=jdbc:mysql://localhost:3306/fashionDB
spring.datasource.username=root
spring.datasource.password=your_password

# JWT Secret
jwt.secret=your-secret-key-here

# Server
server.port=8080
```

## 📚 API Документация

### Authentication

#### Регистрация
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

#### Вход
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

#### Получить все товары
```http
GET /api/public/products
Authorization: Bearer {token}
```

#### Получить товар по ID
```http
GET /api/public/products/{id}
Authorization: Bearer {token}
```

#### Создать товар (Admin)
```http
POST /api/admin/products
Authorization: Bearer {admin_token}
Content-Type: application/json

{
  "name": "Название товара",
  "description": "Описание",
  "categoryId": 1,
  "images": ["url1", "url2"],
  "price": 99.99,
  "originalPrice": 149.99
}
```

### Orders

#### Создать заказ
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

#### Получить заказы пользователя
```http
GET /api/orders
Authorization: Bearer {token}
```

#### Отменить заказ
```http
PUT /api/orders/{orderId}/cancel
Authorization: Bearer {token}
```

## 🧪 Тестирование

```bash
# Запуск всех тестов
mvn test

# Запуск с покрытием
mvn test jacoco:report
```

## 📊 База Данных

### Диаграмма ER

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

## 🔧 Конфигурация

### Security Configuration
- JWT токены с expire 48 часов
- Password encoding с BCrypt
- CORS для фронтенда
- Сессионные cookies с secure флагом

### Database Configuration
- Connection pool: HikariCP
- Batch processing: 50 операций
- Автоматические транзакции
- Timeout: 30 секунд

## 🚢 Деплой

### Railway
Проект деплоится на Railway автоматически из GitHub:
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

## 📝 Логирование

```properties
# Debug logging для разработки
logging.level.com.kounak.backend=DEBUG
logging.level.org.springframework.web=DEBUG

# SQL queries
logging.level.org.hibernate.SQL=DEBUG
```

## 🎯 Статистика

- **Контроллеры**: 22
- **Модели**: 11
- **Сервисы**: 15
- **Репозитории**: 11
- **DTO**: 7
- **API Endpoints**: 50+

## 🏆 Особенности

✅ **Полная безопасность** - JWT, BCrypt, Spring Security  
✅ **Масштабируемость** - Оптимизированные запросы  
✅ **Документированный API** - RESTful принципы  
✅ **Error handling** - Централизованная обработка ошибок  
✅ **Production ready** - Готов к использованию  

## 📄 Лицензия

MIT License

## 👨‍💻 Автор

**Kostya Kunak** - Разработано с нуля для дипломной работы

---

⭐ Спасибо за интерес к проекту!

