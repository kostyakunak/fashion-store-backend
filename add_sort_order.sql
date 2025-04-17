USE fashionDB;

-- Добавляем поле sort_order в таблицу images
ALTER TABLE images ADD COLUMN sort_order INT DEFAULT 0 AFTER is_main;

-- Проверяем, что поле добавлено
DESCRIBE images; 