alter table if exists user_profiles
    alter column date_created type timestamp(6) using date_created::timestamp(6);

alter table if exists user_profiles
    alter column date_created set not null;

alter table if exists user_profiles
    alter column date_created set default current_timestamp(6);

alter table if exists user_profiles
    alter column date_modified type timestamp(6) using date_modified::timestamp(6);

alter table if exists user_profiles
    alter column date_modified set not null;

alter table if exists user_profiles
    alter column date_modified set default current_timestamp(6);

alter table if exists user_profiles
    alter column is_visible set not null;

alter table if exists user_profiles
    alter column is_visible set default true;

alter table if exists user_profiles
    alter column experience_id drop not null;