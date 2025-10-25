-- Очищаем существующие данные (осторожно!)
-- DELETE FROM wishlist;
-- DELETE FROM cart;
-- DELETE FROM payments;
-- DELETE FROM order_details;
-- DELETE FROM orders;
-- DELETE FROM warehouse;
-- DELETE FROM images;
-- DELETE FROM prices;
-- DELETE FROM products;
-- DELETE FROM addresses;
-- DELETE FROM users;
-- DELETE FROM sizes;
-- DELETE FROM categories;

-- Create categories
INSERT INTO categories (name) VALUES 
('Women''s Clothing'),
('Men''s Clothing'),
('Shoes'),
('Accessories'),
('Bags'),
('Jewelry');

-- Create sizes
INSERT INTO sizes (name) VALUES 
('XS'),
('S'),
('M'),
('L'),
('XL'),
('XXL'),
('36'),
('37'),
('38'),
('39'),
('40'),
('41'),
('42'),
('43'),
('44'),
('45');

-- Create users (with real hashed passwords)
INSERT INTO users (email, first_name, last_name, password, phone, role, enabled, created_at) VALUES 
('admin@kounak-fashion.ru', 'Alexander', 'Kunakov', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '+79161234567', 'ADMIN', b'1', NOW()),
('anna.petrova@email.ru', 'Anna', 'Petrova', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '+79161234568', 'USER', b'1', NOW()),
('dmitry.volkov@gmail.com', 'Dmitry', 'Volkov', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '+79161234569', 'USER', b'1', NOW()),
('elena.smirnova@yandex.ru', 'Elena', 'Smirnova', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '+79161234570', 'USER', b'1', NOW()),
('mikhail.kuznetsov@mail.ru', 'Mikhail', 'Kuznetsov', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '+79161234571', 'USER', b'1', NOW());

-- Create addresses
INSERT INTO addresses (city, country, postal_code, recipient_first_name, recipient_last_name, street, user_id, is_main) VALUES 
('Moscow', 'Russia', '125009', 'Alexander', 'Kunakov', 'Tverskaya St, 15, apt 42', 42, 1),
('Saint Petersburg', 'Russia', '191002', 'Anna', 'Petrova', 'Nevsky Prospekt, 28, apt 15', 43, 1),
('Moscow', 'Russia', '119021', 'Dmitry', 'Volkov', 'Leo Tolstoy St, 16, apt 8', 44, 1),
('Yekaterinburg', 'Russia', '620075', 'Elena', 'Smirnova', 'Lenin St, 5, apt 12', 45, 1),
('Novosibirsk', 'Russia', '630091', 'Mikhail', 'Kuznetsov', 'Red Avenue, 25, apt 7', 46, 1);

-- Create products
INSERT INTO products (name, measurements, product_details, category_id) VALUES 
-- Women's Clothing
('Floral Print Midi Dress', 'S-M-L', 'Elegant viscose dress with floral print. Perfect for office and evening events.', 31),
('Silk Blouse', 'XS-S-M', 'Classic blouse made from natural silk. Ideal for business style.', 31),
('Skinny Jeans', 'S-M-L-XL', 'Fitted jeans made from elastic denim. Modern cut and comfortable fit.', 31),
('Cashmere Cardigan', 'S-M-L', 'Soft cardigan made from natural cashmere. Versatile piece for any season.', 31),

-- Men's Clothing
('Classic Suit', 'M-L-XL', 'Business suit made from wool. Classic cut and high-quality tailoring.', 32),
('Oxford Shirt', 'M-L-XL', 'Classic shirt made from Oxford cotton. Perfect for office and casual wear.', 32),
('Merino Wool Sweater', 'M-L-XL', 'Warm sweater made from merino wool. Ideal for cold weather.', 32),
('Chino Pants', 'M-L-XL', 'Stylish pants made from cotton. Comfortable and versatile.', 32),

-- Shoes
('Heeled Shoes', '36-37-38-39-40', 'Elegant shoes with medium heel. Leather sole and comfortable last.', 33),
('White Sneakers', '36-37-38-39-40-41-42', 'Classic white sneakers. Comfortable for everyday wear.', 33),
('Chelsea Boots', '40-41-42-43-44', 'Stylish Chelsea boots made from natural leather. Perfect for any style.', 33),
('Platform Boots', '36-37-38-39-40', 'Trendy platform boots. Ideal for autumn-winter season.', 33),

