package by.krutikov.mappers;

import by.krutikov.domain.Reaction;
import by.krutikov.dto.request.PostReactionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReactionMapper {

    // @Mapping(source = "toProfileId", target = "toProfile")
    Reaction map(PostReactionRequest request);
}