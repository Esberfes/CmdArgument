package schaman.cmdargumet.exception;

public class ParseCommandException extends RuntimeException {

    public ParseCommandException(String description) {
        super("Error parsing command: " + description);
    }
}
