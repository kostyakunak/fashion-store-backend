-- Fixed SQL script with correct IDs
-- This script assumes users (42-46), categories (31-36), and sizes (67-82) already exist

-- Create addresses (using correct user IDs)
INSERT INTO addresses (city, country, postal_code, recipient_first_name, recipient_last_name, street, user_id, is_main) VALUES 
('Moscow', 'Russia', '125009', 'Alexander', 'Kunakov', 'Tverskaya St, 15, apt 42', 42, 1),
('Saint Petersburg', 'Russia', '191002', 'Anna', 'Petrova', 'Nevsky Prospekt, 28, apt 15', 43, 1),
('Moscow', 'Russia', '119021', 'Dmitry', 'Volkov', 'Leo Tolstoy St, 16, apt 8', 44, 1),
('Yekaterinburg', 'Russia', '620075', 'Elena', 'Smirnova', 'Lenin St, 5, apt 12', 45, 1),
('Novosibirsk', 'Russia', '630091', 'Mikhail', 'Kuznetsov', 'Red Avenue, 25, apt 7', 46, 1);

-- Create products (using correct category IDs)
INSERT INTO products (name, measurements, product_details, category_id) VALUES 
-- Women's Clothing (category_id = 31)
('Floral Print Midi Dress', 'S-M-L', 'Elegant viscose dress with floral print. Perfect for office and evening events.', 31),
('Silk Blouse', 'XS-S-M', 'Classic blouse made from natural silk. Ideal for business style.', 31),
('Skinny Jeans', 'S-M-L-XL', 'Fitted jeans made from elastic denim. Modern cut and comfortable fit.', 31),
('Cashmere Cardigan', 'S-M-L', 'Soft cardigan made from natural cashmere. Versatile piece for any season.', 31),

-- Men's Clothing (category_id = 32)
('Classic Suit', 'M-L-XL', 'Business suit made from wool. Classic cut and high-quality tailoring.', 32),
('Oxford Shirt', 'M-L-XL', 'Classic shirt made from Oxford cotton. Perfect for office and casual wear.', 32),
('Merino Wool Sweater', 'M-L-XL', 'Warm sweater made from merino wool. Ideal for cold weather.', 32),
('Chino Pants', 'M-L-XL', 'Stylish pants made from cotton. Comfortable and versatile.', 32),

-- Shoes (category_id = 33)
('Heeled Shoes', '36-37-38-39-40', 'Elegant shoes with medium heel. Leather sole and comfortable last.', 33),
('White Sneakers', '36-37-38-39-40-41-42', 'Classic white sneakers. Comfortable for everyday wear.', 33),
('Chelsea Boots', '40-41-42-43-44', 'Stylish Chelsea boots made from natural leather. Perfect for any style.', 33),
('Platform Boots', '36-37-38-39-40', 'Trendy platform boots. Ideal for autumn-winter season.', 33),

-- Accessories (category_id = 34)
('Silk Scarf', 'Universal', 'Luxurious scarf made from natural silk with print. Adds elegance to any look.', 34),
('Leather Belt', 'S-M-L', 'Classic belt made from natural leather. Universal model.', 34),
('Wool Hat', 'Universal', 'Warm hat made from merino wool. Protects from cold in winter.', 34),

-- Bags (category_id = 35)
('Leather Tote Bag', 'Medium size', 'Stylish tote bag made from natural leather. Spacious and practical.', 35),
('Chain Clutch', 'Compact', 'Elegant clutch with metal chain. Perfect for evening events.', 35),
('Leather Backpack', 'Medium size', 'Modern backpack made from natural leather. Great for work and travel.', 35),

-- Jewelry (category_id = 36)
('Silver Earrings', 'Universal', 'Elegant earrings made from sterling silver. Classic design.', 36),
('Stone Ring', 'Size 16-18', 'Stylish ring with semi-precious stone. Unique design.', 36),
('Gold Chain', '45-50 cm', 'Thin gold chain. Universal jewelry piece.', 36);

-- Get the product IDs that were just created
-- We'll use these IDs in subsequent inserts
-- Product IDs will be: 1-21 (assuming they start from 1)

-- Create prices (using product IDs 1-21)
INSERT INTO prices (created_at, original_price, present_price, product_id) VALUES 
-- Women's Clothing (products 1-4)
(NOW(), 85.00, 72.00, 1),
(NOW(), 120.00, 102.00, 2),
(NOW(), 65.00, 55.00, 3),
(NOW(), 150.00, 127.50, 4),

