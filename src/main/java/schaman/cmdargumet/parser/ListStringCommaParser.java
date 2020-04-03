package schaman.cmdargumet.parser;

import schaman.cmdargumet.exception.ParseCommandParameterException;

import java.util.ArrayList;
import java.util.List;

public class ListStringCommaParser implements ParameterParser {

    @Override
    public Object parse(String value, Class clazz) throws ParseCommandParameterException {
        try {
            List<String> result = new ArrayList<>();

            for (String v : value.split(",")) {
                result.add(v.trim());
            }

            return result;

        } catch (Throwable e) {
            throw new ParseCommandParameterException(null, "Unable to parse value. " + e.getMessage());
        }
    }
}
