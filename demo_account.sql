-- Demo Account для GitHub Зрителей
-- Этот скрипт создает демонстрационные аккаунты для тестирования приложения

-- 1. Админ аккаунт для тестирования админ панели
-- Email: github-demo@fashionstore.com
-- Password: GitHubDemo2024!
INSERT INTO users (first_name, last_name, email, phone, password, role, enabled, created_at) VALUES
('GitHub', 'Demo', 'github-demo@fashionstore.com', '+380123456789', 
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 
 'ADMIN', TRUE, NOW());

-- 2. Обычный пользователь для тестирования функционала
-- Email: user@fashionstore.com  
-- Password: UserPass1234!
INSERT INTO users (first_name, last_name, email, phone, password, role, enabled, created_at) VALUES
('John', 'Doe', 'user@fashionstore.com', '+380987654321',
 '$2a$10$rDmGcWxX5J5J5J5J5J5J5OeZAgcfl7p92ldGxad68LJZdL17lhWy',
 'USER', TRUE, NOW());

-- 3. Создание тестовых адресов для пользователя
-- Получаем ID созданных пользователей
SET @user_id = LAST_INSERT_ID() - 1;
SET @admin_id = LAST_INSERT_ID();

-- Адрес для обычного пользователя
INSERT INTO addresses (city, country, postal_code, recipient_first_name, recipient_last_name, street, user_id, is_main) VALUES
('Київ', 'Україна', '01001', 'John', 'Doe', 'вул. Хрещатик, 1, кв. 10', 
 (SELECT id FROM users WHERE email = 'user@fashionstore.com'), TRUE);

-- Адрес для админа
INSERT INTO addresses (city, country, postal_code, recipient_first_name, recipient_last_name, street, user_id, is_main) VALUES
('Київ', 'Україна', '01001', 'GitHub', 'Demo', 'вул. Незалежності, 25, кв. 5',
 (SELECT id FROM users WHERE email = 'github-demo@fashionstore.com'), TRUE);

-- Инструкция по использованию демо аккаунтов:
-- 
-- АДМИН АККАУНТ:
-- Email: github-demo@fashionstore.com
-- Password: GitHubDemo2024!
-- Роль: ADMIN (полный доступ к админ панели)
-- 
-- ОБЫЧНЫЙ ПОЛЬЗОВАТЕЛЬ:
-- Email: user@fashionstore.com
-- Password: UserPass1234!
-- Роль: USER (пользовательские функции)
-- 
-- Важно: Эти аккаунты созданы для демонстрации функциональности.
-- Просьба не изменять пароли и не удалять данные.

