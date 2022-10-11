package by.krutikov.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExperienceLevel {
    BEGINNER("BEGINNER", 1), AMATEUR("AMATEUR", 2), PRO("PRO", 3);

    private final String name;
    private final Integer id;
    public static ExperienceLevel valueOf(int id) {
        for (ExperienceLevel experienceLevel : ExperienceLevel.values()) {
            if (experienceLevel.id == id) return experienceLevel;
        }
        throw new IllegalArgumentException(String.format("Unsupported experience level by id: %d", id)); //return a default role?
    }
}
