DROP DATABASE prediction_app_database;
DROP USER prediction_app_user;

CREATE USER prediction_app_user WITH PASSWORD 'password';
CREATE DATABASE prediction_app_database WITH template=template0 OWNER=prediction_app_user;
\c prediction_app_database;
ALTER DEFAULT PRIVILEGES GRANT ALL ON TABLES TO prediction_app_user;
ALTER DEFAULT PRIVILEGES GRANT ALL ON SEQUENCES TO prediction_app_user;

CREATE TABLE USERS (
    user_id INTEGER serial,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    email VARCHAR(256) NOT NULL,
    password TEXT NOT NULL
);
