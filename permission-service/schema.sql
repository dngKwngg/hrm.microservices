-- Table: Role
CREATE TABLE role (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL UNIQUE
);

-- Table: Permission
CREATE TABLE permission (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE
);

-- Table: Role_Permission (many-to-many relationship)
CREATE TABLE role_permission (
                                 role_id INT NOT NULL,
                                 permission_id INT NOT NULL,
                                 PRIMARY KEY (role_id, permission_id),

                                 CONSTRAINT fk_role
                                     FOREIGN KEY (role_id) REFERENCES role(id)
                                         ON DELETE CASCADE,

                                 CONSTRAINT fk_permission
                                     FOREIGN KEY (permission_id) REFERENCES permission(id)
                                         ON DELETE CASCADE
);