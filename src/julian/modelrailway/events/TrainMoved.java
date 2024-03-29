
package julian.modelrailway.events;

import julian.modelrailway.rollingmaterial.SetTrain;

/**
 * Event, dass eine erfolgreiche Bewegung speichert.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class TrainMoved extends Event implements Comparable<Event> {

    /**
     * Erstellt eine neue erfolgreiche Bewegung eines Zuges als Event.
     * 
     * @param invol betroffener Zug
     */
    public TrainMoved(SetTrain invol) {
        super(invol);
    }

    @Override
    public String getMessage() {
        return "Train " + getInvolved().getFirst().getId() + " at " + getInvolved().getFirst().getPosition().toString();
    }

}
