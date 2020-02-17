/**
 * Event, dass alle bteiligten Züge eines Crashes speichert.
 * @author Julian Strietzel
 */
package julian.modelrailway.events;

import java.util.LinkedList;

import julian.modelrailway.rollingmaterial.SetTrain;

public class Crash extends Event implements Comparable<Event>{

    /**
     * Erstellt einen neuen Crash mit dem gesetzten Zug invol.
     * @param invol beteiligter Zug
     * @param message Beschreibung des Events
     */
    public Crash(SetTrain invol, String message) {
        super(invol, message);
    }
    
    /**
     * Erstellt einen neuen Crash mit allen Zügen aus invol.
     * @param invol alle involvierten Züge
     * @param message Beschriebung des Events.
     */
    public Crash(LinkedList<SetTrain> invol, String message) {
        super(invol, message);
        involved.sort(null);
    }
    
    @Override 
    public String getMessage() {
        String output = "Crash of train ";
        for(SetTrain tr: involved) {
            output += tr.getId() + ",";
        }
        output = (String) output.subSequence(0, output.length() - 1);
        return output;
    }
    

}
