--INSERT INTO diploma.users (login, password)
--values ('username',
--        'pass'
--        );
--insert into diploma.users
--    (login, password)
--values
--    ('4', '{noop}123');

--insert into diploma.users
--    (login, password, token)
--values
--    ('2', '{noop}123', '01d3da2b-d02e-42fe-8a08-fdb6d1f597d6');

select login from diploma.users u where u."login"=login;
select login from diploma.users u where u."token"=token;