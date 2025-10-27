-- Добавляем тестовые данные в Railway базу данных

-- Добавляем категории
INSERT INTO categories (id, name) VALUES 
(1, 'Футболки'),
(2, 'Штаны'),
(3, 'Куртки'),
(4, 'Аксессуары');

-- Добавляем товары
INSERT INTO products (id, name, description, price, category_id) VALUES 
(1, 'Белая футболка', 'Классическая белая футболка из хлопка', 1500, 1),
(2, 'Черные джинсы', 'Стильные черные джинсы', 3500, 2),
(3, 'Красная куртка', 'Яркая красная куртка', 5500, 3),
(4, 'Белая рубашка', 'Элегантная белая рубашка', 2500, 1),
(5, 'Синие шорты', 'Удобные синие шорты', 2000, 2);

-- Добавляем размеры
INSERT INTO sizes (id, name, product_id) VALUES 
(1, 'S', 1),
(2, 'M', 1),
(3, 'L', 1),
(4, 'XL', 1),
(5, 'S', 2),
(6, 'M', 2),
(7, 'L', 2),
(8, 'S', 3),
(9, 'M', 3),
(10, 'L', 3);

-- Добавляем изображения
INSERT INTO images (id, image_url, product_id) VALUES 
(1, 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400&h=400&fit=crop', 1),
(2, 'https://images.unsplash.com/photo-1542272604-787c3835535d?w=400&h=400&fit=crop', 2),
(3, 'https://images.unsplash.com/photo-1551028719-00167b16eac5?w=400&h=400&fit=crop', 3),
(4, 'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=400&h=400&fit=crop', 4),
(5, 'https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?w=400&h=400&fit=crop', 5);

-- Добавляем складские данные
INSERT INTO warehouse (id, product_id, size_id, quantity, in_stock) VALUES 
(1, 1, 1, 10, true),
(2, 1, 2, 15, true),
(3, 1, 3, 8, true),
(4, 1, 4, 5, true),
(5, 2, 5, 12, true),
(6, 2, 6, 18, true),
(7, 2, 7, 7, true),
(8, 3, 8, 6, true),
(9, 3, 9, 9, true),
(10, 3, 10, 4, true);




