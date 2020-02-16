/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

import julian.modelrailway.Exceptions.*;
import julian.modelrailway.trackmaterial.*;

public class SetTrain implements Comparable<SetTrain>{

    private DirectionalVertex direction;
    private Vertex position;
    private Rail rail;
    private final Train model;
    
    public SetTrain(Train model, DirectionalVertex initDdirection, Vertex pos) {
        this.model = model;
        this.position = pos;
        this.direction = initDdirection;
    }
    
    
    public DirectionalVertex getDirection() {
        return direction;
    }

    public Train getModel() {
        return model;
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
        return model.getID();
    }


    public int getLength() {
        return model.getLength();
    }
    
    public boolean move(boolean forwards) throws LogicalException { //TODO backwards driving
        if(position.equals(rail.getEndInDirection(direction))) {
            try {
                rail = rail.getNextInDirection(direction);
            } catch (IllegalInputException e) {
                throw new LogicalException("dont know whjat happened");
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
