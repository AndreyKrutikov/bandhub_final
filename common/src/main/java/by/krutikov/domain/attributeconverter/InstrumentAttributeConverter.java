package by.krutikov.domain.attributeconverter;

import by.krutikov.domain.enums.InstrumentType;

import javax.persistence.AttributeConverter;

public class InstrumentAttributeConverter implements AttributeConverter<InstrumentType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(InstrumentType attribute) {
        return attribute.getId();
    }

    @Override
    public InstrumentType convertToEntityAttribute(Integer dbData) {
        return InstrumentType.valueOf(dbData);
    }
}
