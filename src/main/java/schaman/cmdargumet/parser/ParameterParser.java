package schaman.cmdargumet.parser;

import schaman.cmdargumet.exception.ParseCommandParameterException;

import java.lang.reflect.Type;

public interface ParameterParser {
    Object parse(String value, String parameter, boolean required, Class clazz, Type elementType) throws ParseCommandParameterException;
}
