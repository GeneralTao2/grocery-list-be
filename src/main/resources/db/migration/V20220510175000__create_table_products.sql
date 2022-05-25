CREATE SEQUENCE IF NOT EXISTS product_seq;

CREATE TABLE IF NOT EXISTS products
(
    id              BIGSERIAL PRIMARY KEY,
    description     VARCHAR(255),
    image   VARCHAR(255),
    name            VARCHAR(40),
    price           DECIMAL(8,2),
    rate            DECIMAL(3,2) DEFAULT 0
    CONSTRAINT chk_price    CHECK (price >= 0 AND price < 100000),
    CONSTRAINT chk_rate     CHECK (rate >= 0 AND rate <= 5)
);

ALTER SEQUENCE product_seq OWNED BY products.id;
