package by.krutikov.mappers;

import by.krutikov.domain.UserProfile;
import by.krutikov.dto.request.UserProfileDetails;
import by.krutikov.dto.response.UserProfileDetailsResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {ReactionMapper.class, MediaMapper.class})
public interface UserProfileMapper {
    UserProfile map(UserProfileDetails request);

    @Mapping(source = "account.id", target = "accountId")
    UserProfileDetailsResponse map(UserProfile userProfile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget UserProfile userProfile, UserProfileDetails request);

    default List<UserProfileDetailsResponse> toResponseList(List<UserProfile> profiles) {
        //profiles.forEach(this::map);
        return profiles.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
