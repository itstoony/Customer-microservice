CREATE TABLE customer (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255),
                          cpf VARCHAR(25) UNIQUE,
                          address VARCHAR(255),
                          zipcode VARCHAR(10),
                          cellphone VARCHAR(20),
                          creation_date DATE,
                          last_modified DATE,
                          users_id BIGINT,
                          CONSTRAINT fk_customer_users FOREIGN KEY (users_id) REFERENCES users(id)
);
