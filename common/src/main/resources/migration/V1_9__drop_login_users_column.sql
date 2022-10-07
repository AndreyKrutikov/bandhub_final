drop index if exists accounts_login_uindex;

alter table accounts
    drop column login;
