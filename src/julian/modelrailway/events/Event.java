/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.events;

import java.util.LinkedList;

import julian.modelrailway.rollingmaterial.SetTrain;

public abstract class Event implements Comparable<Event>{

    protected LinkedList<SetTrain> involved;
    protected final String message;

    public Event(SetTrain invol, String message) {
        this.message = message;
        involved.add(invol);
    }
    
    public Event(LinkedList<SetTrain> invol, String message) {
        this.message = message;
        for (SetTrain tr : invol) {
            involved.add(tr);
        }
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

    public String getMessage() {
        return message;
    }
    
    public boolean isInvolved(SetTrain t) {
        return involved.contains(t);
    }
    
    public int compareTo(Event e) {
        return involved.getFirst().compareTo(e.involved.getFirst());
    }
    
}
