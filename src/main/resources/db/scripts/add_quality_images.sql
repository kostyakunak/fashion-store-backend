-- Добавляем качественные изображения для всех товаров
-- Используем Pexels для получения качественных фотографий модных товаров

-- Women's Clothing (products 44-47)
INSERT INTO images (image_url, product_id, is_main, sort_order) VALUES 
-- Floral Print Midi Dress (44) - 3 изображения
('https://images.pexels.com/photos/1043474/pexels-photo-1043474.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 44, 1, 1),
('https://images.pexels.com/photos/1043473/pexels-photo-1043473.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 44, 0, 2),
('https://images.pexels.com/photos/1043472/pexels-photo-1043472.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 44, 0, 3),

-- Silk Blouse (45) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 45, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 45, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 45, 0, 3),

-- Skinny Jeans (46) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 46, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 46, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 46, 0, 3),

-- Cashmere Cardigan (47) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 47, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 47, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 47, 0, 3),

-- Men's Clothing (products 48-51)
-- Classic Suit (48) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 48, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 48, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 48, 0, 3),

-- Oxford Shirt (49) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 49, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 49, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 49, 0, 3),

-- Merino Wool Sweater (50) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 50, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 50, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 50, 0, 3),

-- Chino Pants (51) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 51, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 51, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 51, 0, 3),

-- Shoes (products 52-55)
-- Heeled Shoes (52) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 52, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 52, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 52, 0, 3),

-- White Sneakers (53) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 53, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 53, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 53, 0, 3),

-- Chelsea Boots (54) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 54, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 54, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 54, 0, 3),

-- Platform Boots (55) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 55, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 55, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 55, 0, 3),

-- Accessories (products 56-58)
-- Silk Scarf (56) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 56, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 56, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 56, 0, 3),

-- Leather Belt (57) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 57, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 57, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 57, 0, 3),

-- Wool Hat (58) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 58, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 58, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 58, 0, 3),

-- Bags (products 59-61)
-- Leather Tote Bag (59) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 59, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 59, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 59, 0, 3),

-- Chain Clutch (60) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 60, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 60, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 60, 0, 3),

-- Leather Backpack (61) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 61, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 61, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 61, 0, 3),

-- Jewelry (products 62-64)
-- Silver Earrings (62) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 62, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 62, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 62, 0, 3),

-- Stone Ring (63) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 63, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 63, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 63, 0, 3),

-- Gold Chain (64) - 3 изображения
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 64, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 64, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 64, 0, 3);
