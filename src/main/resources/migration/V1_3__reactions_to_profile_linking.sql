alter table if exists reactions
    add constraint reactions_user_profiles_id_fk
        foreign key (from_profile) references user_profiles
            on update cascade on delete cascade;

alter table if exists reactions
    add constraint reactions_user_profiles_id_fk_2
        foreign key (to_profile) references user_profiles
            on update cascade on delete cascade;