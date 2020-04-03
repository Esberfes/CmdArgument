package schaman.cmdargumet.parser;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import schaman.cmdargumet.exception.ParseCommandException;
import schaman.cmdargumet.exception.ParseCommandParameterException;

import java.util.Arrays;
import java.util.List;

public class DefaultParser implements ParameterParser {
    @Override
    @SuppressWarnings("unchecked")
    public Object parse(String value, Class clazz) throws ParseCommandParameterException {
        try {
            if(clazz == Integer.class || clazz == int.class)
                return Integer.parseInt(value);

            if(clazz == Long.class || clazz == long.class)
                return Long.parseLong(value);

            if(clazz == Double.class || clazz == double.class)
                return Double.parseDouble(value);

            if(clazz == Float.class || clazz == float.class)
                return Float.parseFloat(value);

            if(clazz == Boolean.class || clazz == boolean.class) {
                // Null se usa como valor implicito
                if(StringUtils.isBlank(value))
                    return true;

                return Boolean.parseBoolean(value);
            }

            if(clazz == List.class)
                return Arrays.asList(value.split(" "));

            if(clazz.isEnum() && EnumUtils.isValidEnum(clazz, value)) {
                return EnumUtils.getEnum(clazz, value);
            }

            return value;

        } catch (Throwable e) {
            throw new ParseCommandParameterException(null, "Unable to parse value. " + e.getMessage());
        }
    }
}
