-- liquibase formatted sql

-- changeset akladkevich:2
create table users
(
    id       integer not null
        constraint users_pk
            primary key,
    username text    not null,
    password text    not null,
    roles    text    not null
);

comment on table users is 'Users';

alter table users
    owner to postgres;

create sequence sequence_user_id
    as integer;

alter sequence sequence_user_id owner to postgres;