/**
 * Wird geworfen, wenn ein Logischer Fehler im Programm auftritt.
 * Bsp. Ein Zug der gelöscht werden soll existiert nicht.
 * @author Julian Strietzel
 */
package julian.modelrailway.Exceptions;

public class LogicalException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * Erstellt eine neue LogicalException mit Nachricht.
     * @param message die Nachricht
     */
    public LogicalException(String message) {
        super(message);
    }
}
