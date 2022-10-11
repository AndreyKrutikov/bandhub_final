package by.krutikov.domain.attributeconverter;

import by.krutikov.domain.enums.ExperienceLevel;

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
