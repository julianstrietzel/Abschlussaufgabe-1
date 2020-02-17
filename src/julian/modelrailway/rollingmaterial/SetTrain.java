/**
 * Ein gesetzter Zug auf dem Schienennetz aufgebaut nach einem Zug aus dem TrainStock
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
    
    /**
     * Setzt einen neuen Zug nach einem Model auf Position und in bestimmter Richtung.
     * @param model Model des Zuges
     * @param initDdirection    Initialrichtung des Zuges
     * @param pos   Initiale Position des Zuges
     */
    public SetTrain(Train model, DirectionalVertex initDdirection, Vertex pos) {
        this.model = model;
        this.position = pos;
        this.direction = initDdirection;    
    }
    
    /**
     * 
     * @return die Richtung des Zuges
     */
    public DirectionalVertex getDirection() {
        return direction;
    }

    /**
     * 
     * @return das Model des Zuges
     */
    public Train getModel() {
        return model;
    }

    /**
     * Setzt die Richtung des Zuges
     * @param direction neue Richtung des Zuges
     */
    public void setDirection(DirectionalVertex direction) {
        this.direction = direction;
    }

    /**
     * 
     * @return Die Position des Zuges
     */
    public Vertex getPosition() {
        return position;
    }

    /**
     * 
     * @param position neue Position des Zuges
     */
    public void setPosition(Vertex position) {
        this.position = position;
    }

    /**
     * 
     * @return die ID des Zuges
     */
    public int getId() {
        return model.getID();
    }

    /**
     * 
     * @return die Länge des Zuges
     */
    public int getLength() {
        return model.getLength();
    }

    /**
     * 
     * @return die aktuelle Schiene
     */
    public Rail getRail() {
        return rail;
    }

    /**
     * Setzt die aktuelle Schiene
     * @param rail neue Schiene
     */
    public void setRail(Rail rail) {
        this.rail = rail;
    }

    /**
     * Bewegt den Zug um einen Schritt
     * @param forwards Richtung des Zuges
     * @return  Boolean ob erfolgreich
     * @throw wird nicht geworfen 
     */
    public boolean move(boolean forwards) throws LogicalException { //TODO backwards driving
        if(position.equals(rail.getEndInDirection(direction))) {
            try {
                rail = rail.getNextInDirection(direction);
            } catch (IllegalInputException e) {
                throw new LogicalException("dont know whjat happened");
            }
            if(rail == null || !rail.isSetCorrectly(position)) {
                model.setInUse(false);
                return false;
            }
            direction = rail.getDirectionFrom(position);
        }
        position = position.add(direction);
//        marcKnodesoccupied();
        return true;
    }
    
//    private Vertex getLastPos() throws IllegalInputException {
//        int i = this.getLength();
//        i -= this.rail.getSpaceLeftBehind(getPosition(), direction);
//        Rail current = rail;
//        Vertex end = current.getEndInDirection(direction.getInverseDirection());
//        Rail next = current.getNextInDirection(direction.getInverseDirection());
//        while(i >= 0) {
//            if(i == 0) {
//                
//            }
//            current = next;
//            next = current.getNextInDirection(current.getDirectionFrom(end));
//            end = current.getEndInDirection(current.getDirectionFrom(end));
//            i -= next.getLength();
//            
//        }
//    }

    
    @Override
    public int compareTo(SetTrain o) {
        return o.getId() - this.getId();
    }
    
}
