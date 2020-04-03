package schaman.cmdargumet.exception;

abstract class ParserException extends Exception {
    ParserException(String description) {
        super(description);
    }
}
