/**
 * Event speichert ein Geschehniss und die beteiligten Züge.
 * @author Julian Strietzel
 */
package julian.modelrailway.events;

import java.util.LinkedList;
import java.util.List;

import julian.modelrailway.rollingmaterial.SetTrain;

public abstract class Event implements Comparable<Event>{

    private final LinkedList<SetTrain> involved;

    /**
     * Erstellt ein neues Event mit dem gesetzten Zug invol.
     * @param invol beteiligter Zug
     */
    public Event(SetTrain invol) {
        involved = new LinkedList<SetTrain>();
        involved.add(invol);
    }
    
    /**
     * Erstellt ein neues Event mit allen Zügen aus invol.
     * @param invol alle involvierten Züge
     */
    public Event(List<SetTrain> invol) {
        involved = new LinkedList<SetTrain>();
        involved.addAll(invol);
        involved.sort(null);
    }
    
    /**
     * 
     * @return Alle beteiligten Züge.
     */
    public LinkedList<SetTrain> getInvolved() {
        involved.sort(null);
        return involved;
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
     * Fügt einen neuen Zug zu den beteiligten hinzu.
     * @param tr neuer gesetzter Zug
     */
    public void addInvolved(SetTrain tr) {
        if(!involved.contains(tr)) {
            involved.add(tr);
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
