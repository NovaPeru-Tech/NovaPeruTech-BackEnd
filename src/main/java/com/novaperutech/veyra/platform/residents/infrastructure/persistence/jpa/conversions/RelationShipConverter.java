package com.novaperutech.veyra.platform.residents.infrastructure.persistence.jpa.conversions;

import com.novaperutech.veyra.platform.residents.domain.model.valueobjects.RelationShip;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RelationShipConverter implements AttributeConverter<RelationShip,Integer> {
    @Override
    public Integer convertToDatabaseColumn(RelationShip attribute) {
        if(attribute==null){
          throw new IllegalArgumentException("RelationShip attribute cannot be null");
        }
        return attribute.getCode();
    }

    @Override
    public RelationShip convertToEntityAttribute(Integer dbData) {
        if(dbData==null){
            throw new IllegalArgumentException("RelationShip code from database cannot be null");
        }
        return RelationShip.fromCode(dbData);
    }
}