-- Accessories
('Silk Scarf', 'Universal', 'Luxurious scarf made from natural silk with print. Adds elegance to any look.', 34),
('Leather Belt', 'S-M-L', 'Classic belt made from natural leather. Universal model.', 34),
('Wool Hat', 'Universal', 'Warm hat made from merino wool. Protects from cold in winter.', 34),

-- Bags
('Leather Tote Bag', 'Medium size', 'Stylish tote bag made from natural leather. Spacious and practical.', 35),
('Chain Clutch', 'Compact', 'Elegant clutch with metal chain. Perfect for evening events.', 35),
('Leather Backpack', 'Medium size', 'Modern backpack made from natural leather. Great for work and travel.', 35),

-- Jewelry
('Silver Earrings', 'Universal', 'Elegant earrings made from sterling silver. Classic design.', 36),
('Stone Ring', 'Size 16-18', 'Stylish ring with semi-precious stone. Unique design.', 36),
('Gold Chain', '45-50 cm', 'Thin gold chain. Universal jewelry piece.', 36);

-- Create prices
INSERT INTO prices (created_at, original_price, present_price, product_id) VALUES 
-- Women's Clothing
(NOW(), 85.00, 72.00, 1),
(NOW(), 120.00, 102.00, 2),
(NOW(), 65.00, 55.00, 3),
(NOW(), 150.00, 127.50, 4),

-- Men's Clothing
(NOW(), 250.00, 212.50, 5),
(NOW(), 45.00, 38.25, 6),
(NOW(), 85.00, 72.25, 7),
(NOW(), 55.00, 46.75, 8),

-- Shoes
(NOW(), 120.00, 102.00, 9),
(NOW(), 85.00, 72.25, 10),
(NOW(), 150.00, 127.50, 11),
(NOW(), 180.00, 153.00, 12),

-- Accessories
(NOW(), 35.00, 29.75, 13),
(NOW(), 25.00, 21.25, 14),
(NOW(), 18.00, 15.30, 15),

-- Bags
(NOW(), 220.00, 187.00, 16),
(NOW(), 85.00, 72.25, 17),
(NOW(), 180.00, 153.00, 18),

-- Jewelry
(NOW(), 45.00, 38.25, 19),
(NOW(), 32.00, 27.20, 20),
(NOW(), 120.00, 102.00, 21);

-- Create images (using Unsplash for realistic images)
INSERT INTO images (image_url, product_id) VALUES 
-- Women's Clothing
('https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=400&h=600&fit=crop', 1),
('https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?w=400&h=600&fit=crop', 2),
('https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=400&h=600&fit=crop', 3),
('https://images.unsplash.com/photo-1571513723812-70c8b0a4a0b4?w=400&h=600&fit=crop', 4),

-- Men's Clothing
('https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&h=600&fit=crop', 5),
('https://images.unsplash.com/photo-1594938298605-c8148c4dae35?w=400&h=600&fit=crop', 6),
('https://images.unsplash.com/photo-1617137984095-74e4e5e3613f?w=400&h=600&fit=crop', 7),
('https://images.unsplash.com/photo-1624378515192-9b55318c95b9?w=400&h=600&fit=crop', 8),

-- Shoes
('https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=400&h=600&fit=crop', 9),
('https://images.unsplash.com/photo-1549298916-b41d501d3772?w=400&h=600&fit=crop', 10),
('https://images.unsplash.com/photo-1544966503-7cc4ac7b5674?w=400&h=600&fit=crop', 11),
('https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=400&h=600&fit=crop', 12),

-- Accessories
('https://images.unsplash.com/photo-1601924994987-69e26d50dc26?w=400&h=600&fit=crop', 13),
('https://images.unsplash.com/photo-1601924994987-69e26d50dc26?w=400&h=600&fit=crop', 14),
('https://images.unsplash.com/photo-1601924994987-69e26d50dc26?w=400&h=600&fit=crop', 15),

-- Bags
('https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=600&fit=crop', 16),
('https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=600&fit=crop', 17),
('https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=600&fit=crop', 18),

-- Jewelry
('https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=400&h=600&fit=crop', 19),
('https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=400&h=600&fit=crop', 20),
('https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=400&h=600&fit=crop', 21);

