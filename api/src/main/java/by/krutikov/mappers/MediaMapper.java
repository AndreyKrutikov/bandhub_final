package by.krutikov.mappers;

import by.krutikov.domain.Media;
import by.krutikov.dto.request.MediaDetails;
import by.krutikov.dto.response.MediaDetailsResponse;
import by.krutikov.service.UserProfileService;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = UserProfileService.class)
public interface MediaMapper {
    //@Mapping(source = "profileId", target = "userProfile")
    Media map(MediaDetails request);

    @Mapping(source = "userProfile.id", target = "profileId")
    MediaDetailsResponse map(Media media);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Media media, MediaDetails request);

    default List<MediaDetailsResponse> toList(Set<Media> media) {
        return media.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}