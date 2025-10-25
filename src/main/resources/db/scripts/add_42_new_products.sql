-- Add 42 new diverse products to the database
-- All products have English names and USD prices

-- ============================================
-- WOMEN'S CLOTHING (15 new items)
-- ============================================

INSERT INTO products (category_id, description, name) VALUES
-- Category 31: Women's Clothing
(31, 'Elegant long evening dress with sequin details, perfect for special occasions', 'Sequin Evening Gown'),
(31, 'Comfortable cotton summer dress with floral pattern and adjustable straps', 'Cotton Summer Dress'),
(31, 'Professional pencil skirt in classic black, ideal for office wear', 'Black Pencil Skirt'),
(31, 'Lightweight chiffon blouse with ruffled sleeves and delicate buttons', 'Chiffon Ruffle Blouse'),
(31, 'Stylish leather jacket with asymmetric zipper and multiple pockets', 'Leather Biker Jacket'),
(31, 'Cozy knit sweater dress with turtleneck, perfect for cold weather', 'Knit Turtleneck Dress'),
(31, 'Elegant wrap dress in burgundy with tie waist detail', 'Burgundy Wrap Dress'),
(31, 'Classic white shirt dress with button-down front and belt', 'White Shirt Dress'),
(31, 'Trendy denim jacket with distressed details and vintage wash', 'Distressed Denim Jacket'),
(31, 'Feminine lace blouse in cream with scalloped edges', 'Cream Lace Blouse'),
(31, 'Versatile midi skirt in pleated satin fabric', 'Pleated Satin Skirt'),
(31, 'Comfortable yoga pants with high waist and moisture-wicking fabric', 'High Waist Yoga Pants'),
(31, 'Elegant blazer in navy blue with tailored fit', 'Navy Tailored Blazer'),
(31, 'Romantic off-shoulder top with elastic neckline', 'Off-Shoulder Top'),
(31, 'Stylish trench coat in beige with double-breasted closure', 'Beige Trench Coat');

-- ============================================
-- MEN'S CLOTHING (12 new items)
-- ============================================

INSERT INTO products (category_id, description, name) VALUES
-- Category 32: Men's Clothing
(32, 'Premium leather jacket with quilted shoulders and inner lining', 'Premium Leather Jacket'),
(32, 'Comfortable polo shirt in navy cotton with embroidered logo', 'Navy Cotton Polo'),
(32, 'Classic denim jeans with straight leg fit and medium wash', 'Straight Leg Jeans'),
(32, 'Elegant dress pants in charcoal gray with pressed creases', 'Charcoal Dress Pants'),
(32, 'Casual henley shirt in heathered gray with button placket', 'Gray Henley Shirt'),
(32, 'Warm parka jacket with fur-trimmed hood and multiple pockets', 'Winter Parka Jacket'),
(32, 'Athletic track pants with side stripes and elastic waistband', 'Athletic Track Pants'),
(32, 'Formal vest in black with satin back panel', 'Black Formal Vest'),
(32, 'Comfortable cargo shorts with multiple pockets', 'Cargo Shorts Khaki'),
(32, 'Stylish bomber jacket in olive green with ribbed cuffs', 'Olive Bomber Jacket'),
(32, 'Classic flannel shirt in red plaid pattern', 'Red Flannel Shirt'),
(32, 'Breathable linen shirt in white for summer wear', 'White Linen Shirt');

-- ============================================
-- SHOES (8 new items)
-- ============================================

INSERT INTO products (category_id, description, name) VALUES
-- Category 33: Shoes
(33, 'Classic oxford shoes in brown leather with lace-up closure', 'Brown Oxford Shoes'),
(33, 'Comfortable running shoes with cushioned sole and mesh upper', 'Running Shoes Gray'),
(33, 'Elegant loafers in burgundy with tassel detail', 'Burgundy Loafers'),
(33, 'Trendy high-top sneakers in black canvas with white sole', 'Black High-Top Sneakers'),
(33, 'Warm winter boots with fur lining and waterproof exterior', 'Waterproof Winter Boots'),
(33, 'Stylish ankle boots with block heel and side zipper', 'Block Heel Ankle Boots'),
(33, 'Casual slip-on sneakers in navy with elastic goring', 'Navy Slip-On Sneakers'),
(33, 'Sophisticated pumps in nude with pointed toe', 'Nude Pointed Pumps');