-- Create warehouse (different sizes for different products)
INSERT INTO warehouse (quantity, product_id, size_id) VALUES 
-- Women's Clothing (sizes XS-XL)
(15, 1, 2), (12, 1, 3), (18, 1, 4), (10, 1, 5),
(8, 2, 1), (15, 2, 2), (20, 2, 3), (12, 2, 4),
(25, 3, 2), (30, 3, 3), (22, 3, 4), (18, 3, 5),
(10, 4, 2), (15, 4, 3), (12, 4, 4),

-- Men's Clothing (sizes M-XL)
(20, 5, 3), (25, 5, 4), (15, 5, 5), (8, 5, 6),
(30, 6, 3), (35, 6, 4), (28, 6, 5), (20, 6, 6),
(18, 7, 3), (22, 7, 4), (16, 7, 5), (12, 7, 6),
(25, 8, 3), (30, 8, 4), (20, 8, 5), (15, 8, 6),

-- Shoes (sizes 36-45)
(20, 9, 7), (25, 9, 8), (30, 9, 9), (28, 9, 10), (22, 9, 11),
(35, 10, 7), (40, 10, 8), (45, 10, 9), (38, 10, 10), (32, 10, 11), (25, 10, 12), (20, 10, 13),
(15, 11, 11), (18, 11, 12), (22, 11, 13), (20, 11, 14), (16, 11, 15),
(12, 12, 7), (15, 12, 8), (18, 12, 9), (16, 12, 10), (14, 12, 11),

-- Accessories (universal sizes)
(50, 13, 1), (40, 14, 2), (35, 14, 3), (30, 14, 4),
(60, 15, 1),

-- Bags (universal sizes)
(25, 16, 1), (20, 17, 1), (18, 18, 1),

-- Jewelry (universal sizes)
(100, 19, 1), (80, 20, 1), (60, 21, 1);

-- Create orders
INSERT INTO orders (created_at, status, total_price, user_id) VALUES 
('2024-01-15 14:30:00', 'DELIVERED', 127.00, 2),
('2024-01-20 10:15:00', 'SHIPPED', 189.25, 3),
('2024-01-25 16:45:00', 'PROCESSING', 85.00, 4),
('2024-02-01 09:20:00', 'DELIVERED', 153.00, 5),
('2024-02-05 13:10:00', 'SHIPPED', 212.50, 2);

-- Create order details
INSERT INTO order_details (price_at_purchase, quantity, order_id, product_id, size_id) VALUES 
-- Order 1 (Anna Petrova)
(72.00, 1, 1, 1, 3), -- Dress M
(55.00, 1, 1, 3, 3), -- Jeans M

-- Order 2 (Dmitry Volkov)
(212.50, 1, 2, 5, 4), -- Suit L
(102.00, 1, 2, 9, 10), -- Shoes 38
(29.75, 1, 2, 13, 1), -- Scarf

-- Order 3 (Elena Smirnova)
(85.00, 1, 3, 17, 1), -- Clutch

-- Order 4 (Mikhail Kuznetsov)
(153.00, 1, 4, 12, 12), -- Boots 42

-- Order 5 (Anna Petrova)
(127.50, 1, 5, 4, 3), -- Cardigan M
(85.00, 1, 5, 10, 9); -- Sneakers 38

-- Create payments
INSERT INTO payments (amount, payment_date, order_id, user_id) VALUES 
(127.00, '2024-01-15 14:35:00', 1, 2),
(189.25, '2024-01-20 10:20:00', 2, 3),
(85.00, '2024-01-25 16:50:00', 3, 4),
(153.00, '2024-02-01 09:25:00', 4, 5),
(212.50, '2024-02-05 13:15:00', 5, 2);

-- Create cart
INSERT INTO cart (quantity, product_id, user_id, size_id) VALUES 
(1, 2, 2, 2), -- Anna: blouse S
(2, 6, 3, 3), -- Dmitry: 2 shirts M
(1, 16, 4, 1), -- Elena: tote bag XS
(1, 19, 5, 1); -- Mikhail: earrings XS

-- Create wishlist
INSERT INTO wishlist (product_id, user_id) VALUES 
(4, 2), -- Anna: cardigan
(7, 3), -- Dmitry: sweater
(11, 4), -- Elena: boots
(20, 5), -- Mikhail: ring
(21, 2); -- Anna: chain
