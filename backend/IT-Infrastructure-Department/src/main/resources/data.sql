CREATE TABLE Role (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL
);

CREATE TABLE Users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(255),
                       phone VARCHAR(50),
                       email VARCHAR(255),
                       status BOOLEAN DEFAULT TRUE,
                       failed_attempts INT,
                       account_non_locked BOOLEAN,
                       lock_time TIMESTAMP,
                       role_id INT,
                       CONSTRAINT fk_role
                           FOREIGN KEY(role_id)
                               REFERENCES Role(id)
                               ON DELETE SET NULL
);

insert into Role(name) values('ADMIN');
insert into Role(name) values('STAFF');
insert into Role(name) values('MANAGER');


