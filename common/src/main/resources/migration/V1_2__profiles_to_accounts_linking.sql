alter table if exists user_profiles
    add if not exists account_id bigint not null;

alter table if exists user_profiles
    add if not exists media_id bigint;

alter table if exists user_profiles
    add if not exists experience_id int not null;

alter table if exists bandhub.user_profiles
    add if not exists instrument_id int not null;

create unique index if not exists user_profiles_account_id_uindex
    on user_profiles (account_id);

create unique index if not exists user_profiles_media_id_uindex
    on user_profiles (media_id);

alter table if exists user_profiles
    add constraint user_profiles_accounts_id_fk
        foreign key (account_id) references bandhub.accounts
            on update cascade on delete cascade;

alter table if exists user_profiles
    add constraint user_profiles_experience_id_fk
        foreign key (experience_id) references experience
            on update cascade on delete cascade;

alter table if exists user_profiles
    add constraint user_profiles_instruments_id_fk
        foreign key (instrument_id) references instruments
            on update cascade on delete cascade;

alter table if exists user_profiles
    add constraint user_profiles_media_id_fk
        foreign key (media_id) references media
            on update cascade on delete cascade;