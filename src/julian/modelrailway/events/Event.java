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
     * 
     * @param invol
     * @param message
     */
    public Event(SetTrain invol, String message) {
        involved = new LinkedList<SetTrain>();
        this.message = message;
        involved.add(invol);
    }
    
    public Event(LinkedList<SetTrain> invol, String message) {
        involved = new LinkedList<SetTrain>();
        this.message = message;
        involved.addAll(invol);
        involved.sort(null);
    }
    
    public LinkedList<SetTrain> getInvolved() {
        return involved;
    }
  
    public void addInvolved(SetTrain tr) {
        if(!involved.contains(tr)) {
            involved.add(tr);
        }
        involved.sort(null);
    }

    public abstract String getMessage();
    
    public boolean isInvolved(SetTrain t) {
        return involved.contains(t);
    }
    
    public int compareTo(Event e) {
        return involved.getFirst().compareTo(e.involved.getFirst());
    }
    
}
