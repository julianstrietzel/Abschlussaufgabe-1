
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;
import java.util.List;

import julian.modelrailway.exceptions.IllegalInputException;
import julian.modelrailway.exceptions.LogicalException;
import julian.modelrailway.rollingmaterial.SetTrain;

/**
 * Repräsentiert eine einfache Schiene im Scheinenetz
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class Rail implements Comparable<Rail> {

    private final Vertex start;
    private final Vertex end;
    private final DirectionalVertex direction;
    private Rail next;
    private Rail previous;
    private final int id;
    private List<SetTrain> trains;

    /**
     * Erstellt eine neue Schiene mit Start, Ende und ID
     * 
     * @param start der Startpunkt
     * @param end   der Endpunkt
     * @param id    die ID
     * @throws IllegalInputException , falls Start und Ende inkompatibel
     */
    public Rail(Vertex start, Vertex end, int id) throws IllegalInputException {
        this.end = end;
        this.id = id;
        this.start = start;
        if (!(start.getXcoord() - end.getXcoord() == 0 ^ start.getYcoord() - end.getYcoord() == 0)) {
            throw new IllegalInputException("wrong start and end.");
        }
        this.direction = start.normedDirection(end);
        trains = new LinkedList<SetTrain>();
    }

    /**
     * 
     * @return Eine Kopie der Liste an Zügen, die auf dem Gleis stehen.
     */
    public List<SetTrain> getCopyTrains() {
        List<SetTrain> l = new LinkedList<SetTrain>();
        l.addAll(trains);
        return l;
    }

    /**
     * 
     * @return die ID der Schiene
     */
    public int getId() {
        return id;
    }

    /**
     * Fügt einen Zug zu dem Knoten hinzu
     * 
     * @param t neuer Zug
     */
    public void addTrain(SetTrain t) {
        trains.add(t);
    }

    /**
     * Entfernt den gewünschten Zug aus der Schiene
     * 
     * @param t zu entfernender Zug
     */
    public void removeTrain(SetTrain t) {
        trains.remove(t);
    }

    /**
     * Löscht die Liste an Zügen
     */
    public void clearTrains() {
        trains.clear();
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
    public long getLength() {
        return Math.max(Math.abs((long) start.getXcoord() - (long) end.getXcoord()),
                Math.abs((long) start.getYcoord() - (long) end.getYcoord()));
    }

    /**
     * 
     * @return bei einer normalen Schiene die einfache Länge
     */
    public long getSetLength() {
        return getLength();
    }

    /**
     * 
     * @return Richtung der Schiene
     */
    public DirectionalVertex getDirection() {
        return direction;
    }

    /**
     * Gibt die Richtung, in die die Schiene zeigt, wenn point der Start oder das
     * Ende ist.
     * 
     * @param point Punkt von dem aus die Richtung losgehen soll
     * @return Richtung in die die Schiene vom point aus weiterführt
     */
    public DirectionalVertex getDirectionFrom(Vertex point) {
        if (this.start.sameVertex(point)) {
            return this.direction;
        } else {
            return this.direction.getInverseDirection();
        }
    }

    /**
     * Gibt die Richtungan um nach point zu kommen.
     * 
     * @param point Zielpunkt
     * @return Richtung zum Zielpunkt
     */
    public DirectionalVertex getDirectionTo(Vertex point) {
        if (this.end.sameVertex(point)) {
            return this.direction;
        } else {
            return this.direction.getInverseDirection();
        }
    }

    /**
     * Sucht die nächste Schiene in der entsprechenden Richtung
     * 
     * @param direction Richtung in der gesuchten werden soll
     * @return nächste Schiene oder null, wenn diese nicht existiert
     * @throws IllegalInputException , wenn Richtung nicht mit der Schiennerichtung
     *                               übereinstimmt.
     */
    public Rail getNextInDirection(DirectionalVertex direction) throws IllegalInputException {
        if (!this.direction.compatibleDirection(direction)) {
            throw new IllegalInputException("wrong directional Input");
        }
        if (this.direction.equalsDirection(direction)) {
            return next;
        } else {
            return previous;
        }
    }

    /**
     * Guckt, ob eine Schiene korrekt gesetz ist nur Relevant bei Gleisschienen.
     * 
     * @param posiVer Ob dieser Vektor auf der Schinee liegt
     * @return WW, ob korrekt gesetzt
     */
    public boolean isSetCorrectly(Vertex posiVer) {
        return true;
    }

    /**
     * Setzt bei die nächste Schiene oder vorherige, jenachdem ob der Point dem Ende
     * oder Start entspricht
     * 
     * @param newRail die Schiene, die verbunden werden soll
     * @param point   der Punkt an dem verbunden werden soll
     * @throws LogicalException , wenn der PÜunkt nicht am ende oder Sgtart der
     *                          Schiene ist.
     */
    public void connectEasy(Rail newRail, Vertex point) throws LogicalException {
        if (point.sameVertex(this.start)) {
            this.setPrevious(newRail);
            return;
        }
        if (point.sameVertex(this.end)) {
            this.setNext(newRail);
            return;
        }
        throw new LogicalException("Punkt der connected werden soll ist nicht Ende oder Start der Schiene.");
    }

    /**
     * Vergleicht zwei Rails anhand ihrer Start und Endpunkte
     * 
     * @param rail zu vergleichende Schiene
     * @return Ob die Schienen den gleichen Start und Endpunkt haben
     */
    public boolean sameRail(Rail rail) {
        if (rail == null) {
            return false;
        }
        if (rail.getClass() != this.getClass()) {
            return rail.sameRail(this);
        }
        return (this.start.sameVertex(rail.end) && this.end.sameVertex(rail.start)
                || this.start.sameVertex(rail.start) && this.end.sameVertex(rail.end));
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
     * 
     * @param direction Richtung in die gesucht werden soll
     * @return Vertex Ende in Richtung
     */
    public Vertex getEndInDirection(DirectionalVertex direction) {
        if (direction.equalsDirection(this.direction)) {
            return end;
        }
        if (direction.equalsDirection(this.direction.getInverseDirection())) {
            return start;
        }
        return null;
    }

    /**
     * Gibt alle anderen Verbundenen Punkte aus.
     * 
     * @param notThisOne außer diesem
     * @return Liste aller verbundenen außer <notThisOne>
     */
    public LinkedList<Rail> getConnected(Rail notThisOne) {
        LinkedList<Rail> list = new LinkedList<Rail>();
        list.add(next);
        list.add(previous);
        list.remove(notThisOne);
        for (Rail r : list) {
            if (r == null) {
                list.remove(r);
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return "t " + id + " " + start.toString() + " -> " + end.toString() + " " + this.getLength();
    }

    /**
     * Sucht, ob der Punkt auf der streck liegt
     * 
     * @param v gesuchter Punkt
     * @return WW, ob auf Strecke ( inkl. ENde und Start)
     */
    public boolean contains(Vertex v) {
        Vertex point = new Vertex(start.getXcoord(), start.getYcoord());
        for (int i = 0; i <= this.getLength(); i++) {
            if (point.sameVertex(v)) {
                return true;
            }
            point = point.add(getSetDirection());
        }
        return false;
    }

    /**
     * Guckt ob die Richtung mit der Schienen-Richtung übereinstimmt
     * 
     * @param direc , die auf Übereinstimmung überprüft wird
     * @return WW, ob Richtung compatibel
     */
    public boolean isCorrectDirec(DirectionalVertex direc) {
        return direc.compatibleDirection(getSetDirection());
    }

    /**
     * Löscht die Schiene aus allen verbundenen Knoten und Gleisen
     * 
     * @param knodes aus diesen Knoten werden alle Verbindungen gelöscht
     */
    public void deleteConnections(List<Knode> knodes) {
        Knode endK = Knode.contains(knodes, end);
        endK.deconnect(this);
        if (endK.isUseless()) {
            knodes.remove(endK);
        }
        Knode startK = Knode.contains(knodes, start);
        startK.deconnect(this);
        if (startK.isUseless()) {
            knodes.remove(startK);
        }
        if (next != null) {
            getNext().deleteConnectionsTo(this);
        }
        if (previous != null) {
            getPrevious().deleteConnectionsTo(this);
        }
    }

    /**
     * Guckt ob die Schiene nötig ist
     * 
     * @param rn Netzwerk in dem überpürüft werden soll
     * @return WW, ob nicht nötig
     */
    public boolean wayWithout(RailNetwork rn) {
        if (getConnected(null).size() < 2) {
            return true;
        }
        LinkedList<Rail> notUse = new LinkedList<Rail>();
        notUse.add(this);
        return rn.wayWithout(notUse, this.getNext(), this.getPrevious(), this);
    }

    /**
     * Löscht alle Verbindungen zur Übergebenen Schiene
     * 
     * @param r Schiene zu der Verbindungen gelöscht werden sollen
     */
    protected void deleteConnectionsTo(Rail r) {
        if (r.sameRail(next)) {
            next = null;
        }
        if (r.sameRail(previous)) {
            previous = null;
        }
    }

    /**
     * Guckt wie viele Wagenlängen hinter der Position platz haben
     * 
     * @param pos   Position von der aus gesucht werdensoll
     * @param direc Fahrtrichtung des Zuges
     * @return ANzahl freier Plätze behind
     */
    public int getSpaceLeftBehind(Vertex pos, DirectionalVertex direc, int trainLength) {
        DirectionalVertex dire = direc.getInverseDirection();
        Vertex posi = pos.clone();
        int i = 0;
        while (!posi.sameVertex(getEndInDirection(dire))) {
            i++;
            posi = posi.add(dire);
            if (i == this.getSetLength() || i >= trainLength) {
                break;
            }
        }
        return i;
    }

    @Override
    public int compareTo(Rail o) {
        return this.getId() - o.getId();

    }

}
