package by.krutikov.controller.commonendpoints;

import by.krutikov.domain.Role;
import by.krutikov.dto.response.RoleDetailsResponse;
import by.krutikov.mappers.RoleMapper;
import by.krutikov.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static by.krutikov.security.CustomHeader.X_AUTH_TOKEN;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleRepository roleRepository;
    private final RoleMapper mapper;

    @Operation(summary = "Get all application roles",
            description = "Get all application roles. Admin/moderator authorities only")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Application roles",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = RoleDetailsResponse.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Admin/moderator authorities only",
            content = @Content
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping
    public ResponseEntity<Object> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDetailsResponse> response = mapper.toResponseList(roles);

        return new ResponseEntity<>(
                Collections.singletonMap("all app roles", response), HttpStatus.OK
        );
    }

    @Operation(summary = "Get all account roles",
            description = "Get all account roles. Admin/moderator authorities only")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Application roles",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = RoleDetailsResponse.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Admin/moderator authorities only",
            content = @Content
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping("/{accountId}")
    public ResponseEntity<Object> getRolesByAccountId(@PathVariable(value = "accountId") Long id) {
        List<RoleDetailsResponse> response = mapper.toResponseList(roleRepository.findRolesByAccountId(id));

        return new ResponseEntity<>(
                Collections.singletonMap("all roles by account id", response), HttpStatus.OK
        );
    }
}
