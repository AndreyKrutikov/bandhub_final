package by.krutikov.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReactionType {
    NOT_SPECIFIED("NOT_SPECIFIED", 1),
    LIKE("LIKE", 2),
    DISLIKE("DISLIKE", 3);

    private final String name;
    private final Integer id;

    public static ReactionType valueOf(int id) {
        for (ReactionType type : ReactionType.values()) {
            if (type.id == id) return type;
        }
        throw new IllegalArgumentException(String.format("Unsupported reaction type by id: %d", id)); //return a default value?
    }
}
