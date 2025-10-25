-- Добавляем уникальные качественные изображения для каждого товара
-- Каждый товар получает свои специфические изображения

-- Women's Clothing (products 44-47)
INSERT INTO images (image_url, product_id, is_main, sort_order) VALUES 
-- Floral Print Midi Dress (44) - платье с цветочным принтом
('https://images.pexels.com/photos/1043474/pexels-photo-1043474.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 44, 1, 1),
('https://images.pexels.com/photos/1043473/pexels-photo-1043473.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 44, 0, 2),
('https://images.pexels.com/photos/1043472/pexels-photo-1043472.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 44, 0, 3),

-- Silk Blouse (45) - шелковая блузка
('https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 45, 1, 1),
('https://images.pexels.com/photos/996330/pexels-photo-996330.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 45, 0, 2),
('https://images.pexels.com/photos/996331/pexels-photo-996331.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 45, 0, 3),

-- Skinny Jeans (46) - узкие джинсы
('https://images.pexels.com/photos/996332/pexels-photo-996332.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 46, 1, 1),
('https://images.pexels.com/photos/996333/pexels-photo-996333.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 46, 0, 2),
('https://images.pexels.com/photos/996334/pexels-photo-996334.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 46, 0, 3),

-- Cashmere Cardigan (47) - кашемировый кардиган
('https://images.pexels.com/photos/996335/pexels-photo-996335.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 47, 1, 1),
('https://images.pexels.com/photos/996336/pexels-photo-996336.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 47, 0, 2),
('https://images.pexels.com/photos/996337/pexels-photo-996337.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 47, 0, 3),

-- Men's Clothing (products 48-51)
-- Classic Suit (48) - классический костюм
('https://images.pexels.com/photos/996338/pexels-photo-996338.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 48, 1, 1),
('https://images.pexels.com/photos/996339/pexels-photo-996339.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 48, 0, 2),
('https://images.pexels.com/photos/996340/pexels-photo-996340.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 48, 0, 3),

-- Oxford Shirt (49) - оксфордская рубашка
('https://images.pexels.com/photos/996341/pexels-photo-996341.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 49, 1, 1),
('https://images.pexels.com/photos/996342/pexels-photo-996342.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 49, 0, 2),
('https://images.pexels.com/photos/996343/pexels-photo-996343.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 49, 0, 3),

-- Merino Wool Sweater (50) - свитер из мериносовой шерсти
('https://images.pexels.com/photos/996344/pexels-photo-996344.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 50, 1, 1),
('https://images.pexels.com/photos/996345/pexels-photo-996345.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 50, 0, 2),
('https://images.pexels.com/photos/996346/pexels-photo-996346.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 50, 0, 3),

-- Chino Pants (51) - чинос
('https://images.pexels.com/photos/996347/pexels-photo-996347.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 51, 1, 1),
('https://images.pexels.com/photos/996348/pexels-photo-996348.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 51, 0, 2),
('https://images.pexels.com/photos/996349/pexels-photo-996349.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 51, 0, 3),

-- Shoes (products 52-55)
-- Heeled Shoes (52) - туфли на каблуке
('https://images.pexels.com/photos/996350/pexels-photo-996350.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 52, 1, 1),
('https://images.pexels.com/photos/996351/pexels-photo-996351.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 52, 0, 2),
('https://images.pexels.com/photos/996352/pexels-photo-996352.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 52, 0, 3),

-- White Sneakers (53) - белые кроссовки
('https://images.pexels.com/photos/996353/pexels-photo-996353.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 53, 1, 1),
('https://images.pexels.com/photos/996354/pexels-photo-996354.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 53, 0, 2),
('https://images.pexels.com/photos/996355/pexels-photo-996355.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 53, 0, 3),

-- Chelsea Boots (54) - челси ботинки
('https://images.pexels.com/photos/996356/pexels-photo-996356.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 54, 1, 1),
('https://images.pexels.com/photos/996357/pexels-photo-996357.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 54, 0, 2),
('https://images.pexels.com/photos/996358/pexels-photo-996358.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 54, 0, 3),

-- Platform Boots (55) - ботинки на платформе
('https://images.pexels.com/photos/996359/pexels-photo-996359.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 55, 1, 1),
('https://images.pexels.com/photos/996360/pexels-photo-996360.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 55, 0, 2),
('https://images.pexels.com/photos/996361/pexels-photo-996361.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 55, 0, 3),

-- Accessories (products 56-58)
-- Silk Scarf (56) - шелковый шарф
('https://images.pexels.com/photos/996362/pexels-photo-996362.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 56, 1, 1),
('https://images.pexels.com/photos/996363/pexels-photo-996363.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 56, 0, 2),
('https://images.pexels.com/photos/996364/pexels-photo-996364.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 56, 0, 3),

-- Leather Belt (57) - кожаный ремень
('https://images.pexels.com/photos/996365/pexels-photo-996365.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 57, 1, 1),
('https://images.pexels.com/photos/996366/pexels-photo-996366.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 57, 0, 2),
('https://images.pexels.com/photos/996367/pexels-photo-996367.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 57, 0, 3),

-- Wool Hat (58) - шерстяная шапка
('https://images.pexels.com/photos/996368/pexels-photo-996368.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 58, 1, 1),
('https://images.pexels.com/photos/996369/pexels-photo-996369.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 58, 0, 2),
('https://images.pexels.com/photos/996370/pexels-photo-996370.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 58, 0, 3),

-- Bags (products 59-61)
-- Leather Tote Bag (59) - кожаная сумка-тоут
('https://images.pexels.com/photos/996371/pexels-photo-996371.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 59, 1, 1),
('https://images.pexels.com/photos/996372/pexels-photo-996372.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 59, 0, 2),
('https://images.pexels.com/photos/996373/pexels-photo-996373.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 59, 0, 3),

-- Chain Clutch (60) - клатч с цепочкой
('https://images.pexels.com/photos/996374/pexels-photo-996374.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 60, 1, 1),
('https://images.pexels.com/photos/996375/pexels-photo-996375.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 60, 0, 2),
('https://images.pexels.com/photos/996376/pexels-photo-996376.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 60, 0, 3),

-- Leather Backpack (61) - кожаный рюкзак
('https://images.pexels.com/photos/996377/pexels-photo-996377.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 61, 1, 1),
('https://images.pexels.com/photos/996378/pexels-photo-996378.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 61, 0, 2),
('https://images.pexels.com/photos/996379/pexels-photo-996379.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 61, 0, 3),

-- Jewelry (products 62-64)
-- Silver Earrings (62) - серебряные серьги
('https://images.pexels.com/photos/996380/pexels-photo-996380.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 62, 1, 1),
('https://images.pexels.com/photos/996381/pexels-photo-996381.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 62, 0, 2),
('https://images.pexels.com/photos/996382/pexels-photo-996382.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 62, 0, 3),

-- Stone Ring (63) - кольцо с камнем
('https://images.pexels.com/photos/996383/pexels-photo-996383.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 63, 1, 1),
('https://images.pexels.com/photos/996384/pexels-photo-996384.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 63, 0, 2),
('https://images.pexels.com/photos/996385/pexels-photo-996385.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 63, 0, 3),

-- Gold Chain (64) - золотая цепочка
('https://images.pexels.com/photos/996386/pexels-photo-996386.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 64, 1, 1),
('https://images.pexels.com/photos/996387/pexels-photo-996387.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 64, 0, 2),
('https://images.pexels.com/photos/996388/pexels-photo-996388.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop', 64, 0, 3);
