alter table if exists accounts
    alter column date_created type timestamp(6) using date_created::timestamp(6);

alter table if exists accounts
    alter column date_created set not null;

alter table if exists accounts
    alter column date_created set default current_timestamp(6);

alter table if exists accounts
    alter column date_modified type timestamp(6) using date_modified::timestamp(6);

alter table if exists accounts
    alter column date_modified set not null;

alter table if exists accounts
    alter column date_modified set default current_timestamp(6);

alter table if exists accounts
    alter column email set not null;

alter table if exists accounts
    alter column is_locked set not null;

alter table if exists accounts
    alter column is_locked set default false;

alter table if exists accounts
    alter column login set not null;

alter table if exists accounts
    alter column password set not null;

create unique index if not exists accounts_email_uindex
    on bandhub.accounts (email);

create unique index if not exists accounts_login_uindex
    on bandhub.accounts (login);