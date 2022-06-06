CREATE SEQUENCE IF NOT EXISTS address_seq;

CREATE TABLE IF NOT EXISTS address
(
    id              BIGSERIAL PRIMARY KEY,
    address         VARCHAR(128),
    addressDetails  VARCHAR(64),
    postalCode      VARCHAR(8),
    city            VARCHAR(24),
    country         VARCHAR(24)
);

ALTER SEQUENCE address_seq OWNED BY address.id;