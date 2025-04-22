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
    department_id BIGINT,
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


INSERT INTO public.departments (id, name, description, created_at, updated_at) VALUES (1, 'VTI.D2', 'Division 2', '2025-04-02 10:58:13.472492', null);
INSERT INTO public.departments (id, name, description, created_at, updated_at) VALUES (2, 'VTI.D3', 'Division 3', '2025-04-02 10:58:17.833130', null);
INSERT INTO public.departments (id, name, description, created_at, updated_at) VALUES (3, 'VTI.D5', 'Division 5', '2025-04-02 10:58:21.630068', null);


INSERT INTO public.roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO public.roles (id, name) VALUES (2, 'USER');


INSERT INTO public.permissions (id, name) VALUES (4, 'UPDATE_DEPARTMENT');
INSERT INTO public.permissions (id, name) VALUES (5, 'DELETE_DEPARTMENT');
INSERT INTO public.permissions (id, name) VALUES (6, 'READ_USERS_IN_DEPARTMENT');
INSERT INTO public.permissions (id, name) VALUES (7, 'READ_USER_BY_USER_ID');
INSERT INTO public.permissions (id, name) VALUES (8, 'READ_CURRENT_USER');
INSERT INTO public.permissions (id, name) VALUES (9, 'ASSIGN_USER_AS_ADMIN');
INSERT INTO public.permissions (id, name) VALUES (10, 'UPDATE_USER_BY_USER_ID');
INSERT INTO public.permissions (id, name) VALUES (11, 'UPDATE_CURRENT_USER');
INSERT INTO public.permissions (id, name) VALUES (12, 'ASSIGN_USER_TO_DEPARTMENT');
INSERT INTO public.permissions (id, name) VALUES (13, 'DELETE_USER_BY_USER_ID');
INSERT INTO public.permissions (id, name) VALUES (2, 'READ_ALL_DEPARTMENTS');
INSERT INTO public.permissions (id, name) VALUES (3, 'CREATE_DEPARTMENT');
INSERT INTO public.permissions (id, name) VALUES (1, 'READ_ALL_USERS');


INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 2);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 3);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 4);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 5);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 6);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 7);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 8);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (2, 8);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 9);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 10);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 11);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (2, 11);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 12);
INSERT INTO public.role_permission (role_id, permission_id) VALUES (1, 13);


INSERT INTO public.users (id, username, password, first_name, last_name, email, active, created_at, updated_at, department_id, role_id) VALUES (1, 'quangnguyenvk@gmail.com', '$2a$12$zXWR5Gyt/FMImoS3yWmPhejGm0aUN/PZ9SImv25ZBDCJiylT3YE1G', 'Nguyen Dang', 'Quang', 'quangnguyenvk@gmail.com', true, '2025-04-02 10:49:21.995403', '2025-04-02 11:40:47.471271', 1, 1);
INSERT INTO public.users (id, username, password, first_name, last_name, email, active, created_at, updated_at, department_id, role_id) VALUES (5, 'lai@gmail.com', '$2a$12$zXWR5Gyt/FMImoS3yWmPhejGm0aUN/PZ9SImv25ZBDCJiylT3YE1G', 'Nguyen Nhat', 'Lai', 'lai@gmail.com', true, '2025-04-02 10:58:44.505051', '2025-04-02 11:43:07.592883', 2, 2);
INSERT INTO public.users (id, username, password, first_name, last_name, email, active, created_at, updated_at, department_id, role_id) VALUES (9, 'vinh@gmail.com', '$2a$10$8SPifCwik7xv1QU.M7hwp.atiK5uKlxGRbNF3bjD9WLKB4t2mNfhi', 'Nguyen Dang', 'Vinh', 'hai@gmail.com', true, '2025-04-22 11:12:18.067297', null, 3, 2);
