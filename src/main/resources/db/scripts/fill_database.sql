-- Создаем категории
INSERT INTO categories (name) VALUES 
('Одежда'),
('Обувь'),
('Аксессуары');

-- Создаем размеры
INSERT INTO sizes (name) VALUES 
('XS'),
('S'),
('M'),
('L'),
('XL');

-- Создаем пользователей
INSERT INTO users (email, first_name, last_name, password, phone, role) VALUES 
('admin@example.com', 'Admin', 'Admin', '$2a$10$rDmGcWxX5J5J5J5J5J5J5O', '+1234567890', 'ADMIN'),
('user@example.com', 'User', 'User', '$2a$10$rDmGcWxX5J5J5J5J5J5J5O', '+1234567891', 'USER');

-- Создаем адреса
INSERT INTO addresses (city, country, postal_code, recipient_first_name, recipient_last_name, street, user_id) VALUES 
('Москва', 'Россия', '123456', 'Admin', 'Admin', 'Ленина 1', 1),
('Санкт-Петербург', 'Россия', '654321', 'User', 'User', 'Невская 2', 2);

-- Создаем продукты
INSERT INTO products (name, measurements, product_details, category_id) VALUES 
('Футболка', 'M', 'Хлопковая футболка', 1),
('Джинсы', 'L', 'Классические джинсы', 1),
('Кроссовки', '42', 'Спортивные кроссовки', 2);

-- Создаем цены
INSERT INTO prices (created_at, original_price, present_price, product_id) VALUES 
(NOW(), 2000.00, 1800.00, 1),
(NOW(), 3000.00, 2700.00, 2),
(NOW(), 5000.00, 4500.00, 3);

-- Создаем изображения
INSERT INTO images (image_url, product_id) VALUES 
('https://example.com/tshirt.jpg', 1),
('https://example.com/jeans.jpg', 2),
('https://example.com/sneakers.jpg', 3);

-- Создаем склад
INSERT INTO warehouse (quantity, product_id, size_id) VALUES 
(10, 1, 2),
(5, 2, 3),
(8, 3, 1);

-- Создаем заказы
INSERT INTO orders (created_at, status, total_price, user_id) VALUES 
(NOW(), 'DELIVERED', 4500.00, 2),
(NOW(), 'SHIPPED', 6300.00, 2);

-- Создаем детали заказов
INSERT INTO order_details (price_at_purchase, quantity, order_id, product_id, size_id) VALUES 
(1800.00, 1, 1, 1, 2),
(2700.00, 1, 1, 2, 3),
(4500.00, 1, 2, 3, 1);

-- Создаем платежи
INSERT INTO payments (amount, payment_date, order_id, user_id) VALUES 
(4500.00, NOW(), 1, 2),
(6300.00, NOW(), 2, 2);

-- Создаем корзину
INSERT INTO cart (quantity, product_id, user_id) VALUES 
(1, 1, 2),
(2, 2, 2);

-- Создаем избранное
INSERT INTO wishlist (product_id, user_id) VALUES 
(3, 2); 