create table Users(
    id INT UNIQUE PRIMARY KEY,
    username varchar(255),
    password varchar(255),
    full_name varchar(255),
    email varchar(255),
    phone varchar(10),
    status BIT,
    creat_at date
);

create table Roles(
    id INT PRIMARY KEY,
    name varchar(255),
    description varchar(255)
);

create table UserRole(
    id INT PRIMARY KEY,
    user_id int,
    role_id int,
    assigned_at date,
    expired_at date,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (role_id) REFERENCES Roles(id)
);

insert into Roles values (1,'ADMIN','Manage the whole system');
insert into Roles values(2,'STAFF','Coworker');
insert into Roles values(3,'MANAGE','Manage the staff and system');

