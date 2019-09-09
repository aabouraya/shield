create table roles
(
    id   bigint       not null,
    name varchar(255) not null,
    primary key (id)
);

create table roles_privileges
(
    role_id      bigint not null,
    privilege_id bigint not null
);

create table users
(
    id            binary(16)   not null,
    email         varchar(255) not null,
    enabled       boolean      not null,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    password      varchar(255) not null,
    token_expired boolean      not null,
    primary key (id)
);

create table users_roles
(
    user_id binary not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);

create table verification_token
(
    id          bigint       not null,
    is_active   boolean      not null,
    expiry_date timestamp    not null,
    token       varchar(255) not null,
    user_id     binary       not null,
    primary key (id)
);

INSERT INTO users (email, enabled, first_name, last_name, password, token_expired, id)
VALUES ('admin@shild.com', true, 'admin', 'ad', '$2a$10$rq6wC6tqbqoZtx3r9aLx1Op8dXZlS88hNxIX/JBx0.YevQkTI4zae',
        false, '21ae7719e3f64465ba7ed66cfec9fa90');

-- alter table roles_privileges
--     add constraint FK5duhoc7rwt8h06avv41o41cfy FOREIGN key (privilege_id) references privileges;
-- alter table roles_privileges
--     add constraint FK629oqwrudgp5u7tewl07ayugj foreign key (role_id) references roles;
-- alter table users_roles
--     add constraint FKj6m8fwv7oqv74fcehir1a9ffy foreign key (role_id) references roles;
-- alter table users_roles
--     add constraint FK2o0jvgh89lemvvo17cbqvdxaa foreign key (user_id) references users;
-- alter table verification_token
--     add constraint FK3asw9wnv76uxu3kr1ekq4i1ld foreign key (user_id) references users;