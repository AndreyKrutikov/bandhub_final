alter table if exists roles
    alter column date_created type timestamp(6) using date_created::timestamp(6);

alter table if exists roles
    alter column date_created set default CURRENT_TIMESTAMP(6);

alter table if exists roles
    alter column date_modified type timestamp(6) using date_modified::timestamp(6);

alter table if exists roles
    alter column date_modified set default CURRENT_TIMESTAMP(6);