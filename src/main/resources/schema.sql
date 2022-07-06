CREATE TABLE users
(
    id        serial PRIMARY KEY,
    username  VARCHAR(255),
    password  VARCHAR(255),
    email     VARCHAR(255),
    createdAt timestamp
);

CREATE TABLE transaction_history
(
    id        serial PRIMARY KEY,
    type      VARCHAR(255),
    createdAt timestamp
);