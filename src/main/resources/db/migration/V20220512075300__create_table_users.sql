CREATE SEQUENCE IF NOT EXISTS user_seq;

CREATE TABLE IF NOT EXISTS users
(
    id              BIGSERIAL PRIMARY KEY,
    email           VARCHAR(25) NOT NULL UNIQUE,
    password        VARCHAR(100) NOT NULL,
    roles           VARCHAR(20) NOT NULL
);

ALTER SEQUENCE user_seq OWNED BY users.id;