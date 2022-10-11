package by.krutikov.domain.attributeconverter;

import by.krutikov.domain.enums.ReactionType;

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