-- Men's Clothing (products 5-8)
(NOW(), 250.00, 212.50, 5),
(NOW(), 45.00, 38.25, 6),
(NOW(), 85.00, 72.25, 7),
(NOW(), 55.00, 46.75, 8),

-- Shoes (products 9-12)
(NOW(), 120.00, 102.00, 9),
(NOW(), 85.00, 72.25, 10),
(NOW(), 150.00, 127.50, 11),
(NOW(), 180.00, 153.00, 12),

-- Accessories (products 13-15)
(NOW(), 35.00, 29.75, 13),
(NOW(), 25.00, 21.25, 14),
(NOW(), 18.00, 15.30, 15),

-- Bags (products 16-18)
(NOW(), 220.00, 187.00, 16),
(NOW(), 85.00, 72.25, 17),
(NOW(), 180.00, 153.00, 18),

-- Jewelry (products 19-21)
(NOW(), 45.00, 38.25, 19),
(NOW(), 32.00, 27.20, 20),
(NOW(), 120.00, 102.00, 21);

-- Create images (using product IDs 1-21)
INSERT INTO images (image_url, product_id) VALUES 
-- Women's Clothing (products 1-4)
('https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=400&h=600&fit=crop', 1),
('https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?w=400&h=600&fit=crop', 2),
('https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=400&h=600&fit=crop', 3),
('https://images.unsplash.com/photo-1571513723812-70c8b0a4a0b4?w=400&h=600&fit=crop', 4),

-- Men's Clothing (products 5-8)
('https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&h=600&fit=crop', 5),
('https://images.unsplash.com/photo-1594938298605-c8148c4dae35?w=400&h=600&fit=crop', 6),
('https://images.unsplash.com/photo-1617137984095-74e4e5e3613f?w=400&h=600&fit=crop', 7),
('https://images.unsplash.com/photo-1624378515192-9b55318c95b9?w=400&h=600&fit=crop', 8),

-- Shoes (products 9-12)
('https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=400&h=600&fit=crop', 9),
('https://images.unsplash.com/photo-1549298916-b41d501d3772?w=400&h=600&fit=crop', 10),
('https://images.unsplash.com/photo-1544966503-7cc4ac7b5674?w=400&h=600&fit=crop', 11),
('https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=400&h=600&fit=crop', 12),

-- Accessories (products 13-15)
('https://images.unsplash.com/photo-1601924994987-69e26d50dc26?w=400&h=600&fit=crop', 13),
('https://images.unsplash.com/photo-1601924994987-69e26d50dc26?w=400&h=600&fit=crop', 14),
('https://images.unsplash.com/photo-1601924994987-69e26d50dc26?w=400&h=600&fit=crop', 15),

-- Bags (products 16-18)
('https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=600&fit=crop', 16),
('https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=600&fit=crop', 17),
('https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=600&fit=crop', 18),

-- Jewelry (products 19-21)
('https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=400&h=600&fit=crop', 19),
('https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=400&h=600&fit=crop', 20),
('https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=400&h=600&fit=crop', 21);

-- Create warehouse (using correct size IDs: 67-82)
INSERT INTO warehouse (quantity, product_id, size_id) VALUES 
-- Women's Clothing (products 1-4, sizes 67-71 for XS-XL)
(15, 1, 68), (12, 1, 69), (18, 1, 70), (10, 1, 71), -- Dress S-M-L-XL
(8, 2, 67), (15, 2, 68), (20, 2, 69), (12, 2, 70), -- Blouse XS-S-M-L
(25, 3, 68), (30, 3, 69), (22, 3, 70), (18, 3, 71), -- Jeans S-M-L-XL
(10, 4, 68), (15, 4, 69), (12, 4, 70), -- Cardigan S-M-L

-- Men's Clothing (products 5-8, sizes 69-71 for M-XL)
(20, 5, 69), (25, 5, 70), (15, 5, 71), (8, 5, 72), -- Suit M-L-XL-XXL
(30, 6, 69), (35, 6, 70), (28, 6, 71), (20, 6, 72), -- Shirt M-L-XL-XXL
(18, 7, 69), (22, 7, 70), (16, 7, 71), (12, 7, 72), -- Sweater M-L-XL-XXL
(25, 8, 69), (30, 8, 70), (20, 8, 71), (15, 8, 72), -- Pants M-L-XL-XXL

