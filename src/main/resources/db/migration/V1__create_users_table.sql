CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(255) UNIQUE,
                       password VARCHAR(255),
                       creation_date DATE,
                       last_modified DATE
);
