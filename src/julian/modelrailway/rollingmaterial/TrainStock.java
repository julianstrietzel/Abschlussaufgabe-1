/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

import java.util.HashMap;
import java.util.LinkedList;

import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.LogicalException;

public class TrainStock {

    private final HashMap<Integer, Train> trains;
    private final ModelRailWay model;
    /**
     * 
     */
    public TrainStock(ModelRailWay model) {
        this.model = model;
        trains = new HashMap<Integer, Train>();
    }
    
    private RollingStock getRollStock() {
        return model.getRollStock();
    }
    
    public String addTrain(int trainID, String rollID) throws LogicalException {
        RollingMaterial r = getRollStock().getWagon(rollID);
        if(r == null) {
            throw new LogicalException("rolling material not existing.");
        }
        if(r.isUsed()) {
            throw new LogicalException("rolling material is used.");
        }
        if(trains.containsKey(trainID)) {
            if(!trains.get(trainID).inUse()) {
                    return trains.get(trainID).add(r);
            } else {
                throw new LogicalException("train on track.");
            }
        } else {
            for(int i = trainID - 1; i > 0; i--) {
                if(!trains.containsKey(i)) {
                    throw new LogicalException("free train ID " + i + " needs to be used first!");
                }
            }
            trains.put(trainID, new Train(r, trainID));
            return r.getTypeForAdding() + " " + r.getStringID() + " added to " + trainID;
        }
    }
    
    public String deleteTrain(int id) throws LogicalException {
        if(trains.remove(id) == null) {
            throw new LogicalException("train not existing.");
        }
        return "OK";
    }
    
    public Train getTrain(int id) {
        return trains.get(id);
    }
    
    public String toString() {
        if(trains.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < trains.size(); i = i) {
            if(trains.containsKey(i)) {
                sb.append(trains.get(i).toString() + "\n");
                i++;
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

}
