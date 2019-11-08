create table roles
(
    id   bigint       not null,
    name varchar(255) not null unique,
    primary key (id)
);

create table users
(
    id                  binary(16)   not null,
    email               varchar(255) not null,
    enabled             boolean      not null,
    first_name          varchar(255) not null,
    last_name           varchar(255) not null,
    password            varchar(255) not null,
    token_expired       boolean      not null,
    account_expired     boolean DEFAULT FALSE,
    credentials_expired boolean DEFAULT FALSE,
    account_locked      boolean DEFAULT FALSE,
    primary key (id)
);

create table users_roles
(
    user_id binary not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);

create table roles_privileges
(
    role_id    bigint       not null,
    privileges varchar(255) not null
);

-- CREATE TABLE IF NOT EXISTS authorities
-- (
--     username  VARCHAR(256) NOT NULL,
--     authority VARCHAR(256) NOT NULL,
--     PRIMARY KEY (username, authority)
-- );

create table verification_token
(
    id          bigint       not null,
    is_active   boolean      not null,
    expiry_date timestamp    not null,
    token       varchar(255) not null,
    user_id     binary       not null,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS oauth_client_details
(
    client_id               VARCHAR(256) PRIMARY KEY,
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256) NOT NULL,
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4000),
    autoapprove             VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS oauth_client_token
(
    token_id          VARCHAR(256),
    token             BLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS oauth_access_token
(
    token_id          VARCHAR(256),
    token             BLOB,
    authentication_id VARCHAR(256),
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    BLOB,
    refresh_token     VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          BLOB,
    authentication BLOB
);

CREATE TABLE IF NOT EXISTS oauth_code
(
    code           VARCHAR(256),
    authentication BLOB
);

--
-- INSERT INTO users (id, email, enabled, first_name, last_name, password, token_expired, id)
-- VALUES (1, 'admin@shild.com', true, 'admin', 'ad', '$2a$10$rq6wC6tqbqoZtx3r9aLx1Op8dXZlS88hNxIX/JBx0.YevQkTI4zae',
--         false, '21ae7719e3f64465ba7ed66cfec9fa90');

INSERT INTO users (email, enabled, first_name,
                   last_name, password, token_expired, id)
VALUES ('admin@shild.com', true, 'user', 'again',
        '{bcrypt}$2a$10$cyf5NfobcruKQ8XGjUJkEegr9ZWFqaea6vjpXWEaSqTa2xL9wjgQC',
        false, '21ae7719e3f64465ba7ed66cfec9fa90');

INSERT INTO roles(id, name)
VALUES (1, 'ADMIN');

-- INSERT INTO roles_privileges(role_id, privileges)
-- VALUES (1, 'CREATE');
INSERT INTO roles_privileges(role_id, privileges)
VALUES (1, 'READ');
-- INSERT INTO roles_privileges(role_id, privileges)
-- VALUES (1, 'UPDATE');
-- INSERT INTO roles_privileges(role_id, privileges)
-- VALUES (1, 'DELETE');

INSERT INTO users_roles(user_id, role_id)
VALUES ('21ae7719e3f64465ba7ed66cfec9fa90', 1);

INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities,
                                  access_token_validity)

VALUES ('shield_client', '{bcrypt}$2a$10$vCXMWCn7fDZWOcLnIEhmK.74dvK1Eh8ae2WrWlhr2ETPLoxQctN4.', 'shield',
        'password,refresh_token,client_credentials',
        'ROLE_ADMIN_CREATE,ROLE_ADMIN_READ,ROLE_ADMIN_UPDATE,ROLE_ADMIN_DELETE', 300);

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