package me.liuzeyu.aimusic.music.dao.converter;

import me.liuzeyu.aimusic.music.api.enums.StatusType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusTypeConverter
        implements AttributeConverter<StatusType, String> {
    @Override
    public String convertToDatabaseColumn(StatusType statusType) {
        return statusType.getCode();
    }

    @Override
    public StatusType convertToEntityAttribute(String code) {
        return StatusType.convert(code);
    }
}
