--create table diploma.login (
--                               login varchar(255) primary key,
--                               password varchar(255) not null,
--                               token varchar(255),
--                               enabled boolean default true,
--                               role varchar(255) default 'USER'
--);
--CREATE TABLE diploma.authorities (
--                             username VARCHAR(50) NOT NULL,
--                             authority VARCHAR(50) NOT NULL,
--                             FOREIGN KEY (username) REFERENCES diploma.login(login)
--);
--
--CREATE UNIQUE INDEX ix_auth_username
--    on diploma.authorities (username,authority);
create table diploma.users (
                                login varchar(50),
                                password varchar(55) not null,
                                token varchar(255),
                                enabled boolean default true,
                                primary key (login)
);

create table diploma.files(
                               id bigserial primary key,
                               filename varchar(255) unique not null,
                               size bigint,
                               file bytea,
                               user_id varchar(50) references diploma.users (login),
                               data date default now()
);