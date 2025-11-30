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


insert into Role(name) values('NETWORK_ENGINEER');
insert into Role(name) values('SECURITY_ENGINEER');
insert into Role(name) values('SYSTEM_ADMINISTRATOR');
insert into Role(name) values('CLOUD_ARCHITECT');
insert into Role(name) values('DEVOPS');
insert into Role(name) values('IT_SUPPORT');
insert into Role(name) values('DATABASE_ADMINISTRATOR');





