--------------------------------------------------------
--  Check Constraints for Table Cart-Products
--------------------------------------------------------

ALTER TABLE IF EXISTS cart_products
ADD CONSTRAINT check_quantity CHECK (quantity > 0);