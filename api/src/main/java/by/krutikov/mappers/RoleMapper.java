package by.krutikov.mappers;

import by.krutikov.domain.Role;
import by.krutikov.dto.response.RoleDetailsResponse;
import jdk.dynalink.linker.LinkerServices;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDetailsResponse map(Role role);

    default List<RoleDetailsResponse> toResponseList(List<Role> roles) {
        return roles.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
