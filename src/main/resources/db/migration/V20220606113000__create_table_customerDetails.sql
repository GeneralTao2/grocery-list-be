CREATE SEQUENCE IF NOT EXISTS customerDetails_seq;

CREATE TABLE IF NOT EXISTS customerDetails
(
    id              BIGSERIAL PRIMARY KEY,
    firstName       VARCHAR(24),
    lastName        VARCHAR(24),
    keepMeInTouch   BIT,
    addressId       BIGSERIAL FOREIGN KEY
);

ALTER SEQUENCE customerDetails_seq OWNED BY customerDetails.id;