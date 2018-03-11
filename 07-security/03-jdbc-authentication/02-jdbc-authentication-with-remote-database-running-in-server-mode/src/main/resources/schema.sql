create table users(
	username varchar_ignorecase(50) not null primary key,
	password varchar_ignorecase(50) not null,
	enabled boolean not null
);

create table authorities (
	username varchar_ignorecase(50) not null,
	authority varchar_ignorecase(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

insert into USERS (USERNAME, PASSWORD, ENABLED) values ('tina', 222, true);
insert into USERS (USERNAME, PASSWORD, ENABLED) values ('tom', 333, true);
insert into USERS (USERNAME, PASSWORD, ENABLED) values ('jake', 111, true);

insert into AUTHORITIES (USERNAME, AUTHORITY) values ('tina', 'ROLE_ADMIN');
insert into AUTHORITIES (USERNAME, AUTHORITY) values ('tom', 'ROLE_USER');
insert into AUTHORITIES (USERNAME, AUTHORITY) values ('jake', 'ROLE_VISITOR');