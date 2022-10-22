package by.krutikov.mappers;

import by.krutikov.domain.Media;
import by.krutikov.dto.request.MediaInfo;
import by.krutikov.service.UserProfileService;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = UserProfileService.class)
public interface MediaMapper {
    @Mapping(source = "profileId", target = "userProfile")
    Media map(MediaInfo request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Media media, MediaInfo request);
}