-- Shoes (products 9-12, sizes 67-82 for 36-45)
(20, 9, 67), (25, 9, 68), (30, 9, 69), (28, 9, 70), (22, 9, 71), -- Heeled Shoes 36-40
(35, 10, 67), (40, 10, 68), (45, 10, 69), (38, 10, 70), (32, 10, 71), (25, 10, 72), (20, 10, 73), -- Sneakers 36-42
(15, 11, 71), (18, 11, 72), (22, 11, 73), (20, 11, 74), (16, 11, 75), -- Chelsea Boots 40-44
(12, 12, 67), (15, 12, 68), (18, 12, 69), (16, 12, 70), (14, 12, 71), -- Platform Boots 36-40

-- Accessories (products 13-15, universal sizes)
(50, 13, 67), -- Scarf XS
(40, 14, 68), (35, 14, 69), (30, 14, 70), -- Belt S-M-L
(60, 15, 67), -- Hat XS

-- Bags (products 16-18, universal sizes)
(25, 16, 67), (20, 17, 67), (18, 18, 67), -- Bags XS

-- Jewelry (products 19-21, universal sizes)
(100, 19, 67), (80, 20, 67), (60, 21, 67); -- Jewelry XS

-- Create orders (using correct user IDs: 42-46)
INSERT INTO orders (created_at, status, total_price, user_id) VALUES 
('2024-01-15 14:30:00', 'DELIVERED', 127.00, 43), -- Anna Petrova
('2024-01-20 10:15:00', 'SHIPPED', 189.25, 44), -- Dmitry Volkov
('2024-01-25 16:45:00', 'PROCESSING', 85.00, 45), -- Elena Smirnova
('2024-02-01 09:20:00', 'DELIVERED', 153.00, 46), -- Mikhail Kuznetsov
('2024-02-05 13:10:00', 'SHIPPED', 212.50, 43); -- Anna Petrova

-- Create order details (using order IDs 1-5, product IDs 1-21, size IDs 67-82)
INSERT INTO order_details (price_at_purchase, quantity, order_id, product_id, size_id) VALUES 
-- Order 1 (Anna Petrova)
(72.00, 1, 1, 1, 69), -- Dress M
(55.00, 1, 1, 3, 69), -- Jeans M

-- Order 2 (Dmitry Volkov)
(212.50, 1, 2, 5, 70), -- Suit L
(102.00, 1, 2, 9, 68), -- Shoes 37
(29.75, 1, 2, 13, 67), -- Scarf XS

-- Order 3 (Elena Smirnova)
(85.00, 1, 3, 17, 67), -- Clutch XS

-- Order 4 (Mikhail Kuznetsov)
(153.00, 1, 4, 12, 72), -- Boots 42

-- Order 5 (Anna Petrova)
(127.50, 1, 5, 4, 69), -- Cardigan M
(85.00, 1, 5, 10, 68); -- Sneakers 37

-- Create payments (using order IDs 1-5, user IDs 42-46)
INSERT INTO payments (amount, payment_date, order_id, user_id) VALUES 
(127.00, '2024-01-15 14:35:00', 1, 43),
(189.25, '2024-01-20 10:20:00', 2, 44),
(85.00, '2024-01-25 16:50:00', 3, 45),
(153.00, '2024-02-01 09:25:00', 4, 46),
(212.50, '2024-02-05 13:15:00', 5, 43);

-- Create cart (using user IDs 42-46, product IDs 1-21, size IDs 67-82)
INSERT INTO cart (quantity, product_id, user_id, size_id) VALUES 
(1, 2, 43, 68), -- Anna: blouse S
(2, 6, 44, 69), -- Dmitry: 2 shirts M
(1, 16, 45, 67), -- Elena: tote bag XS
(1, 19, 46, 67); -- Mikhail: earrings XS

-- Create wishlist (using user IDs 42-46, product IDs 1-21)
INSERT INTO wishlist (product_id, user_id) VALUES 
(4, 43), -- Anna: cardigan
(7, 44), -- Dmitry: sweater
(11, 45), -- Elena: boots
(20, 46), -- Mikhail: ring
(21, 43); -- Anna: chain
