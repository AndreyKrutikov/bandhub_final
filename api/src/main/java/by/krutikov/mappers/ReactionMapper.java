package by.krutikov.mappers;

import by.krutikov.domain.Reaction;
import by.krutikov.dto.request.PostReactionRequest;
import by.krutikov.dto.response.ReactionDetailsResponse;
import by.krutikov.service.UserProfileService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = UserProfileService.class)
public interface ReactionMapper {
    Reaction map(PostReactionRequest request);

    @Mapping(source = "toProfile.id", target = "toProfileId")
    @Mapping(source = "fromProfile.id", target = "fromProfileId")
    ReactionDetailsResponse map(Reaction entity);

    default List<ReactionDetailsResponse> toList(Set<Reaction> reactions) {
        return reactions.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}