-- Final SQL script with correct product IDs (44-64)
-- Products already exist with IDs 44-64

-- Create prices (using correct product IDs 44-64)
INSERT INTO prices (created_at, original_price, present_price, product_id) VALUES 
-- Women's Clothing (products 44-47)
(NOW(), 85.00, 72.00, 44),
(NOW(), 120.00, 102.00, 45),
(NOW(), 65.00, 55.00, 46),
(NOW(), 150.00, 127.50, 47),

-- Men's Clothing (products 48-51)
(NOW(), 250.00, 212.50, 48),
(NOW(), 45.00, 38.25, 49),
(NOW(), 85.00, 72.25, 50),
(NOW(), 55.00, 46.75, 51),

-- Shoes (products 52-55)
(NOW(), 120.00, 102.00, 52),
(NOW(), 85.00, 72.25, 53),
(NOW(), 150.00, 127.50, 54),
(NOW(), 180.00, 153.00, 55),

-- Accessories (products 56-58)
(NOW(), 35.00, 29.75, 56),
(NOW(), 25.00, 21.25, 57),
(NOW(), 18.00, 15.30, 58),

-- Bags (products 59-61)
(NOW(), 220.00, 187.00, 59),
(NOW(), 85.00, 72.25, 60),
(NOW(), 180.00, 153.00, 61),

-- Jewelry (products 62-64)
(NOW(), 45.00, 38.25, 62),
(NOW(), 32.00, 27.20, 63),
(NOW(), 120.00, 102.00, 64);

-- Create images (using product IDs 44-64)
INSERT INTO images (image_url, product_id) VALUES 
-- Women's Clothing (products 44-47)
('https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=400&h=600&fit=crop', 44),
('https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?w=400&h=600&fit=crop', 45),
('https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=400&h=600&fit=crop', 46),
('https://images.unsplash.com/photo-1571513723812-70c8b0a4a0b4?w=400&h=600&fit=crop', 47),

-- Men's Clothing (products 48-51)
('https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&h=600&fit=crop', 48),
('https://images.unsplash.com/photo-1594938298605-c8148c4dae35?w=400&h=600&fit=crop', 49),
('https://images.unsplash.com/photo-1617137984095-74e4e5e3613f?w=400&h=600&fit=crop', 50),
('https://images.unsplash.com/photo-1624378515192-9b55318c95b9?w=400&h=600&fit=crop', 51),

-- Shoes (products 52-55)
('https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=400&h=600&fit=crop', 52),
('https://images.unsplash.com/photo-1549298916-b41d501d3772?w=400&h=600&fit=crop', 53),
('https://images.unsplash.com/photo-1544966503-7cc4ac7b5674?w=400&h=600&fit=crop', 54),
('https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=400&h=600&fit=crop', 55),

-- Accessories (products 56-58)
('https://images.unsplash.com/photo-1601924994987-69e26d50dc26?w=400&h=600&fit=crop', 56),
('https://images.unsplash.com/photo-1601924994987-69e26d50dc26?w=400&h=600&fit=crop', 57),
('https://images.unsplash.com/photo-1601924994987-69e26d50dc26?w=400&h=600&fit=crop', 58),

-- Bags (products 59-61)
('https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=600&fit=crop', 59),
('https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=600&fit=crop', 60),
('https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=600&fit=crop', 61),

-- Jewelry (products 62-64)
('https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=400&h=600&fit=crop', 62),
('https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=400&h=600&fit=crop', 63),
('https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=400&h=600&fit=crop', 64);

-- Create warehouse (using correct size IDs: 67-82)
INSERT INTO warehouse (quantity, product_id, size_id) VALUES 
-- Women's Clothing (products 44-47, sizes 67-71 for XS-XL)
(15, 44, 68), (12, 44, 69), (18, 44, 70), (10, 44, 71), -- Dress S-M-L-XL
(8, 45, 67), (15, 45, 68), (20, 45, 69), (12, 45, 70), -- Blouse XS-S-M-L
(25, 46, 68), (30, 46, 69), (22, 46, 70), (18, 46, 71), -- Jeans S-M-L-XL
(10, 47, 68), (15, 47, 69), (12, 47, 70), -- Cardigan S-M-L

