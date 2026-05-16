create table department
(
    id               bigint       not null AUTO_INCREMENT,
    name             varchar(255) not null,
    created_by       varchar(255) not null,
    created_at       timestamp    not null,
    last_modified_by varchar(255),
    last_modified_at timestamp,
    primary key (id),
    UNIQUE KEY unique_name (name)
);

create table asset
(
    id               bigint       not null AUTO_INCREMENT,
    name             varchar(255) not null,
    asset_number     varchar(255) not null,
    created_by       varchar(255) not null,
    created_at       timestamp    not null,
    last_modified_by varchar(255),
    last_modified_at timestamp,
    primary key (id),
    UNIQUE KEY unique_name (name),
    UNIQUE KEY unique_asset_number (asset_number)
);

create table employee
(
    id               bigint       not null AUTO_INCREMENT,
    firstname        varchar(255) not null,
    lastname         varchar(255) not null,
    assigned_id      varchar(255) not null,
    created_by       varchar(255) not null,
    created_at       timestamp    not null,
    last_modified_by varchar(255),
    last_modified_at timestamp,
    primary key (id)
);

create table user
(
    id               bigint       not null AUTO_INCREMENT,
    username         varchar(255) not null,
    password         varchar(255) not null,
    created_by       varchar(255) not null,
    created_at       timestamp    not null,
    last_modified_by varchar(255),
    last_modified_at timestamp,
    primary key (id)
);

create table role
(
    id               bigint       not null AUTO_INCREMENT,
    name             varchar(255) not null,
    code             varchar(255) not null,
    created_by       varchar(255) not null,
    created_at       timestamp    not null,
    last_modified_by varchar(255),
    last_modified_at timestamp,
    primary key (id)
);

create table user_role
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    foreign key (user_id) references user (id),
    foreign key (role_id) references role (id)
);

create table permission
(
    id               bigint       not null AUTO_INCREMENT,
    name             varchar(255) not null,
    created_by       varchar(255) not null,
    created_at       timestamp    not null,
    last_modified_by varchar(255),
    last_modified_at timestamp,
    primary key (id)
);

create table role_permission
(
    role_id bigint not null,
    permission_id bigint not null,
    primary key (role_id, permission_id),
    foreign key (role_id) references role (id),
    foreign key (permission_id) references permission (id)
);
