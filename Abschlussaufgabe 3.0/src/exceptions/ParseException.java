package exceptions;

/**
 * This is a custom exception class
 * @author usoia
 * @version 1.0
 */
public class ParseException extends Exception {
    /**
     * Custom parse exception
     * @param message return parse exception message
     */
    public ParseException(String message) {
        super(message);
    }
}
