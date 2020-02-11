/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

import java.util.LinkedList;


public class Railsystem {
    
    private LinkedList<Rail> rails;
    
    public Railsystem() {
        rails = new LinkedList<Rail>();
    }
    
    public void addTrack(Vertex start, Vertex end) throws IllegalInputException{
        if(rails.contains(new Rail(start, end))) {
            throw new IllegalInputException("rail existing.");
        }
        if(!rails.isEmpty()) {
            for(Rail rail: rails) {
                if(rail.connectsFreeTo(start) || rail.connectsFreeTo(end)) {
                    rail.connect(new Rail(start, end));
                    return;
                }
            }
        }
    }

}
