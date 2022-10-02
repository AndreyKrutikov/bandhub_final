drop index l_account_roles_account_id_uindex;

create index if not exists l_account_roles_account_id_index
    on l_account_roles (account_id);
