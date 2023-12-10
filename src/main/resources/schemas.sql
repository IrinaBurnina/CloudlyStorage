create table diploma.login (
                               login varchar(255) primary key,
                               password varchar(255) not null,
                               token varchar(255),
                               enabled boolean default true,
                               role varchar(255) default 'USER'
);
CREATE TABLE diploma.authorities (
                             username VARCHAR(50) NOT NULL,
                             authority VARCHAR(50) NOT NULL,
                             FOREIGN KEY (username) REFERENCES diploma.login(login)
);

CREATE UNIQUE INDEX ix_auth_username
    on diploma.authorities (username,authority);