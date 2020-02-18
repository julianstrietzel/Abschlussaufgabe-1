/**
 * Ein Knotenpunkt im Schienensystem speichert ein und ausgehende Verbindungen und seine eigene Position.
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;

import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.rollingmaterial.SetTrain;

public class Knode extends Vertex {

    private Rail railIn;
    private Rail railOut;
    private LinkedList<SetTrain> trains;

    /**
     * Erstellt einen neuen Knoten mit einer eingehenden Verbindung und der Position
     * aus den Koordinaten
     * 
     * @param xcoord
     * @param ycoord
     * @param railIn eingehende Verbindung
     */
    public Knode(int xcoord, int ycoord, Rail railIn) {
    
        super(xcoord, ycoord);
        this.railIn = railIn;
        trains = new LinkedList<SetTrain>();
    }

    /**
     * Erstellt einen neuen Knoten mit einer eingehenden Verbindung und der Position
     * des Vektors.
     * 
     * @param pos    Position als Vektor
     * @param railIn eingehende Verbindung
     */
    public Knode(Vertex pos, Rail railIn) {
        super(pos.getXcoord(), pos.getYcoord());
        this.railIn = railIn;
        trains = new LinkedList<SetTrain>();
    }

    /**
     * 
     * @return dei eingehende Verbindung
     */
    public Rail getRailIn() {
        return railIn;
    }

    /**
     * 
     * @param railIn die neue eingehende Verbindung
     */
    public void setRailIn(Rail railIn) {
        this.railIn = railIn;
    }

    /**
     * 
     * @return die ausgehende Verindung
     */
    public Rail getRailOut() {
        return railOut;
    }

    /**
     * 
     * @param railOut neue ausgehende Verbindung
     */
    public void setRailOut(Rail railOut) {
        this.railOut = railOut;
    }

    /**
     * 
     * @return WW, ob noch ein Anschluss frei.
     */
    public boolean isFree() {
        return railOut == null || railIn == null;
    }

    public void resetTrains() {
        trains.clear();
    }

    public void addTrain(SetTrain t) {
        trains.add(t);
    }

    public LinkedList<SetTrain> getTrains() {
        return trains;
    }

    public boolean hasTrain() {
        return !trains.isEmpty();
    }
    
    public void deconnect(Rail r) {
        if(r.equals(railIn)) {
            railIn = railOut;
            railOut = null;
        }
        if(r.equals(railOut)) {
            railOut = null;
        }
    }

    /**
     * 
     * @return true, wenn keine Verbindungen mehr existieren
     */
    public boolean isUseless() {
        return railIn == null && railOut == null;
    }
    
    /**
     * 
     * @param railone nciht interessierende Schiene
     * @return interessierende Schiene
     */
    public Rail getNext(Rail railone) {
        if (railone.equals(railIn)) {
            return railOut;
        } else if (railone.equals(railOut)) {
            return railIn;
        }
        return null;
    }

    /**
     * 
     * @param direc Ricchtung, zu der der Track passen soll
     * @return gesuchter Trakc, bei entsprechneder Richtung, oder null, falls nicht
     *         kompatibel
     * @throws IllegalInputException 
     * @throws LogicalException 
     */
    public Rail getTrack(DirectionalVertex direc) throws LogicalException {
        
            if (railIn != null) {
                if (this.equals(railIn.getEndInDirection(direc))) {
                    return railIn;
                }
                if (direc.compatibleDirection(railIn.getSetDirection())) {
                    if (railOut != null) {
                        return railOut;
                    }
                    throw new LogicalException("no fitting Track existing.");
                } 
            }
            if (railOut != null) {
                if (this.equals(railOut.getEndInDirection(direc))) {
                    return railOut;
                }
                if (direc.compatibleDirection(railOut.getSetDirection())) {
                    if (railIn != null) {
                        return railIn;
                    }
                    throw new LogicalException("no fitting Track existing.");
                } 
            }
            throw new LogicalException("no fitting Track existing.");        
    }
}