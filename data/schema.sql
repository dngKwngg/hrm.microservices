-- Table: Role
CREATE TABLE roles (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL UNIQUE,
                      created_at TIMESTAMP NOT NULL,
                      updated_at TIMESTAMP
);

-- Table: Permission
CREATE TABLE permissions (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE,
                            created_at TIMESTAMP NOT NULL,
                            updated_at TIMESTAMP
);

-- Table: Role_Permission (many-to-many relationship)
CREATE TABLE role_permissions (
                                 role_id INT NOT NULL,
                                 permission_id INT NOT NULL,
                                 created_at TIMESTAMP NOT NULL,
                                 updated_at TIMESTAMP,
                                 PRIMARY KEY (role_id, permission_id),

                                 CONSTRAINT fk_role
                                     FOREIGN KEY (role_id) REFERENCES roles(id)
                                         ON DELETE CASCADE,

                                 CONSTRAINT fk_permission
                                     FOREIGN KEY (permission_id) REFERENCES permissions(id)
                                         ON DELETE CASCADE
);

-- Table: Departments
CREATE TABLE IF NOT EXISTS departments (
                                           id BIGSERIAL PRIMARY KEY,
                                           name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
    );

-- Table: Users

CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role_id INTEGER NOT NULL,
    department_id INTEGER NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
    );

-- Add foreign key constraint to the table users and set the foreign key to the table roles
ALTER TABLE users
    ADD CONSTRAINT fk_users_roles
        FOREIGN KEY (role_id)
            REFERENCES roles (id)
            ON DELETE CASCADE;

-- Add foreign key constraint to the table users and set the foreign key to the table departments
ALTER TABLE users
    ADD CONSTRAINT fk_users_departments
        FOREIGN KEY (department_id)
            REFERENCES departments (id)
            ON DELETE CASCADE;