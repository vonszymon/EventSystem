CREATE TABLE User(
	username varchar(50),
	password varchar(50),
	enabled tinyint,
	role varchar(50),
	primary key(username)
);

insert into User(username, password, enabled, role) values('Admin', '123456', 1, 'ROLE_ADMIN');
insert into User(username, password, enabled, role) values('a', 'a', 1, 'ROLE_ADMIN');
insert into User(username, password, enabled, role) values('User', '123456', 1, 'ROLE_USER');
