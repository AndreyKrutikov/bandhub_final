alter table if exists l_account_roles
    drop constraint if exists l_account_roles_roles_id_fk;

alter table if exists l_account_roles
    add constraint l_account_roles_roles_id_fk
        foreign key (role_id) references roles;