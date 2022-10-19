alter table if exists media
    add user_profile_id bigint;

alter table if exists media
    add constraint media_user_profiles_id_fk
        foreign key (user_profile_id) references user_profiles
            on update cascade on delete cascade;

drop index if exists bandhub.user_profiles_media_id_uindex;

alter table user_profiles
    drop column if exists media_id;
