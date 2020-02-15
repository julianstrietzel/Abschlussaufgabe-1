/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.Exceptions;

public class LogicalException extends Exception {
    /**
     * Diese Exception wird geworfen, wenn der gegebene Konsolen-Input nicht den Anforderungen entspricht.
     */
    private static final long serialVersionUID = 1L;

    public LogicalException(String message) {
        super(message);
    }
}
