USE fashionDB;

-- Удаляем существующие триггеры
DROP TRIGGER IF EXISTS after_order_details_insert;
DROP TRIGGER IF EXISTS after_order_details_update;
DROP TRIGGER IF EXISTS after_order_details_delete;

DELIMITER //

-- Триггер для INSERT
CREATE TRIGGER after_order_details_insert 
AFTER INSERT ON order_details 
FOR EACH ROW 
BEGIN
    UPDATE orders o
    SET o.total_price = COALESCE((
        SELECT SUM(od.quantity * od.price_at_purchase)
        FROM order_details od
        WHERE od.order_id = o.id
    ), 0)
    WHERE o.id = NEW.order_id;
END//

-- Триггер для UPDATE
CREATE TRIGGER after_order_details_update 
AFTER UPDATE ON order_details 
FOR EACH ROW 
BEGIN
    UPDATE orders o
    SET o.total_price = COALESCE((
        SELECT SUM(od.quantity * od.price_at_purchase)
        FROM order_details od
        WHERE od.order_id = o.id
    ), 0)
    WHERE o.id = NEW.order_id OR o.id = OLD.order_id;
END//

-- Триггер для DELETE
CREATE TRIGGER after_order_details_delete 
AFTER DELETE ON order_details 
FOR EACH ROW 
BEGIN
    UPDATE orders o
    SET o.total_price = COALESCE((
        SELECT SUM(od.quantity * od.price_at_purchase)
        FROM order_details od
        WHERE od.order_id = o.id
    ), 0)
    WHERE o.id = OLD.order_id;
END//

DELIMITER ; 