package by.krutikov.domain.converter;

import by.krutikov.domain.ReactionType;

import javax.persistence.AttributeConverter;

public class ReactionAttributeConverter implements AttributeConverter<ReactionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReactionType attribute) {
        return attribute.getId();
    }

    @Override
    public ReactionType convertToEntityAttribute(Integer dbData) {
        return ReactionType.valueOf(dbData);
    }
}
