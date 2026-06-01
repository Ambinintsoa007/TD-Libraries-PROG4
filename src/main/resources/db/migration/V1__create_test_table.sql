CREATE TABLE IF NOT EXISTS connection_test (
                                               id SERIAL PRIMARY KEY,
                                               message TEXT NOT NULL
);

INSERT INTO connection_test (message)
VALUES ('Spring Boot connected to Neon');