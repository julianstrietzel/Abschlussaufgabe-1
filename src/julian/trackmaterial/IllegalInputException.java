/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

public class IllegalInputException extends Exception {
    /**
     * Diese Exception wird geworfen, wenn der gegebene Konsolen-Input nicht den Anforderungen entspricht.
     */
    private static final long serialVersionUID = 1L;

    IllegalInputException(String message) {
        super(message);
    }
}
