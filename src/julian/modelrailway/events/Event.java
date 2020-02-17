/**
 * Event speichert ein Geschehniss und die beteiligten Züge.
 * @author Julian Strietzel
 */
package julian.modelrailway.events;

import java.util.LinkedList;

import julian.modelrailway.rollingmaterial.SetTrain;

public abstract class Event implements Comparable<Event>{

    protected LinkedList<SetTrain> involved;
    protected final String message;

    /**
     * Erstellt ein neues Event mit dem gesetzten Zug invol.
     * @param invol beteiligter Zug
     * @param message Beschreibung des Events
     */
    public Event(SetTrain invol, String message) {
        involved = new LinkedList<SetTrain>();
        this.message = message;
        involved.add(invol);
    }
    
    /**
     * Erstellt ein neues Event mit allen Zügen aus invol.
     * @param invol alle involvierten Züge
     * @param message Beschriebung des Events.
     */
    public Event(LinkedList<SetTrain> invol, String message) {
        involved = new LinkedList<SetTrain>();
        this.message = message;
        involved.addAll(invol);
        involved.sort(null);
    }
    
    /**
     * 
     * @return Alle beteiligten Züge.
     */
    public LinkedList<SetTrain> getInvolved() {
        return involved;
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
     * @return Die NAchricht des Events
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
        return e.involved.getFirst().compareTo(involved.getFirst());
    }
    
}