-- ============================================
-- ACCESSORIES (4 new items)
-- ============================================

INSERT INTO products (category_id, description, name) VALUES
-- Category 34: Accessories
(34, 'Stylish sunglasses with polarized lenses and metal frame', 'Polarized Sunglasses'),
(34, 'Warm cashmere gloves in charcoal gray', 'Cashmere Gloves'),
(34, 'Classic leather wallet with multiple card slots', 'Leather Bifold Wallet'),
(34, 'Trendy baseball cap with embroidered logo', 'Embroidered Baseball Cap');

-- ============================================
-- BAGS (2 new items)
-- ============================================

INSERT INTO products (category_id, description, name) VALUES
-- Category 35: Bags
(35, 'Spacious messenger bag in canvas with leather trim', 'Canvas Messenger Bag'),
(35, 'Elegant crossbody bag in black leather with gold chain', 'Crossbody Chain Bag');

-- ============================================
-- JEWELRY (1 new item)
-- ============================================

INSERT INTO products (category_id, description, name) VALUES
-- Category 36: Jewelry
(36, 'Delicate pendant necklace with heart charm in silver', 'Silver Heart Pendant');

-- ============================================
-- CREATE PRICES FOR NEW PRODUCTS
-- ============================================

-- Get the last product ID to calculate new IDs
-- Assuming current products end at ID 64, new products start at 65

-- Women's Clothing Prices (15 items, IDs 65-79)
INSERT INTO prices (created_at, original_price, present_price, product_id) VALUES
(NOW(), 180.00, 153.00, 65),  -- Sequin Evening Gown
(NOW(), 65.00, 55.25, 66),     -- Cotton Summer Dress
(NOW(), 55.00, 46.75, 67),     -- Black Pencil Skirt
(NOW(), 75.00, 63.75, 68),     -- Chiffon Ruffle Blouse
(NOW(), 220.00, 187.00, 69),   -- Leather Biker Jacket
(NOW(), 95.00, 80.75, 70),     -- Knit Turtleneck Dress
(NOW(), 110.00, 93.50, 71),    -- Burgundy Wrap Dress
(NOW(), 85.00, 72.25, 72),     -- White Shirt Dress
(NOW(), 95.00, 80.75, 73),     -- Distressed Denim Jacket
(NOW(), 70.00, 59.50, 74),     -- Cream Lace Blouse
(NOW(), 78.00, 66.30, 75),     -- Pleated Satin Skirt
(NOW(), 48.00, 40.80, 76),     -- High Waist Yoga Pants
(NOW(), 135.00, 114.75, 77),   -- Navy Tailored Blazer
(NOW(), 42.00, 35.70, 78),     -- Off-Shoulder Top
(NOW(), 165.00, 140.25, 79);   -- Beige Trench Coat

-- Men's Clothing Prices (12 items, IDs 80-91)
INSERT INTO prices (created_at, original_price, present_price, product_id) VALUES
(NOW(), 280.00, 238.00, 80),   -- Premium Leather Jacket
(NOW(), 55.00, 46.75, 81),     -- Navy Cotton Polo
(NOW(), 75.00, 63.75, 82),     -- Straight Leg Jeans
(NOW(), 85.00, 72.25, 83),     -- Charcoal Dress Pants
(NOW(), 38.00, 32.30, 84),     -- Gray Henley Shirt
(NOW(), 195.00, 165.75, 85),   -- Winter Parka Jacket
(NOW(), 52.00, 44.20, 86),     -- Athletic Track Pants
(NOW(), 68.00, 57.80, 87),     -- Black Formal Vest
(NOW(), 45.00, 38.25, 88),     -- Cargo Shorts Khaki
(NOW(), 125.00, 106.25, 89),   -- Olive Bomber Jacket
(NOW(), 58.00, 49.30, 90),     -- Red Flannel Shirt
(NOW(), 72.00, 61.20, 91);     -- White Linen Shirt

-- Shoes Prices (8 items, IDs 92-99)
INSERT INTO prices (created_at, original_price, present_price, product_id) VALUES
(NOW(), 145.00, 123.25, 92),   -- Brown Oxford Shoes
(NOW(), 95.00, 80.75, 93),     -- Running Shoes Gray
(NOW(), 132.00, 112.20, 94),   -- Burgundy Loafers
(NOW(), 78.00, 66.30, 95),     -- Black High-Top Sneakers
(NOW(), 168.00, 142.80, 96),   -- Waterproof Winter Boots
(NOW(), 115.00, 97.75, 97),    -- Block Heel Ankle Boots
(NOW(), 65.00, 55.25, 98),     -- Navy Slip-On Sneakers
(NOW(), 125.00, 106.25, 99);   -- Nude Pointed Pumps

