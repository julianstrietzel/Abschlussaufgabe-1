/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.events;

import java.util.LinkedList;

import julian.modelrailway.rollingmaterial.SetTrain;

public class Crash extends Event implements Comparable<Event>{

    public Crash(SetTrain invol, String message) {
        super(invol, message);
    }
    
    public Crash(LinkedList<SetTrain> invol, String message) {
        super(invol, message);
        involved.sort(null);
    }
    
    public String getMessage() {
        String output = "Crash of train ";
        for(SetTrain tr: involved) {
            output += tr.getId() + ",";
        }
        output = (String) output.subSequence(0, output.length() - 1);
        return output;
    }
    

}
