package by.krutikov.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemRoles {
    ROLE_USER("ROLE_USER", 1),
    ROLE_ADMIN("ROLE_ADMIN", 2),
    ROLE_MODERATOR("ROLE_MODERATOR", 3),
    ROLE_ANONYMOUS("ROLE_ANONYMOUS", 4);

    private final String name;
    private final Integer id;

    public static SystemRoles valueOf(int id) {
        for (SystemRoles type : SystemRoles.values()) {
            if (type.id == id) return type;
        }
        throw new IllegalArgumentException(String.format("Unsupported role type by id: %d", id));
    }
}