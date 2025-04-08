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