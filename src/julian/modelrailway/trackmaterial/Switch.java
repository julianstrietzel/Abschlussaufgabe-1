/**
 * Repräsentiert eine Weiche
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;

import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;

public class Switch extends Rail{
    private Rail nextTwo;
    private final Vertex endTwo;
    private boolean set;
    private boolean setDirOne;
    private DirectionalVertex setDirection;
    private final DirectionalVertex directionTwo;
    
    /**
     * Erstellt eine neue Schiene vom Start zu beiedne Enden
     * @param start Startpunkt
     * @param end Endpunkt eins
     * @param endTwo Endpunkt zwei
     * @param id Identifikator der Schiene
     * @throws IllegalInputException, wenn Punkte nciht richtig.
     */
    public Switch(Vertex start, Vertex end, Vertex endTwo, int id) throws IllegalInputException {
        super(start, end, id);
        this.endTwo = endTwo;
        this.set = false;
        if(!(getStart().getXcoord() - endTwo.getXcoord() == 0 ^ start.getYcoord() - endTwo.getYcoord() == 0)) {
            throw new IllegalInputException("wrong start and end.");
        }
        this.directionTwo = start.normedDirection(endTwo);
        if(directionTwo.equalsDirection(getDirection())) {
            throw new IllegalInputException("not a correct Switch.");
        }
    }
    
    @Override
    public Rail getNextInDirection(DirectionalVertex direction) throws IllegalInputException{
        if(!set) {
            throw new IllegalInputException("switch not set yet.");
        }
        if(!this.setDirection.compatibleDirection(direction)) {
            throw new IllegalInputException("wrong directional Input");
        }
        if(setDirection.equals(directionTwo)) {
            if(this.setDirection.equalsDirection(direction)) {
                return nextTwo;
            } else { 
                return getPrevious();
            }
        } else {
            if(this.setDirection.equalsDirection(direction)) {
                return getNext();
            } else { 
                return getPrevious();
            }
        }
        
    }
    
    /**
     * Gitb die Länge des zweiten Schienenabschnitts aus.
     * @return
     */
    public int getLengthTwo() {
        return Math.max(getStart().getXcoord() - endTwo.getXcoord(), getStart().getYcoord() 
                - endTwo.getYcoord());
    }
    
    /**
     * 
     * @return die Länge des aktuell gesetzten Schienenabschnittes
     */
    public int getSetLength() {
        if(!set) {
            return getMinLength();
        }
        if(setDirOne) {
            return getLength();
        }
        return getLengthTwo();
    }
    
    /**
     * 
     * @return die schiene nach dem zweiten Ence der Weiche
     */
    public Rail getNextTwo() {
        return nextTwo;
    }

    /**
     * 
     * @param nextTwo die neue zweite nächste Schiene
     */
    public void setNextTwo(Rail nextTwo) {
        this.nextTwo = nextTwo;
    }

    @Override
    public boolean isSetCorrectly(Vertex posiVertex) {
        if(posiVertex.equals(endTwo) && setDirOne || posiVertex.equals(getEnd()) && !setDirOne) {
            return false;
        }
        return set;
    }
    
    /**
     * 
     * @return , ob die Weiche gesetzt ist.
     */
    public boolean isSet() {
        return set;
    }

    /**
     * 
     * @return Ob in richtung eins gesetzt ist
     */
    public boolean isSetDirOne() {
        return setDirOne;
    }

    /**
     * bei true wird Richtung eins gesetzt, sonst zwei
     * @param setDirOne ob Richtung eins gesetzt werdne soll
     */
    public void setSetDirOne(boolean setDirOne) {
        this.setDirOne = setDirOne;
    }

    /**
     * @return die aktuellgeesetzte Richtung
     */
    public DirectionalVertex getSetDirection() {
        return setDirection;
    }

    /**
     * 
     * @param setDirection Richtung wird auf diese gesetzt
     */
    public void setSetDirection(DirectionalVertex setDirection) {
        if(setDirection.equals(directionTwo)) {
            this.setDirection = directionTwo;
            setDirOne = false;
        }
        if(setDirection.equals(getDirection())) {
            this.setDirection = getDirection();
            setDirOne = true;
        }
       
    }

    /**
     * 
     * @return die zweite Richtung
     */
    public DirectionalVertex getDirectionTwo() {
        return directionTwo;
    }

    /**
     * 
     * @return das zweite Ende
     */
    public Vertex getEndTwo() {
        return endTwo;
    }

    /**
     * 
     * @return die minimale Länge beider Schienenabschnitte
     */
    public int getMinLength() {
        return Math.min(getLength(), Math.max(Math.abs(getStart().getXcoord() - endTwo.getXcoord()), Math.abs(getStart().getYcoord() 
                - endTwo.getYcoord())));
    }
    
    /**
     * Setzt die Weiche auf eine neue Stellung
     * @param point ENdpunkt auf den gesetzt werden soll
     * @throws IllegalInputException, wenn Point kein Endpunkt
     */
    public void setSwitch(Vertex point) throws IllegalInputException {
        if(getEnd().equals(point)) {
            this.setDirection = getDirection();
            set = true;
            setDirOne = true;
            return;
        }
        if(endTwo.equals(point)) {
            this.setDirection = directionTwo;
            set = true;
            setDirOne = false;
            return;
        }
        //TODO entgleisen, wenn zug auf Gleis
        throw new IllegalInputException("point is not part of switch.");
    }
    
    /**
     * macht die Weiche ungesetzt
     */
    public void unSet() {
        set = false;
        setDirOne = true;
    }
    
    @Override
    public void connectEasy(Rail newRail, Vertex point) throws LogicalException {
        super.connectEasy(newRail, point);
        if(point.equals(this.endTwo)) {
            this.nextTwo = newRail;
        }
        
    }
    
    @Override
    public boolean connectsFreeTo(Vertex point) {
        return super.connectsFreeTo(point) || (point.equals(endTwo) && this.nextTwo == null );
    }
    
