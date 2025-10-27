--liquibase formatted sql

--changeset IlyaKrasovickii:1

create table if not exists "users"
(
    id       bigserial primary key,
    login    varchar(25) not null,
    password varchar     not null,
    created  bigint
);

drop type if exists global_status;
create type global_status as enum ('PENDING', 'SUCCESS', 'DELETED', 'FAIL');

create table if not exists "tasks"
(
    id      bigserial primary key,
    name    varchar       not null,
    target  bigint        not null,
    created bigint,
    "user"  bigint,
    status  global_status not null default 'PENDING'
);

create table if not exists "notifications"
(
    id      bigserial primary key,
    data    varchar       not null,
    created bigint,
    "user"  bigint,
    status  global_status not null default 'PENDING'
);
