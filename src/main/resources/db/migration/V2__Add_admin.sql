insert into users(id, username, password, is_active)
    values(1, 'admin', '123', true);

insert into user_roles(user_id, roles)
    values(1, 'ROLE_USER'), (1, 'ROLE_ADMIN'), (1, 'ROLE_OPERATOR');