package schaman.cmdargumet.parser;

import schaman.cmdargumet.exception.ParseCommandParameterException;

public interface ParameterParser {
    Object parse(String value, Class clazz) throws ParseCommandParameterException;
}
