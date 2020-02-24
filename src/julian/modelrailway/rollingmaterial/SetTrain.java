
package julian.modelrailway.rollingmaterial;

import julian.modelrailway.Exceptions.*;
import julian.modelrailway.trackmaterial.*;

/**
 * Ein gesetzter Zug auf dem Schienennetz aufgebaut nach einem Zug aus dem
 * TrainStock
 * 
 * @author Julian Strietzel
 */
public class SetTrain implements Comparable<SetTrain> {

    private DirectionalVertex direction;
    private Vertex position;
    private Rail rail;
    private final Train model;

    /**
     * Setzt einen neuen Zug nach einem Model auf Position und in bestimmter
     * Richtung.
     * 
     * @param model          Model des Zuges
     * @param initDdirection Initialrichtung des Zuges
     * @param pos            Initiale Position des Zuges
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
     * 
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
     * 
     * @param rail neue Schiene
     */
    public void setRail(Rail rail) {
        this.rail = rail;
    }

    /**
     * Bewegt den Zug um einen Schritt
     * 
     * @param forwards Richtung des Zuges
     * @return Boolean ob erfolgreich
     * @throws IllegalInputException , wenn nächste Schiene nicht existiert
     * @throws LogicalInputException , wenn Fehler im markoccupied()
     */
    public boolean move(boolean forwards) throws LogicalException, IllegalInputException {

        if (forwards) {
            if (position.equals(rail.getEndInDirection(direction))) {
                Rail now = rail;
                try {
                    rail = rail.getNextInDirection(direction);
                } catch (IllegalInputException e) {
                    throw new LogicalException("dont know whjat happened");
                }
                if (rail == null || !rail.isSetCorrectly(position)) {
                    model.setInUse(false);
                    if (this.getLength() > 1) {
                        now.addTrain(this);
                    }
                    return false;
                }
                direction = rail.getDirectionFrom(position);
            }
            position = position.add(direction);
        } else {
            Rail now = rail;
            if (position.equals(rail.getEndInDirection(direction.getInverseDirection()))) {
                rail = rail.getNextInDirection(direction.getInverseDirection());

                if (rail == null || !rail.isSetCorrectly(position)) {
                    model.setInUse(false);
                    position = position.add(direction.getInverseDirection());
                    return false;
                }
                direction = rail.getDirectionFrom(position).getInverseDirection();
            }

            position = position.add(direction.getInverseDirection());
            if (position.equals(rail.getEndInDirection(direction.getInverseDirection()))) {
                rail.removeTrain(this);
                rail = rail.getNextInDirection(direction.getInverseDirection());
                if (rail == null || !rail.isSetCorrectly(position)) {
                    model.setInUse(false);
                    if (this.getLength() > 1) {
                        now.addTrain(this);
                    }
                    return false;
                }

                direction = rail.getDirectionFrom(position).getInverseDirection();
            }
        }
        return true;
    }

    @Override
    public int compareTo(SetTrain o) {
        return this.getId() - o.getId();
    }

}
