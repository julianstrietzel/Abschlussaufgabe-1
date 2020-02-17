/**
 * Event, dass eine erfolgreiche Bewegung speichert.
 * @author Julian Strietzel
 */
package julian.modelrailway.events;


import julian.modelrailway.rollingmaterial.SetTrain;

public class TrainMoved extends Event implements Comparable<Event>{

    /**
     * Erstellt eine neue erfolgreiche Bewegung eines Zuges als Event.
     * @param invol betroffener Zug
     * @param message Beschreibung
     */
    public TrainMoved(SetTrain invol, String message) {
        super(invol, message);
    }
    
    @Override 
    public String getMessage() {
        return "Train " + involved.getFirst().getId() + " at " + involved.getFirst().getPosition().toString();
    }

}
