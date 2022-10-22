package by.krutikov.mappers;

import by.krutikov.domain.UserProfile;
import by.krutikov.dto.request.UserProfileInfo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile map(UserProfileInfo request);

    UserProfileInfo map(UserProfile userProfile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget UserProfile userProfile, UserProfileInfo request);
}