-- Accessories Prices (4 items, IDs 100-103)
INSERT INTO prices (created_at, original_price, present_price, product_id) VALUES
(NOW(), 95.00, 80.75, 100),    -- Polarized Sunglasses
(NOW(), 68.00, 57.80, 101),    -- Cashmere Gloves
(NOW(), 55.00, 46.75, 102),    -- Leather Bifold Wallet
(NOW(), 28.00, 23.80, 103);    -- Embroidered Baseball Cap

-- Bags Prices (2 items, IDs 104-105)
INSERT INTO prices (created_at, original_price, present_price, product_id) VALUES
(NOW(), 88.00, 74.80, 104),    -- Canvas Messenger Bag
(NOW(), 195.00, 165.75, 105);  -- Crossbody Chain Bag

-- Jewelry Prices (1 item, ID 106)
INSERT INTO prices (created_at, original_price, present_price, product_id) VALUES
(NOW(), 58.00, 49.30, 106);    -- Silver Heart Pendant

-- ============================================
-- CREATE WAREHOUSE ENTRIES FOR NEW PRODUCTS
-- ============================================

-- We'll add reasonable quantities for each size
-- Using existing size IDs: 67 (XS), 68 (S), 69 (M), 70 (L), 71 (XL), 72 (XXL), 73-82 (shoe sizes 36-45)

-- Women's Clothing (IDs 65-79) - sizes XS to XL
INSERT INTO warehouse (quantity, product_id, size_id) VALUES
-- Sequin Evening Gown (65)
(15, 65, 67), (20, 65, 68), (25, 65, 69), (20, 65, 70), (15, 65, 71),
-- Cotton Summer Dress (66)
(20, 66, 67), (25, 66, 68), (30, 66, 69), (25, 66, 70), (20, 66, 71),
-- Black Pencil Skirt (67)
(18, 67, 67), (22, 67, 68), (28, 67, 69), (22, 67, 70), (18, 67, 71),
-- Chiffon Ruffle Blouse (68)
(20, 68, 67), (25, 68, 68), (30, 68, 69), (25, 68, 70), (20, 68, 71),
-- Leather Biker Jacket (69)
(12, 69, 67), (15, 69, 68), (18, 69, 69), (15, 69, 70), (12, 69, 71),
-- Knit Turtleneck Dress (70)
(15, 70, 67), (20, 70, 68), (25, 70, 69), (20, 70, 70), (15, 70, 71),
-- Burgundy Wrap Dress (71)
(18, 71, 67), (22, 71, 68), (28, 71, 69), (22, 71, 70), (18, 71, 71),
-- White Shirt Dress (72)
(20, 72, 67), (25, 72, 68), (30, 72, 69), (25, 72, 70), (20, 72, 71),
-- Distressed Denim Jacket (73)
(15, 73, 67), (20, 73, 68), (25, 73, 69), (20, 73, 70), (15, 73, 71),
-- Cream Lace Blouse (74)
(18, 74, 67), (22, 74, 68), (28, 74, 69), (22, 74, 70), (18, 74, 71),
-- Pleated Satin Skirt (75)
(20, 75, 67), (25, 75, 68), (30, 75, 69), (25, 75, 70), (20, 75, 71),
-- High Waist Yoga Pants (76)
(25, 76, 67), (30, 76, 68), (35, 76, 69), (30, 76, 70), (25, 76, 71),
-- Navy Tailored Blazer (77)
(15, 77, 67), (20, 77, 68), (25, 77, 69), (20, 77, 70), (15, 77, 71),
-- Off-Shoulder Top (78)
(22, 78, 67), (28, 78, 68), (32, 78, 69), (28, 78, 70), (22, 78, 71),
-- Beige Trench Coat (79)
(12, 79, 67), (15, 79, 68), (18, 79, 69), (15, 79, 70), (12, 79, 71);

