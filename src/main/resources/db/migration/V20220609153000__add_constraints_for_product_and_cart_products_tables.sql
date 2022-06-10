ALTER TABLE products ALTER COLUMN image SET DEFAULT 'https://static.hertz-audio.com/media/2021/05/no-product-image.png';

ALTER TABLE products ALTER COLUMN price SET NOT NULL;

ALTER TABLE products ALTER COLUMN size SET NOT NULL;

ALTER TABLE products ADD CONSTRAINT chk_size CHECK (size > 0);

ALTER TABLE products ADD CONSTRAINT chk_type CHECK (type IN ('WEIGHABLE','PACKABLE'));

ALTER TABLE products ADD CONSTRAINT chk_sold_products CHECK (count_of_sold_products >= 0);

ALTER TABLE products ADD CONSTRAINT chk_category CHECK (category IN ('FRUITS','VEGETABLES', 'MEAT', 'BAKERY'));

ALTER TABLE cart_products ADD CONSTRAINT pk_cart_products PRIMARY KEY (cart_id, products_id);
