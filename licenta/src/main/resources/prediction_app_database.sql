DROP DATABASE prediction_app_database;
DROP USER prediction_app_user;

CREATE USER prediction_app_user WITH PASSWORD 'password';
CREATE DATABASE prediction_app_database WITH template=template0 OWNER=prediction_app_user;
\c prediction_app_database;
ALTER DEFAULT PRIVILEGES GRANT ALL ON TABLES TO prediction_app_user;
ALTER DEFAULT PRIVILEGES GRANT ALL ON SEQUENCES TO prediction_app_user;

CREATE TABLE USERS (
    user_id INTEGER PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    email VARCHAR(256) NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE DRUG (
    drug_id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    date_of_usage DATE NOT NULL,
    price FLOAT NOT NULL
);

CREATE SEQUENCE user_sequence INCREMENT 1 START 1;