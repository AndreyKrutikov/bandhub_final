package by.krutikov.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Experience {
    BEGINNER("BEGINNER", 1), AMATEUR("AMATEUR", 2), PRO("PRO", 3);

    private final String name;
    private final Integer id;
    public static Experience valueOf(int id) {
        for (Experience experience : Experience.values()) {
            if (experience.id == id) return experience;
        }
        throw new IllegalArgumentException(String.format("no experience by id %d found: ", id)); //return a default role?
    }
}
