package schaman.cmdargumet.exception;

public class ParseCommandParameterException extends ParserException {

    public ParseCommandParameterException(String parameter, String description){
        super(parameter != null ? "Error parsing command parameter: [" + parameter + "] " + description : "Error parsing command parameter. " + description);
    }
}
