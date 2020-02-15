/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway;
import java.util.LinkedList;

import julian.modelrailway.Exceptions.*;
import julian.modelrailway.rollingmaterial.*;
import julian.modelrailway.trackmaterial.*;

public class ModelRailWay {

    private final Railsystem rs;
    private LinkedList<SetTrain> trains;
    
    
    public ModelRailWay() {
        rs = new Railsystem();
        trains = new LinkedList<SetTrain>();
  
    }
    
    public String addTrack(int startX, int startY, int endX, int endY){
        try {
            return "" + rs.addRail(new Vertex(startX, startY), new Vertex(endX, endY));
        } catch (IllegalInputException e) {
            return "Error, " + e.getMessage();
        }
    }
    
    public String putTestTrain(int id, Vertex pos, DirectionalVertex direction) {
        try {
            rs.addTrain(new SetTrain(id,direction, pos));
            return "OK";
        } catch (LogicalException e) {
            return "Error, " +  e.getMessage();
        }
    }
    
    public String move(int speed) throws LogicalException {
        try {
            if(!rs.isAllSet()) {
                throw new LogicalException("position of switches not set.");
            }
            if(rs.getTrainsOnTrack().isEmpty()) {
                return "OK";
            }
            LinkedList<Event> events = new LinkedList<Event>();
            for(int i = 0; i < Math.abs(speed); i++) {
                rs.move(Math.abs(speed) == speed); 
            }
            for(SetTrain t:rs.getTrainsOnTrack()) {
                events.add(new TrainMoved(t, ""));
            }
            events.addAll(rs.getCrashes());
            events.sort(null);
            StringBuilder sb = new StringBuilder();
            for(Event e: events) {
                sb.append(e.getMessage());
                sb.append("\n");
            }
            sb.substring(0, sb.length() - 2);
            return sb.toString();
        } catch (LogicalException e) {
            return "Error, " + e.getMessage();
        }
    }

}