-- Men's Clothing (products 48-51, sizes 69-71 for M-XL)
(20, 48, 69), (25, 48, 70), (15, 48, 71), (8, 48, 72), -- Suit M-L-XL-XXL
(30, 49, 69), (35, 49, 70), (28, 49, 71), (20, 49, 72), -- Shirt M-L-XL-XXL
(18, 50, 69), (22, 50, 70), (16, 50, 71), (12, 50, 72), -- Sweater M-L-XL-XXL
(25, 51, 69), (30, 51, 70), (20, 51, 71), (15, 51, 72), -- Pants M-L-XL-XXL

-- Shoes (products 52-55, sizes 67-82 for 36-45)
(20, 52, 67), (25, 52, 68), (30, 52, 69), (28, 52, 70), (22, 52, 71), -- Heeled Shoes 36-40
(35, 53, 67), (40, 53, 68), (45, 53, 69), (38, 53, 70), (32, 53, 71), (25, 53, 72), (20, 53, 73), -- Sneakers 36-42
(15, 54, 71), (18, 54, 72), (22, 54, 73), (20, 54, 74), (16, 54, 75), -- Chelsea Boots 40-44
(12, 55, 67), (15, 55, 68), (18, 55, 69), (16, 55, 70), (14, 55, 71), -- Platform Boots 36-40

-- Accessories (products 56-58, universal sizes)
(50, 56, 67), -- Scarf XS
(40, 57, 68), (35, 57, 69), (30, 57, 70), -- Belt S-M-L
(60, 58, 67), -- Hat XS

-- Bags (products 59-61, universal sizes)
(25, 59, 67), (20, 60, 67), (18, 61, 67), -- Bags XS

-- Jewelry (products 62-64, universal sizes)
(100, 62, 67), (80, 63, 67), (60, 64, 67); -- Jewelry XS

-- Create orders (using correct user IDs: 42-46)
INSERT INTO orders (created_at, status, total_price, user_id) VALUES 
('2024-01-15 14:30:00', 'DELIVERED', 127.00, 43), -- Anna Petrova
('2024-01-20 10:15:00', 'SHIPPED', 189.25, 44), -- Dmitry Volkov
('2024-01-25 16:45:00', 'PENDING', 85.00, 45), -- Elena Smirnova
('2024-02-01 09:20:00', 'DELIVERED', 153.00, 46), -- Mikhail Kuznetsov
('2024-02-05 13:10:00', 'SHIPPED', 212.50, 43); -- Anna Petrova

-- Create order details (using order IDs 82-86, product IDs 44-64, size IDs 67-82)
INSERT INTO order_details (price_at_purchase, quantity, order_id, product_id, size_id) VALUES 
-- Order 82 (Anna Petrova)
(72.00, 1, 82, 44, 69), -- Dress M
(55.00, 1, 82, 46, 69), -- Jeans M

-- Order 83 (Dmitry Volkov)
(212.50, 1, 83, 48, 70), -- Suit L
(102.00, 1, 83, 52, 68), -- Shoes 37
(29.75, 1, 83, 56, 67), -- Scarf XS

-- Order 84 (Elena Smirnova)
(85.00, 1, 84, 60, 67), -- Clutch XS

-- Order 85 (Mikhail Kuznetsov)
(153.00, 1, 85, 55, 72), -- Boots 42

-- Order 86 (Anna Petrova)
(127.50, 1, 86, 47, 69), -- Cardigan M
(85.00, 1, 86, 53, 68); -- Sneakers 37

-- Create payments (using order IDs 82-86, user IDs 42-46)
INSERT INTO payments (amount, payment_date, order_id, user_id) VALUES 
(127.00, '2024-01-15 14:35:00', 82, 43),
(189.25, '2024-01-20 10:20:00', 83, 44),
(85.00, '2024-01-25 16:50:00', 84, 45),
(153.00, '2024-02-01 09:25:00', 85, 46),
(212.50, '2024-02-05 13:15:00', 86, 43);

-- Create cart (using user IDs 42-46, product IDs 44-64, size IDs 67-82)
INSERT INTO cart (quantity, product_id, user_id, size_id) VALUES 
(1, 45, 43, 68), -- Anna: blouse S
(2, 49, 44, 69), -- Dmitry: 2 shirts M
(1, 59, 45, 67), -- Elena: tote bag XS
(1, 62, 46, 67); -- Mikhail: earrings XS

-- Create wishlist (using user IDs 42-46, product IDs 44-64)
INSERT INTO wishlist (product_id, user_id) VALUES 
(47, 43), -- Anna: cardigan
(50, 44), -- Dmitry: sweater
(54, 45), -- Elena: boots
(63, 46), -- Mikhail: ring
(64, 43); -- Anna: chain
