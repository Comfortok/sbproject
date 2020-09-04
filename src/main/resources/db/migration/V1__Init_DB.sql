create sequence hibernate_sequence start 1 increment 1;

create table message (
    id int8 not null,
    filename varchar(255),
    tag varchar(255),
    text varchar(2048) not null,
    user_id int8,
    primary key (id)
);

create table user_roles (
    user_id int8 not null,
    roles varchar(255)
);

create table users (
    id int8 not null,
    activation_code varchar(255),
    email varchar(255),
    is_active boolean not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);

alter table if exists message
    add constraint fk_message_user
    foreign key (user_id) references users;

alter table if exists user_roles
    add constraint fk_user_roles_user
    foreign key (user_id) references users;