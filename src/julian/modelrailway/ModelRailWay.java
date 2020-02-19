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

    private final Railsystem rSystem;
    private final TrainStock ts;
    private final RollingStock rstock; 

    public ModelRailWay() {
        rSystem = new Railsystem();
        ts = new TrainStock(this);
        rstock = new RollingStock();
    }

    public Railsystem getRailSystem() {
        return rSystem;
    }
    
    public RollingStock getRollStock() {
        return rstock;
    }

    public String addTrack(int startX, int startY, int endX, int endY) throws IllegalInputException, LogicalException {
        return "" + rSystem.addRail(new Vertex(startX, startY), new Vertex(endX, endY));
    }

    public String addSwitch(int startX, int startY, int endX, int endY, int end2x, int end2y)
            throws IllegalInputException, LogicalException {

        return "" + rSystem.addSwitch(new Vertex(startX, startY), new Vertex(endX, endY), new Vertex(end2x, end2y));

    }
    
    public String createCoach(String coachType, int length, boolean cFront, boolean cBack) {
       return rstock.createCoach(coachType, length, cFront, cBack);
    }
    
    public String createEngine(String engineType, String series, String name, int length, boolean cFront, boolean cBack)
            throws LogicalException {
        return rstock.createEngine(engineType, series, name, length, cFront, cBack);
    }

    public String createTrainSet(String series, String name, int length, boolean cFront, boolean cBack)
            throws LogicalException {
        return rstock.createTrainSet(series, name, length, cFront, cBack);
    }
    
    public String delete(boolean isPowered, String id) throws IllegalInputException, LogicalException {
        return rstock.delete(isPowered, id);
    }
        
    
    public String deleteTrack(int id) throws IllegalInputException, LogicalException {
        rSystem.deleteTrack(id);
        return "OK";
    }
    
    public String addTrain(int trainID, String rollID) throws LogicalException {
        return ts.addTrain(trainID, rollID);
    }

    public String deleteTrain(int id) throws LogicalException {
        for(SetTrain s: rSystem.getTrainsOnTrack()) {
            if(s.getId() == id) {
                rSystem.getTrainsOnTrack().remove(s);
            }
        }
        return ts.deleteTrain(id);
    }
    
    
    public String putTrain(int id, Vertex pos, DirectionalVertex direction) throws LogicalException, IllegalInputException {
        Train model = ts.getTrain(id);
        if(model == null) {
            throw new LogicalException("train does not exist.");
        }
        if(model.inUse()) {
            throw new LogicalException("train already on track.");
        }
        if(!model.hasPower()) {
            throw new LogicalException("train has no power.");
        }
        rSystem.putTrain(new SetTrain(model, direction, pos), direction, pos);
        model.setInUse(true);
        return "OK";
        
    }

    public String move(int speed) throws LogicalException, IllegalInputException {

            if (!rSystem.isAllSet()) {
                throw new LogicalException("position of switches not set.");
            }
            if (rSystem.getTrainsOnTrack().isEmpty()) {
                return "OK";
            }
            LinkedList<Event> events = new LinkedList<Event>();
            for (int i = 0; i < Math.abs(speed); i++) {
                rSystem.move(Math.abs(speed) == speed);
            }
            for (SetTrain t : rSystem.getTrainsOnTrack()) {
                events.add(new TrainMoved(t, ""));
            }
            events.addAll(rSystem.getCrashes());
            events.sort(null);
            StringBuilder sb = new StringBuilder();
            for (Event e : events) {
                sb.append(e.getMessage());
                sb.append("\n");
            }
            rSystem.resetMarkersAndCrashes();
            for(SetTrain train: rSystem.getTrainsOnTrack()) {
                    rSystem.markBackOccupied(train, train.getPosition(), train.getDirection(), train.getRail(), false);
                
           }
            return sb.substring(0, sb.length() - 1).toString();
        
    }

    public String listTracks() {
        if(rSystem.getRails().isEmpty()) {
            return "No track exists";
        }
        StringBuilder sb = new StringBuilder();
        for (Rail r : rSystem.getRails()) {
            sb.append(r.toString());
            sb.append("\n");
        }
        return sb.substring(0, sb.length() - 1);
    }
    
    public String listTrains() {
        return ts.toString();
    }
    
    public String showTrain(int trainID) throws LogicalException {
        try{
            return ts.getTrain(trainID).getRepre(); 
        } catch (NullPointerException e) {
            throw new LogicalException("train does not exist.");
        }
    }
    
    public String coachestoString() {
        return rstock.coachestoString();
    }
    
    public String enginestoString() {
        return rstock.enginestoString();
    }
    
    public String trainSettoString() {
        return rstock.trainSettoString();
    }
    
    public void setSwitch(int id, Vertex point) throws IllegalInputException {
        rSystem.setSwitch(id, point);
    }
    
    
    
    
}
