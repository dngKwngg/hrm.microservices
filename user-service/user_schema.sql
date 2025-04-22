-- Create users table using VARCHAR for role with CHECK constraint
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(10) NOT NULL CHECK (role IN ('ADMIN', 'USER')),  -- Using VARCHAR with CHECK constraint
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
    );

-- ADD department_id BIGINT,
ALTER TABLE users
    ADD department_id BIGINT;


ALTER TABLE users ADD COLUMN role_id INTEGER;

ALTER TABLE users DROP COLUMN role;