/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.events;


import julian.modelrailway.rollingmaterial.SetTrain;

public class TrainMoved extends Event implements Comparable<Event>{

    /**
     * @param invol
     * @param message
     */
    public TrainMoved(SetTrain invol, String message) {
        super(invol, message);
    }
    
    public String getMessage() {
        return "Train " + involved.getFirst().getId() + " at " + involved.getFirst().getPosition().toString();
    }

}
