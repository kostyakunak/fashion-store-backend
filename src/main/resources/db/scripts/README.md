# SQL Scripts

This directory contains SQL scripts for database management.

## Files Description

1. `create_trigger.sql` - Creates triggers for automatic order total price calculation
2. `fill_database.sql` - Contains sample data for initial database population
3. `update_triggers.sql` - Updates existing triggers with new logic

## Usage

These scripts should be executed manually when needed:

1. For initial database setup:
   ```sql
   source fill_database.sql
   ```

2. To create or update triggers:
   ```sql
   source create_trigger.sql
   # or
   source update_triggers.sql
   ```

## Important Notes

- Always backup your database before running these scripts
- Make sure you're connected to the correct database
- Scripts should be executed in the correct order if dependencies exist 

## Планы на будущее

- Внедрение refresh-токенов для аутентификации:
  - Access-токен (JWT) будет жить ограниченное время (например, 15-60 минут).
  - Refresh-токен будет использоваться для автоматического продления сессии пользователя без повторного логина.
  - Это повысит безопасность (можно отозвать refresh-токен при подозрении на взлом) и удобство (пользователь не будет разлогиниваться даже после истечения access-токена).
  - Подробнее: реализовать отдельный endpoint для обновления access-токена по refresh-токену, хранить refresh-токен безопасно на клиенте и валидировать на сервере. 