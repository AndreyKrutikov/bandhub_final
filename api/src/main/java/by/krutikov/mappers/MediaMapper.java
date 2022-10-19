package by.krutikov.mappers;

import by.krutikov.domain.Media;
import by.krutikov.dto.request.MediaInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    Media map (MediaInfo request);
    void update (@MappingTarget Media media, MediaInfo request);
}
