create unique index if not exists reactions_from_profile_to_profile_uindex
    on reactions (from_profile, to_profile);