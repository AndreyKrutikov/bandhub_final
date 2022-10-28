package by.krutikov.controller;

import by.krutikov.dto.response.RoleDetailsResponse;
import by.krutikov.mappers.RoleMapper;
import by.krutikov.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleRepository roleRepository;
    private final RoleMapper mapper;

    @GetMapping
    public ResponseEntity<Object> getAllRoles() {
        List<RoleDetailsResponse> response = mapper.toResponseList(roleRepository.findAll());

        return new ResponseEntity<>(
                Collections.singletonMap("all roles", response), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRolesByAccountId(@PathVariable(value = "id") Long id) {
        List<RoleDetailsResponse> response = mapper.toResponseList(roleRepository.findRolesByAccountId(id));

        return new ResponseEntity<>(
                Collections.singletonMap("all roles by account id", response), HttpStatus.OK
        );
    }
}
