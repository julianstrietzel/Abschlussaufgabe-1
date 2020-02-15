/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway;

import java.util.LinkedList;

import julian.modelrailway.Exceptions.*;
import julian.modelrailway.events.Event;
import julian.modelrailway.events.TrainMoved;
import julian.modelrailway.rollingmaterial.*;
import julian.modelrailway.trackmaterial.*;

public class ModelRailWay {

    private final Railsystem rs;
    private LinkedList<SetTrain> trains;
    private final int ndelEnter = 2;

    public ModelRailWay() {
        rs = new Railsystem();
        trains = new LinkedList<SetTrain>();

    }

    public Railsystem rs() {
        return rs;
    }
    public String addTrack(int startX, int startY, int endX, int endY) throws IllegalInputException, LogicalException {

        return "" + rs.addRail(new Vertex(startX, startY), new Vertex(endX, endY));

    }
    
    public String addSwitch(int startX, int startY, int endX, int endY, int end2x, int end2y) throws IllegalInputException, LogicalException {

        return "" + rs.addSwitch(new Vertex(startX, startY), new Vertex(endX, endY), new Vertex(end2x, end2y));

    }
    
    public String delete(int id) throws IllegalInputException, LogicalException {
        rs.deleteTrack(id);
        return "OK";
    }

    public String putTestTrain(int id, Vertex pos, DirectionalVertex direction) {
        try {
            rs.addTrain(new SetTrain(id, direction, pos, 10));
            return "OK";
        } catch (LogicalException e) {
            return "Error, " + e.getMessage();
        }
    }

    public String move(int speed) throws LogicalException {
        try {
            if (!rs.isAllSet()) {
                throw new LogicalException("position of switches not set.");
            }
            if (rs.getTrainsOnTrack().isEmpty()) {
                return "OK";
            }
            LinkedList<Event> events = new LinkedList<Event>();
            for (int i = 0; i < Math.abs(speed); i++) {
                rs.move(Math.abs(speed) == speed);
            }
            for (SetTrain t: rs.getTrainsOnTrack()) {
                events.add(new TrainMoved(t, ""));
            }
            events.addAll(rs.getCrashes());
            events.sort(null);
            StringBuilder sb = new StringBuilder();
            for (Event e : events) {
                sb.append(e.getMessage());
                sb.append("\n");
            }
            sb.substring(0, sb.length() - ndelEnter);
            return sb.toString();
        } catch (LogicalException e) {
            return "Error, " + e.getMessage();
        }
    }

    public String listTracks() {
        StringBuilder sb = new StringBuilder();
        for(Rail r: rs.getRails()) {
            sb.append(r.toString());
            sb.append("\n");
        }
        return sb.substring(0, sb.length() - ndelEnter);
    }
}
