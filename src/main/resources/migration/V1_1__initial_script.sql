create table if not exists instruments
(
    id   serial
        constraint instruments_pk
            primary key,
    name varchar(50) not null
);

alter table instruments
    owner to postgres;

create unique index if not exists instruments_id_uindex
    on instruments (id);

create unique index if not exists instruments_name_uindex
    on instruments (name);

create table if not exists experience
(
    id   serial
        constraint experience_pk
            primary key,
    name varchar(50) default 'beginner'::character varying not null
);

alter table experience
    owner to postgres;

create unique index if not exists experience_id_uindex
    on experience (id);

create unique index if not exists experience_name_uindex
    on experience (name);

create table if not exists reaction_type
(
    id   serial
        constraint reaction_type_pk
            primary key,
    name varchar(20) default 'NOT_SELECTED'::character varying not null
);

alter table reaction_type
    owner to postgres;

create unique index if not exists reaction_type_id_uindex
    on reaction_type (id);

create unique index if not exists reaction_type_name_uindex
    on reaction_type (name);

create table if not exists reactions
(
    from_profile     bigint                                    not null,
    to_profile       bigint                                    not null,
    id               bigserial
        constraint reactions_pk
            primary key,
    date_created     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    date_modified    timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    reaction_type_id integer                                   not null
        constraint reactions_reaction_type_id_fk
            references reaction_type
            on update cascade on delete cascade
);

alter table reactions
    owner to postgres;

create unique index if not exists reactions_id_uindex
    on reactions (id);

create table if not exists accounts
(
    id            bigserial
        primary key,
    date_created  timestamp,
    date_modified timestamp,
    email         varchar(255),
    is_locked     boolean,
    login         varchar(255),
    password      varchar(255)
);

alter table accounts
    owner to postgres;

create table if not exists media
(
    id            bigserial
        primary key,
    date_created  timestamp,
    date_modified timestamp,
    demo_url      varchar(255),
    photo_url     varchar(255)
);

alter table media
    owner to postgres;

create table if not exists roles
(
    id            serial
        primary key,
    date_created  timestamp,
    date_modified timestamp,
    name          varchar(255)
);

alter table roles
    owner to postgres;

create table if not exists user_profiles
(
    id                bigserial
        primary key,
    date_created      timestamp,
    date_modified     timestamp,
    description       varchar(255),
    displayed_name    varchar(255),
    is_visible        boolean,
    location          geometry,
    cell_phone_number varchar(255)
);

alter table user_profiles
    owner to postgres;

create table if not exists l_account_roles
(
    id         bigserial
        constraint l_account_roles_pk
            primary key,
    account_id bigint  not null
        constraint l_account_roles_accounts_id_fk
            references accounts
            on update cascade on delete cascade,
    role_id    integer not null
        constraint l_account_roles_roles_id_fk
            references roles
            on update cascade on delete cascade
);

alter table l_account_roles
    owner to postgres;

create unique index if not exists l_account_roles_account_id_uindex
    on l_account_roles (account_id);

create unique index if not exists l_account_roles_id_uindex
    on l_account_roles (id);







