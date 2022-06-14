CREATE SEQUENCE IF NOT EXISTS user_seq
MINVALUE 100
START WITH 100
INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS users
(
    id       INTEGER PRIMARY KEY,
    email    VARCHAR(25)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role     VARCHAR(20)  NOT NULL
);

ALTER SEQUENCE user_seq OWNED BY users.id;