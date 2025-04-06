DROP TRIGGER IF EXISTS `after_order_details_update`;

DELIMITER //

CREATE TRIGGER `after_order_details_update` 
AFTER INSERT ON `order_details` 
FOR EACH ROW 
BEGIN
    UPDATE orders o
    SET o.total_price = (
        SELECT SUM(od.quantity * od.price_at_purchase)
        FROM order_details od
        WHERE od.order_id = o.id
    )
    WHERE o.id = NEW.order_id;
END//

DELIMITER ; 