//    public Knode createKnode(boolean start) {
//        if(start) {
//            return new Knode(this.getStart().getXcoord(), this.getStart().getYcoord(), this);
//        }
//        return new Knode(this.getEnd().getXcoord(), this.getEnd().getYcoord(), this);
//    }
    
    @Override
    public LinkedList<Vertex> getKnodes() {
        LinkedList<Vertex> list = super.getKnodes();
        list.add(endTwo);
        return list;
    }
    
    @Override
    public LinkedList<Rail> getConnected(Rail notThisOne) {
        LinkedList<Rail> list = new LinkedList<Rail>();
        list.add(getNext());
        list.add(getPrevious());
        list.add(nextTwo);
        list.remove(notThisOne);
        return list;
    }
    
    @Override
    public DirectionalVertex getDirectionFrom(Vertex point) {
        if(point.equals(this.getStart())) {
            return this.setDirection;
        } else {
            if(point.equals(endTwo)) {
                return this.directionTwo.getInverseDirection();
            } else {
                return this.getDirection().getInverseDirection();
            }
        }
    }
    
    @Override
    public Vertex getEndInDirection(DirectionalVertex direction) throws NullPointerException{
        if(direction.equals(this.setDirection)) {
            if(setDirOne) {
                return getEnd();
            }
            return endTwo;
        } 
        if(direction.equals(this.setDirection.getInverseDirection())){
            return getStart();
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "s " + getId() + " " + getStart().toString() + " -> " + getEnd().toString() + "," 
                    + endTwo.toString();
    }
    
    @Override
    public boolean equals(Rail r) {
        try {
        if(r instanceof Switch) {
            Switch re = (Switch) r;
            if(new Rail(re.getStart(), re.getEnd(),0).equals(new Rail(this.getStart(), this.getEnd(), 0)) 
                    || new Rail(re.getStart(), re.getEnd(),0).equals(new Rail(this.getStart(), this.endTwo, 0))) {
                return true;
            }
            if(new Rail(re.getStart(), re.endTwo,0).equals(new Rail(this.getStart(), this.getEnd(), 0)) 
                    || new Rail(re.getStart(), re.endTwo,0).equals(new Rail(this.getStart(), this.endTwo, 0))) {
                return true;
            }
        } else {
            
                if(r.equals(new Rail(getStart(), getEnd(), 0)) || r.equals(new Rail(getStart(), endTwo, 0))) {
                    return true;
                }
            
        }
        
        } catch (IllegalInputException e) {
        }
        return false;
    }
    

    

}
