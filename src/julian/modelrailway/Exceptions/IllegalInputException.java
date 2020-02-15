/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.Exceptions;

public class IllegalInputException extends Exception {
    /**
     * Diese Exception wird geworfen, wenn der gegebene Konsolen-Input nicht den Anforderungen entspricht.
     * Oder dieser einen Fehler wirft.
     */
    private static final long serialVersionUID = 1L;

    public IllegalInputException(String message) {
        super(message);
    }
}
