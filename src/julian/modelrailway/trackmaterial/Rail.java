/**
 * Repräsentiert eine einfache Schiene im Scheinenetz
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;

import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.rollingmaterial.SetTrain;

public class Rail {

    private final Vertex start;
    private final Vertex end;
    private final DirectionalVertex direction;
    private Rail next;
    private Rail previous;
    private final int id;
    private LinkedList<SetTrain> trains;

    /**
     * Erstellt eine neue Schiene mit Start, Ende und ID
     * @param start der Startpunkt
     * @param end   der Endpunkt
     * @param id    die ID
     * @throws IllegalInputException, falls Start und Ende inkompatibel
     */
    public Rail(Vertex start, Vertex end, int id) throws IllegalInputException {
        this.end = end;
        this.id = id;
        this.start = start;
        if(!(start.getXcoord() - end.getXcoord() == 0 ^ start.getYcoord() - end.getYcoord() == 0)) {
            throw new IllegalInputException("wrong start and end.");
        }
        this.direction = start.normedDirection(end);
        trains = new LinkedList<SetTrain>();
    }
//  /**
//  * Verbindet zwei Schienen miteinander
//  * @param rail wird verbunden
//  */
// public void connect(Rail rail) {
//     if(this.start.equals(rail.start)) {
//         this.previous = rail;
//         rail.previous = this;
//     }
//     if(this.end.equals(rail.end)) {
//         this.next = rail;
//         rail.next = this;
//     }
//     if(this.end.equals(rail.start)) {
//         this.next = rail;
//         rail.previous = this;
//     }
//     if(this.start.equals(rail.end)) {
//         this.previous = rail;
//         rail.next = this;
//     }
// }
// 
// /**
//  * Verbindet eine Schiene mit einer Wecihe 
//  * @param switch wird verbunden
//  */
// public void connect(Switch s) {
//     if(this.start.equals(s.getStart())) {
//         this.setPrevious(s);
//         s.setPrevious(this);
//     }
//     if(this.start.equals(s.getEnd())) {
//         this.setPrevious(s);
//         s.setNext(this);
//     }
//     if(this.start.equals(s.getEndTwo())) {
//         setPrevious(s);
//         s.setNextTwo(this);
//     }
//     if(this.end.equals(s.getEnd())) {
//         setNext(s);
//         s.setNext(this);
//     }
//     if(this.end.equals(s.getStart())) {
//         setNext(s);
//         s.setPrevious(this);
//     }
//     if(this.end.equals(s.getEndTwo())) {
//         setNext(s);
//         s.setNextTwo(this);
//     }
// }
    
    /**
     * Erstellt einen neuen Knoten am Ende oder Anfang dieser Schiene.
     * @param start ob ANfang oder ende
     * @return
//     */
//    public Knode createKnode(boolean start) {
//        if(start) {
//            return new Knode(this.start.getXcoord(), this.start.getYcoord(), this);
//        }
//        return new Knode(this.end.getXcoord(), this.end.getYcoord(), this);
//    }

    /**
     * 
     * @return alle Züge auf dieser Schiene
     */
    public LinkedList<SetTrain> getTrains() {
        return trains;
    }

    /**
     * 
     * @return die ID der Schiene
     */
    public int getId() {
        return id;
    }

   
    /**
     * 
     * @return die Richtung in die gesetzt ist.
     */
    public DirectionalVertex getSetDirection() {
        return direction;
    } 
    
    /**
     * 
     * @return Schiene, die als nächste abgespeichert ist
     */
    public Rail getNext() {
        return next;
    }

    /**
     * 
     * @param next neue nächste Schiene
     */
    public void setNext(Rail next) {
        this.next = next;
    }

    /**
     * 
     * @return Schiene, die als letzte abgespeichert ist
     */
    public Rail getPrevious() {
        return previous;
    }

    /**
     * 
     * @param previous neue letzte Schiene
     */
    public void setPrevious(Rail previous) {
        this.previous = previous;
    }

    /**
     * 
     * @return WW, ob Schiene belegt
     */
    public boolean isOccupied() {
        return !trains.isEmpty();
    }
    
    /**
     * 
     * @return Startpunkt der Schiene
     */
    public Vertex getStart() {
        return start;
    }

    /**
     * 
     * @return Endpunkt der Schiene
     */
    public Vertex getEnd() {
        return end;
    }
    
    /**
     * 
     * @return Länge der Schiene
     */
    public int getLength() {
        return Math.max(Math.abs(start.getXcoord() - end.getXcoord()), Math.abs(start.getYcoord() - end.getYcoord()));
    }
    
    /**
     * 
     * @return Richtung der Schiene
     */
    public DirectionalVertex getDirection() {
        return direction;
    }
    
    /**
     * Gibt die Richtung, in die die Schiene zeigt, wenn point der Start oder das Ende ist.
     * @param point Punkt von dem aus die Richtung losgehen soll
     * @return
     */
    public DirectionalVertex getDirectionFrom(Vertex point) {
        if(this.start.equals(point)) {
            return this.direction;
        } else {
            return this.direction.getInverseDirection();
        }
    }
    
    /**
     * Gibt die Richtungan um nach point zu kommen.
     * @param point Zielpunkt
     * @return Richtung zum Zielpunkt
     */
    public DirectionalVertex getDirectionTo(Vertex point) {
        if(this.end.equals(point)) {
            return this.direction;
        } else {
            return this.direction.getInverseDirection();
        }
    }
    
    /**
     * Sucht die nächste Schiene in der entsprechenden Richtung
     * @param direction Richtung in der gesuchten werden soll
     * @return  nächste Schiene oder null, wenn diese nicht existiert
     * @throws IllegalInputException, wenn Richtung nicht mit der Schiennerichtung übereinstimmt.
     */
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

    /**
     * Prüft, ob die Schiene zu dem Punkt eine freie Verbindung hat.
     * @param point überprüfender Punkt.
     * @return boolean, ob freie Verbindung
     */
    public boolean connectsFreeTo(Vertex point) {
        return (point.equals(end) && this.next == null ) || (point.equals(start) && this.previous == null);
    }
    
    /**
     * Guckt, ob eine Schiene korrekt gesetz ist nur Relevant bei Gleisschienen.
     * @param posiVer Ob dieser Vektor  auf der Schinee liegt
     * @return WW, ob korrekt gesetzt
     */
    public boolean isSetCorrectly(Vertex posiVer) {
        return true;
    }
    
    /**
     * Setzt bei die nächste Schiene oder vorherige, jenachdem ob der Point dem Ende oder Start entspricht
     * @param newRail
     * @param point
     * @throws LogicalException 
     */
    public void connectEasy(Rail newRail, Vertex point) throws LogicalException {
     if(point.equals(this.start)) {
         this.setPrevious(newRail);
         return;
     }
     if(point.equals(this.end)) {
         this.setNext(newRail);
         return;
     }
     throw new LogicalException("Punkt der connected werden soll ist nicht Ende oder Start der Schiene.");
    }

    
    /**
     * Vergleicht zwei Rails anhand ihrer Start und Endpunkte
     * @param rail
     * @return
     */
    public boolean equals(Rail rail) {
        if((rail instanceof Switch)) {
            return rail.equals(this);
        }
        return (this.start.equals(rail.end) && this.end.equals(rail.start) || 
                this.start.equals(rail.start) && this.end.equals(rail.end));
    }
    
    /**
     * 
     * @return Die verbundenen Punkte
     */
    public LinkedList<Vertex> getKnodes() {
        LinkedList<Vertex> list = new LinkedList<Vertex>();
        list.add(start);
        list.add(end);
        return list;
    }
    
    /**
     * Gibt das nächste Ende in die Richtung aus
     * @param direction Richtung in die gesucht werden soll
     * @return Vertex Ende in Richtung
     */
    public Vertex getEndInDirection(DirectionalVertex direction) {
        if(direction.equalsDirection(this.direction)) {
            return end;
        } 
        if(direction.equalsDirection(this.direction.getInverseDirection())){
            return start;
        }
        return null;
    }
    
    /**
     * Gibt alle anderen Verbundenen Punkte aus.
     * @param notThisOne außer diesem
     * @return Liste aller verbundenen außer <notThisOne>
     */
    public LinkedList<Rail> getConnected(Rail notThisOne) {
        LinkedList<Rail> list = new LinkedList<Rail>();
        list.add(next);
        list.add(previous);
        list.remove(notThisOne);
        return list;
    }
    
    @Override
    public String toString() {
        return "t " + id + " " + start.toString() + " -> " + end.toString() + " " + this.getLength();
    }
    

    /**
     * Sucht, ob der Punkt auf der streck liegt
     * @param v gesuchter Punkt
     * @return WW, ob auf Strecke ( inkl. ENde und Start)
     */
    public boolean contains(Vertex v) {
        Vertex point = new Vertex(start.getXcoord(), start.getYcoord());
        for(int i = 0; i <= this.getLength(); i++) {
            if(point.equals(v)) {
                return true;
            }
            point = point.add(getSetDirection());
        }
        return false;
    }
    
    /**
     * Guckt ob die Richtung mit der Schienen-Richtung übereinstimmt
     * @param direc
     * @return
     */
    public boolean isCorrectDirec(DirectionalVertex direc) {
        return direc.compatibleDirection(getSetDirection());
    }
    
    /**
     * Löscht die Schiene aus allen verbundenen Knoten und Gleisen
     * @param knodes
     */
    public void deleteConnections(LinkedList<Knode> knodes) {
        Knode endK = ListUtility.contains(knodes, end);
        endK.deconnect(this);
        if(endK.isUseless()) {
            knodes.remove(endK);
        }
        Knode startK = ListUtility.contains(knodes, start);
        startK.deconnect(this);
        if(startK.isUseless()) {
            knodes.remove(startK);
        }
        if(next != null) {
            getNext().deleteConnectionsTo(this);
        }
        if(previous != null) {
            getPrevious().deleteConnectionsTo(this);
        }
    }
    
    /**
     * Löscht alle Verbindungen zur Übergebenen Schiene
     * @param r Schiene zu der Verbindungen gelöscht werden sollen
     */
    protected void deleteConnectionsTo(Rail r) {
        if(r.equals(next)) {
            next = null;
        }
        if(r.equals(previous)) {
            previous = null;
        }
    }
    
    /**
     * Guckt wie viele Wagenlängen hinter der Position platz haben
     * @param pos Position von der aus gesucht werdensoll
     * @param direc Fahrtrichtung des Zuges
     * @return ANzahl freier Plätze behind
     */
    public int getSpaceLeftBehind(Vertex pos, DirectionalVertex direc) {
        DirectionalVertex dire = direc.getInverseDirection();
        Vertex posi = pos.clone();
        int i = 0;
        while(!posi.equals(getEndInDirection(dire))) {
            i++;
            posi = posi.add(dire);
        }
        return i;
    }
    
    

}
