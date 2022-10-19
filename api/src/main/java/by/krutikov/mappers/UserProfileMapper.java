package by.krutikov.mappers;

import by.krutikov.domain.UserProfile;
import by.krutikov.dto.request.UserProfileInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile map(UserProfileInfo request);

    UserProfileInfo map(UserProfile userProfile);

    void update(@MappingTarget UserProfile userProfile, UserProfileInfo request);
}
