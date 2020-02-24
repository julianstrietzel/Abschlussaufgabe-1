
package julian.modelrailway.Exceptions;

/**
 * Wird geworfen, wenn ein Logischer Fehler im Programm auftritt. Bsp. Ein Zug
 * der gelöscht werden soll existiert nicht.
 * 
 * @author Julian Strietzel
 */
public class LogicalException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Erstellt eine neue LogicalException mit Nachricht.
     * 
     * @param message die Nachricht
     */
    public LogicalException(String message) {
        super(message);
    }
}
