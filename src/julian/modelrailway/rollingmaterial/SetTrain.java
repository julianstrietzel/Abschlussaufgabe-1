/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

import java.util.LinkedList;

import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.trackmaterial.*;

public class SetTrain implements Comparable<SetTrain>{

//    private LinkedList<RollingMaterial> wagons;
    private final int id;
    private DirectionalVertex direction;
    private Vertex position;
    private Rail rail;
    private int length;
   // private Train model;
    
    public SetTrain(int id, /**Train model,**/ DirectionalVertex initDdirection, Vertex pos, int length) {
//        wagons = new LinkedList<RollingMaterial>();
        this.length = length; //TODO delete
        this.id = id;
        this.position = pos;
        this.direction = initDdirection;
//        wagons.add(first);
    }
    
    
    public DirectionalVertex getDirection() {
        return direction;
    }


    public void setDirection(DirectionalVertex direction) {
        this.direction = direction;
    }


    public Vertex getPosition() {
        return position;
    }


    public void setPosition(Vertex position) {
        this.position = position;
    }


    public int getId() {
        return id;
    }


    public int getLength() {
        
        //TODO äbhängig von Wagon länge
        return length;
    }
    
    public boolean move(boolean forwards) { //TODO backwards driving
        if(position.equals(rail.getEndInDirection(direction))) {
            try {
                rail = rail.getNextInDirection(direction);
            } catch (IllegalInputException e) {
//                throw new LogicalException("dont know whjat happened");
            }
            if(rail == null || !rail.isSetCorrectly(position)) {
              //model.setFree(true);
                return false;
            }
            direction = rail.getDirectionFrom(position);
            
        }
        position = position.add(direction);
        
        return true;
    }

    public Rail getRail() {
        return rail;
    }


    public void setRail(Rail rail) {
        this.rail = rail;
    }
    
    @Override
    public int compareTo(SetTrain o) {
        return o.getId() - this.getId();
    }
}
