-- Fix sizes for old clothing products (IDs 44-51)
-- Remove shoe sizes and add correct clothing sizes

-- Delete incorrect warehouse entries for old clothing products
DELETE FROM warehouse WHERE product_id IN (44, 45, 46, 47, 48, 49, 50, 51);

-- ============================================
-- Women's Clothing (IDs 44-47) - sizes XS to XL (IDs 61-65)
-- ============================================

INSERT INTO warehouse (quantity, product_id, size_id) VALUES
-- Floral Print Midi Dress (44)
(15, 44, 61), (20, 44, 62), (25, 44, 63), (20, 44, 64), (15, 44, 65),
-- Silk Blouse (45)
(18, 45, 61), (22, 45, 62), (28, 45, 63), (22, 45, 64), (18, 45, 65),
-- Skinny Jeans (46)
(20, 46, 61), (25, 46, 62), (30, 46, 63), (25, 46, 64), (20, 46, 65),
-- Cashmere Cardigan (47)
(15, 47, 61), (20, 47, 62), (25, 47, 63), (20, 47, 64), (15, 47, 65);

-- ============================================
-- Men's Clothing (IDs 48-51) - sizes S to XXL (IDs 62-66)
-- ============================================

INSERT INTO warehouse (quantity, product_id, size_id) VALUES
-- Classic Suit (48)
(10, 48, 62), (15, 48, 63), (18, 48, 64), (15, 48, 65), (10, 48, 66),
-- Oxford Shirt (49)
(20, 49, 62), (25, 49, 63), (30, 49, 64), (25, 49, 65), (20, 49, 66),
-- Merino Wool Sweater (50)
(18, 50, 62), (22, 50, 63), (28, 50, 64), (22, 50, 65), (18, 50, 66),
-- Chino Pants (51)
(20, 51, 62), (25, 51, 63), (30, 51, 64), (25, 51, 65), (20, 51, 66);
