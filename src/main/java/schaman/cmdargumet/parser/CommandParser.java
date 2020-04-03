package schaman.cmdargumet.parser;

import org.apache.commons.lang3.StringUtils;
import schaman.cmdargumet.annotations.Command;
import schaman.cmdargumet.annotations.Parameter;
import schaman.cmdargumet.exception.ParseCommandException;
import schaman.cmdargumet.exception.ParseCommandParameterException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandParser<T> {

    private T object;
    private String[] args;
    private ParsedCommand<T> parsedCommand;
    private List<Field> fields;

    public CommandParser(T object, String[] args) {
        this.object = object;
        this.args = args;
        init();
    }

    /**
     * No se realiza parseo, solo se obtienen meta datos de las anotaciones para que en caso de error se pueda obtener ayuda
     * @throws ParseCommandException
     */
    private void init() throws ParseCommandException {
        Class<?> clazz = object.getClass();

        if (!clazz.isAnnotationPresent(Command.class))
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

        String prefix = clazz.getAnnotation(Command.class).prefix();
        String description = clazz.getAnnotation(Command.class).description();

        this.parsedCommand = new ParsedCommand<>(object, prefix, description);

        for (Field field : fields) {
            field.setAccessible(true);
            String parameterName = field.getAnnotation(Parameter.class).name();
            String parameterHelp = field.getAnnotation(Parameter.class).help();
            this.parsedCommand.putHelpParameter(parameterName, parameterHelp);
        }
    }

    public String getHelp() {
        return this.parsedCommand.getHelp();
    }

    public ParsedCommand<T> parse() throws ParseCommandParameterException {
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
                    if (rawValue.equalsIgnoreCase(this.parsedCommand.getPrefixParameter() + parameterName)) {
                        values = new ArrayList<>();
                        found = true;
                        continue;
                    }
                    // Si se encuetra con otro nombre de parametro termina
                    if (found && rawValue.startsWith(this.parsedCommand.getPrefixParameter()))
                        break;
                    // Se añade
                    if (found) {
                        values.add(rawValue);
                        this.parsedCommand.putRawParameter(parameterName, rawValue);
                    }
                }

                if (required && !found)
                    throw new ParseCommandParameterException(parameterName, "is required");

                if (values != null) {
                    Object parsedValue = parameterParser.parse(StringUtils.join(values, " "), field.getType());
                    this.parsedCommand.putParsedParameter(parameterName, parsedValue);
                    field.set(object, parsedValue);
                }

            } catch (ParseCommandParameterException e) {
                throw e;
            } catch (Throwable e) {
                throw new ParseCommandParameterException(parameterName, e.getMessage());
            }
        }

        return this.parsedCommand;
    }

    public T getObject() {
        return object;
    }

    public String[] getArgs() {
        return args;
    }

    public ParsedCommand<T> getParsedCommand() {
        return parsedCommand;
    }
}
