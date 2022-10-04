package by.krutikov.domain.converter;

import by.krutikov.domain.ExperienceLevel;

import javax.persistence.AttributeConverter;

public class ExperienceAttributeConverter implements AttributeConverter<ExperienceLevel, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ExperienceLevel attribute) {
        return attribute.getId();
    }

    @Override
    public ExperienceLevel convertToEntityAttribute(Integer dbData) {
        return ExperienceLevel.valueOf(dbData);
    }
}
