package schaman.cmdargumet.parser;

import org.apache.commons.lang3.StringUtils;
import schaman.cmdargumet.annotations.Command;
import schaman.cmdargumet.annotations.Parameter;
import schaman.cmdargumet.exception.ParseCommandException;
import schaman.cmdargumet.exception.ParseCommandParameterException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandParser<T> {

    private T object;
    private String[] args;
    private CommandParsedResult<T> commandParsedResult;
    private List<Field> fields;

    public CommandParser(T object, String[] args) {
        this.object = object;
        this.args = args;
        init();
    }

    /**
     * No se realiza parseo, solo se obtienen meta datos de las anotaciones para que en caso de error se pueda obtener ayuda
     *
     * @throws ParseCommandException
     */
    private void init() throws ParseCommandException {
        Class<?> clazz = object.getClass();
        Class<?> commandClazz = null;
        if (!clazz.isAnnotationPresent(Command.class)) {
            Class<?> superClazz = clazz.getSuperclass();
            while (superClazz != null) {
                if (superClazz.isAnnotationPresent(Command.class)) {
                    commandClazz = superClazz;
                    break;
                }
                superClazz = superClazz.getSuperclass();
            }
        } else
            commandClazz = clazz;

        if (commandClazz == null)
            throw new ParseCommandException("@Command annotation is not present on class");

        // Se buscan anotaciones el clase
        this.fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Parameter.class)).collect(Collectors.toList());

        // Se buscan anotacion en clases padre para permitir herencia
        Class<?> superClazz = clazz.getSuperclass();
        while (superClazz != null) {
            this.fields.addAll(Arrays.stream(superClazz.getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(Parameter.class)).collect(Collectors.toList()));

            superClazz = superClazz.getSuperclass();
        }

        String prefix = commandClazz.getAnnotation(Command.class).prefix();
        String description = commandClazz.getAnnotation(Command.class).description();

        this.commandParsedResult = new CommandParsedResult<>(object, prefix, description);

        for (Field field : fields) {
            field.setAccessible(true);
            String parameterName = field.getAnnotation(Parameter.class).name();
            String parameterHelp = field.getAnnotation(Parameter.class).help();
            this.commandParsedResult.putHelpParameter(parameterName, parameterHelp);
        }
    }

    public String getHelp() {
        return this.commandParsedResult.getHelp();
    }

    public CommandParsedResult<T> parse() throws ParseCommandParameterException {
        for (Field field : fields) {
            String parameterName = field.getAnnotation(Parameter.class).name();
            try {
                field.setAccessible(true);
                boolean required = field.getAnnotation(Parameter.class).required();
                Class<? extends ParameterParser> parameterParserClazz = field.getAnnotation(Parameter.class).parser();
                ParameterParser parameterParser = parameterParserClazz.newInstance();

                List<String> values = null;
                boolean found = false;
                for (String rawValue : this.args) {
                    // Si es nombre del parametro
                    if (rawValue.equalsIgnoreCase(this.commandParsedResult.getPrefixParameter() + parameterName)) {
                        values = new ArrayList<>();
                        found = true;
                        continue;
                    }
                    // Si se encuetra con otro nombre de parametro termina
                    if (found && rawValue.startsWith(this.commandParsedResult.getPrefixParameter()))
                        break;
                    // Se añade
                    if (found) {
                        values.add(rawValue);
                        this.commandParsedResult.putRawParameter(parameterName, rawValue);
                    }
                }

                if (required && !found)
                    throw new ParseCommandParameterException(parameterName, "is required");

                if (values != null) {
                    Type elementType = null;
                    if (field.getGenericType() instanceof ParameterizedType) {
                        elementType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    }

                    Object parsedValue = parameterParser.parse(StringUtils.join(values, " "), parameterName, required, field.getType(), elementType);
                    this.commandParsedResult.putParsedParameter(parameterName, parsedValue);
                    field.set(object, parsedValue);
                }

            } catch (ParseCommandParameterException e) {
                throw e;
            } catch (Throwable e) {
                throw new ParseCommandParameterException(parameterName, e.getMessage());
            }
        }

        return this.commandParsedResult;
    }

    public T getObject() {
        return object;
    }

    public String[] getArgs() {
        return args;
    }

    public CommandParsedResult<T> getCommandParsedResult() {
        return commandParsedResult;
    }
}
