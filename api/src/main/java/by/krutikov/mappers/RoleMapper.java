package by.krutikov.mappers;

import by.krutikov.domain.Role;
import by.krutikov.dto.response.RoleDetailsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDetailsResponse map (Role role);
}
