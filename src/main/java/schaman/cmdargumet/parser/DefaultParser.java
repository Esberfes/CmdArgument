package schaman.cmdargumet.parser;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import schaman.cmdargumet.exception.ParseCommandParameterException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultParser implements ParameterParser {
    @Override
    @SuppressWarnings("unchecked")
    public Object parse(String value, String parameter, boolean required, Class clazz, Type elementType) throws ParseCommandParameterException {
        try {
            value = value.trim();

            if (clazz == List.class) {
                if (elementType != null) {
                    List result = new ArrayList();

                    for (String v : value.split(" "))
                        result.add(parseCommons(v, (Class) elementType));

                    return result;
                } else
                    return Arrays.asList(value.split(" "));
            }

            if (clazz.isEnum()) {
                if (!EnumUtils.isValidEnum(clazz, value))
                    throw new Exception("Value is not a valid enum for type: " + clazz.getName());

                return EnumUtils.getEnum(clazz, value);
            }

            if (clazz == String.class && required && StringUtils.isBlank(value))
                throw new Exception("Value is required");

            return parseCommons(value, clazz);

        } catch (Throwable e) {
            throw new ParseCommandParameterException(parameter, "Unable to parse value. " + e.getMessage());
        }
    }

    private Object parseCommons(String value, Class clazz) {
        if (clazz == Integer.class || clazz == int.class)
            return Integer.parseInt(value);

        if (clazz == Long.class || clazz == long.class)
            return Long.parseLong(value);

        if (clazz == Double.class || clazz == double.class)
            return Double.parseDouble(value);

        if (clazz == Float.class || clazz == float.class)
            return Float.parseFloat(value);

        if (clazz == Boolean.class || clazz == boolean.class) {
            // Null se usa como valor implicito
            if (StringUtils.isBlank(value))
                return true;

            return Boolean.parseBoolean(value);
        }

        return value;
    }
}
