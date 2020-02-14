/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

import java.util.LinkedList;

public class Rail {

    protected final Vertex start;
    protected final Vertex end;
    protected final DirectionalVertex direction;
    protected Rail next;
    protected Rail previous;
    protected boolean occupied;
//    protected Train currentTrain;
    
//    public Train getCurrentTrain() {
//        return currentTrain;
//    }

//    public void setCurrentTrain(Train currentTrain) {
//        this.currentTrain = currentTrain;
//    }

    public Rail(Vertex start, Vertex end) throws IllegalInputException {
        this.end = end;
        this.start = start;
        if(!(start.getXcoord() - end.getXcoord() == 0 ^ start.getYcoord() - end.getYcoord() == 0)) {
            throw new IllegalInputException("wrong start and end.");
        }
        this.direction = start.normedDirection(end);
        this.occupied = false;
    }

    public Rail getNextInDirection(DirectionalVertex direction) throws IllegalInputException {
        if(!this.direction.compatibleDirection(direction)) {
            throw new IllegalInputException("wrong directional Input");
        }
        if(this.direction.equalsDirection(direction)) {
            return next;
        } else { 
            return previous;
        }
        
    }
    public Rail getNext() {
        return next;
    }

    public void setNext(Rail next) {
        this.next = next;
    }

    public Rail getPrevious() {
        return previous;
    }

    public void setPrevious(Rail previous) {
        this.previous = previous;
    }

//    public boolean isOccupied() {
//        return !(this.currentTrain == null);
//    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }
    
    public int getLength() {
        return Math.max(start.getXcoord() - end.getXcoord(), start.getYcoord() - end.getYcoord());
    }
    
    public DirectionalVertex getDirection() {
        return direction;
    }
    
    public DirectionalVertex getDirectionFrom(Vertex point) {
        if(point.equals(this.start)) {
            return this.direction;
        } else {
            return this.direction.getInverseDirection();
        }
    }

    /**
     * Prüft, ob die Schiene zu dem Punkt eine freie Verbindung hat.
     * @param point überprüfender Punkt.
     * @return boolean, ob freie Verbindung
     */
    public boolean connectsFreeTo(Vertex point) {
        return (point.equals(end) && this.next == null ) || (point.equals(start) && this.previous == null);
    }
    
    /**
     * Verbindet zwei Schienen miteinander
     * @param rail wird verbunden
     */
    public void connect(Rail rail) {
        if(this.start.equals(rail.start)) {
            this.previous = rail;
            rail.previous = this;
        }
        if(this.end.equals(rail.end)) {
            this.next = rail;
            rail.next = this;
        }
        if(this.end.equals(rail.start)) {
            this.next = rail;
            rail.previous = this;
        }
        if(this.start.equals(rail.end)) {
            this.previous = rail;
            rail.next = this;
        }
    }
    
    public void connectEasy(Rail newRail, Vertex point) {
     if(point.equals(this.start)) {
         this.setPrevious(newRail);
     }
     if(point.equals(this.end)) {
         this.setNext(newRail);
     }
     
    }
    
    public Knode createKnode(boolean start) {
        if(start) {
            return new Knode(this.start.getXcoord(), this.start.getYcoord(), this);
        }
        return new Knode(this.end.getXcoord(), this.end.getYcoord(), this);
    }
    
    /**
     * Vergleicht zwei Rails anhand ihrer Start und Endpunkte
     * @param rail
     * @return
     */
    public boolean equals(Rail rail) {
        if(!(rail instanceof Rail)) {
            //TODO check equals Switch
        }
        return (this.start.equals(rail.end) && this.end.equals(rail.start) || 
                this.start.equals(rail.start) && this.end.equals(rail.end));
    }
    
    public LinkedList<Vertex> getKnodes() {
        LinkedList<Vertex> list = new LinkedList<Vertex>();
        list.add(start);
        list.add(end);
        return list;
    }
    
    public Vertex getEndInDirection(DirectionalVertex direction) {
        if(direction.equals(this.direction)) {
            return start;
        } else {
            return end;
        }
    }
    

}
