
package julian.modelrailway.Exceptions;

/**
 * Wird geworfen, wenn ein gegebener Input nicht den Anforderungen entspricht.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class IllegalInputException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Erstellt eine neue IlleaglInputException mit NAchricht.
     * 
     * @param message die Nachricht
     */
    public IllegalInputException(String message) {
        super(message);
    }
}