-- Men's Clothing (IDs 80-91) - sizes S to XXL
INSERT INTO warehouse (quantity, product_id, size_id) VALUES
-- Premium Leather Jacket (80)
(10, 80, 68), (15, 80, 69), (18, 80, 70), (15, 80, 71), (10, 80, 72),
-- Navy Cotton Polo (81)
(20, 81, 68), (25, 81, 69), (30, 81, 70), (25, 81, 71), (20, 81, 72),
-- Straight Leg Jeans (82)
(18, 82, 68), (22, 82, 69), (28, 82, 70), (22, 82, 71), (18, 82, 72),
-- Charcoal Dress Pants (83)
(15, 83, 68), (20, 83, 69), (25, 83, 70), (20, 83, 71), (15, 83, 72),
-- Gray Henley Shirt (84)
(22, 84, 68), (28, 84, 69), (32, 84, 70), (28, 84, 71), (22, 84, 72),
-- Winter Parka Jacket (85)
(12, 85, 68), (15, 85, 69), (18, 85, 70), (15, 85, 71), (12, 85, 72),
-- Athletic Track Pants (86)
(20, 86, 68), (25, 86, 69), (30, 86, 70), (25, 86, 71), (20, 86, 72),
-- Black Formal Vest (87)
(15, 87, 68), (20, 87, 69), (25, 87, 70), (20, 87, 71), (15, 87, 72),
-- Cargo Shorts Khaki (88)
(18, 88, 68), (22, 88, 69), (28, 88, 70), (22, 88, 71), (18, 88, 72),
-- Olive Bomber Jacket (89)
(12, 89, 68), (15, 89, 69), (18, 89, 70), (15, 89, 71), (12, 89, 72),
-- Red Flannel Shirt (90)
(20, 90, 68), (25, 90, 69), (30, 90, 70), (25, 90, 71), (20, 90, 72),
-- White Linen Shirt (91)
(18, 91, 68), (22, 91, 69), (28, 91, 70), (22, 91, 71), (18, 91, 72);

-- Shoes (IDs 92-99) - shoe sizes 36-42
INSERT INTO warehouse (quantity, product_id, size_id) VALUES
-- Brown Oxford Shoes (92)
(12, 92, 73), (15, 92, 74), (18, 92, 75), (20, 92, 76), (18, 92, 77), (15, 92, 78), (12, 92, 79),
-- Running Shoes Gray (93)
(15, 93, 73), (18, 93, 74), (22, 93, 75), (25, 93, 76), (22, 93, 77), (18, 93, 78), (15, 93, 79),
-- Burgundy Loafers (94)
(10, 94, 73), (12, 94, 74), (15, 94, 75), (18, 94, 76), (15, 94, 77), (12, 94, 78), (10, 94, 79),
-- Black High-Top Sneakers (95)
(18, 95, 73), (22, 95, 74), (25, 95, 75), (28, 95, 76), (25, 95, 77), (22, 95, 78), (18, 95, 79),
-- Waterproof Winter Boots (96)
(12, 96, 73), (15, 96, 74), (18, 96, 75), (20, 96, 76), (18, 96, 77), (15, 96, 78), (12, 96, 79),
-- Block Heel Ankle Boots (97)
(15, 97, 73), (18, 97, 74), (22, 97, 75), (25, 97, 76), (22, 97, 77), (18, 97, 78), (15, 97, 79),
-- Navy Slip-On Sneakers (98)
(18, 98, 73), (22, 98, 74), (25, 98, 75), (28, 98, 76), (25, 98, 77), (22, 98, 78), (18, 98, 79),
-- Nude Pointed Pumps (99)
(15, 99, 73), (18, 99, 74), (22, 99, 75), (25, 99, 76), (22, 99, 77), (18, 99, 78), (15, 99, 79);

-- Accessories (IDs 100-103) - one size (XS)
INSERT INTO warehouse (quantity, product_id, size_id) VALUES
(50, 100, 67), -- Polarized Sunglasses
(40, 101, 67), -- Cashmere Gloves
(45, 102, 67), -- Leather Bifold Wallet
(60, 103, 67); -- Embroidered Baseball Cap

-- Bags (IDs 104-105) - one size (XS)
INSERT INTO warehouse (quantity, product_id, size_id) VALUES
(30, 104, 67), -- Canvas Messenger Bag
(25, 105, 67); -- Crossbody Chain Bag

-- Jewelry (ID 106) - one size (XS)
INSERT INTO warehouse (quantity, product_id, size_id) VALUES
(35, 106, 67); -- Silver Heart Pendant
