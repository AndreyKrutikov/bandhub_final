package by.krutikov.mappers;

import by.krutikov.domain.Reaction;
import by.krutikov.dto.request.PostReactionRequest;
import by.krutikov.service.UserProfileService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = UserProfileService.class)
public interface ReactionMapper {

    @Mapping(source = "toProfileId", target = "toProfile")
    Reaction map(PostReactionRequest request);
}