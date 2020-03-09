
package julian.modelrailway.events;

import java.util.LinkedList;
import java.util.List;

import julian.modelrailway.rollingmaterial.SetTrain;

/**
 * Event speichert ein Geschehniss und die beteiligten Züge.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public abstract class Event implements Comparable<Event> {

    private final LinkedList<SetTrain> involved;

    /**
     * Erstellt ein neues Event mit dem gesetzten Zug invol.
     * 
     * @param invol beteiligter Zug
     */
    Event(SetTrain invol) {
        involved = new LinkedList<SetTrain>();
        involved.add(invol);
    }

    /**
     * Erstellt ein neues Event mit allen Zügen aus invol.
     * 
     * @param invol alle involvierten Züge
     */
    Event(List<SetTrain> invol) {
        involved = new LinkedList<SetTrain>();
        involved.addAll(invol);
        involved.sort(null);
    }

    /**
     * 
     * @return Alle beteiligten Züge.
     */
    public LinkedList<SetTrain> getInvolved() {

        LinkedList<SetTrain> involnew = new LinkedList<SetTrain>();
        involnew.addAll(involved);
        return involnew;
    }

    /**
     * 
     * @param newly ersetzt die aktuell involvierten Züge
     */
    protected void setInvolved(List<SetTrain> newly) {
        involved.clear();
        involved.addAll(newly);
    }

    /**
     * Fügt mehrere neue Züge zu den beteiligten hinzu.
     * 
     * @param trs neuer gesetzter Zug
     */
    public void addInvolved(List<SetTrain> trs) {
        for (SetTrain train : trs) {
            if (!involved.contains(train)) {
                involved.add(train);
            }
        }
        involved.sort(null);
    }

    /**
     * 
     * @return Die Nachricht des Events
     */
    public abstract String getMessage();

    /**
     * Überprüft, ob der Zug am Event beteiligt ist.
     * 
     * @param t zu überprüfender Zug
     * @return WW, ob t beteiligt.
     */
    public boolean isInvolved(SetTrain t) {
        return involved.contains(t);
    }

    @Override
    public int compareTo(Event e) {
        return getInvolved().getFirst().compareTo(e.getInvolved().getFirst());
    }

}
