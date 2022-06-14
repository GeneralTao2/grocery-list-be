--------------------------------------------------------
--  Check Constraints for Table Products
--------------------------------------------------------

ALTER TABLE IF EXISTS products
ADD CONSTRAINT check_total_count_in_stock CHECK (total_count_in_stock >= 0);
