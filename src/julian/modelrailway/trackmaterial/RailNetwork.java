
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;
import java.util.List;

import julian.modelrailway.exceptions.IllegalInputException;
import julian.modelrailway.exceptions.LogicalException;
import julian.modelrailway.rollingmaterial.SetTrain;

/**
 * Das Netzwerk speichert alle schienen und Knotenpunkte
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class RailNetwork {

    private final LinkedList<Rail> rails;
    private final List<Knode> knodes;
    private final List<Switch> switches;
    private final Railsystem rSys;

    /**
     * Erstellt ein neues Schienennetz
     * 
     * @param rSys gekoppeltes Schienennetz
     */
    public RailNetwork(Railsystem rSys) {
        rails = new LinkedList<Rail>();
        this.rSys = rSys;
        knodes = new LinkedList<Knode>();
        switches = new LinkedList<Switch>();
    }

    /**
     * @return Alle Schienen im Netz
     */
    public List<Rail> getCopyRails() {
        LinkedList<Rail> wcRails = new LinkedList<Rail>();
        wcRails.addAll(rails);
        return wcRails;
    }

    /**
     * @return Alle Weichen im Netz
     */
    public List<Switch> getCopySwitches() {
        LinkedList<Switch> wcSwitch = new LinkedList<Switch>();
        wcSwitch.addAll(switches);
        return wcSwitch;
    }

    /**
     * @return the knodes
     */
    public List<Knode> getCopyKnodes() {
        LinkedList<Knode> wcKnode = new LinkedList<Knode>();
        wcKnode.addAll(knodes);
        return wcKnode;
    }

    /**
     * Sucht für eine neue Schiene die nächste freie ID raus.
     * 
     * @return nächste freie ID
     */
    private int getNextFreeID() {
        int i = 1;
        while (i > 0) {
            boolean ex = false;
            for (Rail r : rails) {
                if (r.getId() == i) {
                    i++;
                    ex = true;
                    break;
                }
            }
            if (!ex) {
                return i;
            }
        }
        return rails.size() + 1;
    }

    /**
     * Entfernt eine Schiene aus dem Netz, wenn diese nicht notwendig ist
     * 
     * @param id der Schiene
     * @throws LogicalException , wenn Schiene notwendig oder besetzt
     */
    public void deleteTrack(int id) throws LogicalException {
        Rail dRail = getRailinSystem(id);
        if (dRail.isOccupied()) {
            throw new LogicalException("track occupied");
        }
        if (dRail.wayWithout(this)) {
            rails.remove(dRail);
            switches.remove(dRail);
            dRail.deleteConnections(knodes);
        } else {
            throw new LogicalException("track necessary");
        }
    }

    /**
     * Guckt ob es einen Weg von "from" zu "to", ohne die Tracks in notUse gibt.
     * Also ob die Schiene "notwendig" ist Dabei wird rekursiv vorgegangen und alle
     * besuchten Tracks zu notUse hinzugefügt.
     * 
     * @param notUse Nicht zu verwendende Scheinewege
     * @param from   Startpunkt
     * @param to     Endpunkt
     * @param now    aktuelle Schiene
     * @return WW, ob weg ohne
     */
    public boolean wayWithout(List<Rail> notUse, Rail from, Rail to, Rail now) {
        if (from == null) {
            return false;
        }
        if (from.sameRail(to)) {
            return true;
        }
        LinkedList<Rail> nextOnes = from.getConnected(now);
        notUse.add(from);
        boolean yes = false;
        nextOnes.removeAll(notUse);
        for (Rail next : nextOnes) {
            if (wayWithout(notUse, next, to, from)) {
                yes = true;
            }
        }
        return yes;
    }

    /**
     * sucht eine bestimmte Schiene
     * 
     * @param id der Schiene
     * @return Rail mit id, wenn existend
     * @throws LogicalException , wenn nicht existend
     */
    private Rail getRailinSystem(int id) throws LogicalException {
        for (Rail rail : rails) {
            if (rail.getId() == id) {
                return rail;
            }
        }
        throw new LogicalException("track not found");
    }

    /**
     * Checkt, ob die Strecke zwischen start und ein eine Schiene kreuzt
     * 
     * @param start Startpunkt
     * @param end   Endpunkt
     * @return WW, ob Schiene geschnitten
     */
    private boolean checkTrackCollision(Vertex start, Vertex end) {
        for (Rail rail : rails) {
            Vertex checker = rail.getStart();
            for (int i = 1; i < rail.getLength(); i++) {
                checker = checker.add(rail.getDirection());
                if (checker.sameVertex(start) || checker.sameVertex(end)) {
                    return true;
                }
            }
            if (switches.contains(rail)) {
                Switch rails = (Switch) rail;
                checker = rails.getStart();
                for (int i = 1; i < rails.getLengthTwo(); i++) {
                    checker = checker.add(rails.getDirectionTwo());
                    if (checker.sameVertex(start) || checker.sameVertex(end)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Guckt, ob die Knoten noch frei sind
     * 
     * @param knodes Liste an Knoten, die zu überprüfen sind
     * @throws IllegalInputException , wenn einer der Knoten ncith im System ist
     */
    private void checkFreeKnodes(List<Vertex> knodes) throws LogicalException {
        int freeKnodes = 0;
        for (Knode knode : this.knodes) {
            if (contains(knodes, knode)) {
                freeKnodes++;
                if (!knode.isFree()) {
                    throw new LogicalException("knodes occupied.");
                }
            }
        }
        if (freeKnodes == 0) {
            throw new LogicalException("knodes not connected.");
        }
    }

    /**
     * Markiert hinter jedem Kopf eines ZUges entsprechend der Länge des Zuges die
     * Schienen als besetzt. Dabei wird nacheinander das Schienennetz entlang
     * gegangen und bei jeder durch diesen Zug belegten Schiene, dieser der Liste an
     * Zügen auf dieser Schiene hinzugefügt.
     * 
     * @param train   Zug um den es geht
     * @param pos     aktuelle Position
     * @param dire    aktuelle Richtung
     * @param rail    aktuele Schiene
     * @param breakUp ob die Funktion bei einer Collision weiter markiere n soll
     *                oder eben nicht
     * @return bei break up false, wenn eine Schiene besetzt. sonst immer true.
     * @throws IllegalInputException , wenn interner Fehler
     * @throws LogicalException      , wenn Zug zu lang für Schienennetz
     */
    public boolean markBackOccupied(SetTrain train, Vertex pos, DirectionalVertex dire, Rail rail, boolean breakUp)
            throws IllegalInputException, LogicalException {
        DirectionalVertex direc = dire;
        if (Knode.contains(knodes, train.getPosition()) != null) {
            Knode newLy = Knode.contains(knodes, train.getPosition());
            newLy.addTrain(train);
        }
        int i = rail.getSpaceLeftBehind(pos, direc);
        LinkedList<Rail> newlyOccupied = new LinkedList<Rail>();
        newlyOccupied.add(rail);
        train.setRail(rail);
        Rail next = rail;
        Rail previous;
        while (i < train.getLength()) {
            previous = next;
            next = previous.getNextInDirection(direc.getInverseDirection());
            if (next == null) {
                throw new LogicalException("train to long for rails");
            }
            direc = next.getDirectionFrom(previous.getEndInDirection(direc.getInverseDirection()));
            i += next.getSetLength();
            if (next.isOccupied() && breakUp) {
                return true;
            }
            newlyOccupied.add(next);
            if (i == train.getLength()) {
                Knode.contains(knodes, next.getEndInDirection(direc)).addTrain(train);
            }
        }
        for (Rail newRail : newlyOccupied) {
            newRail.addTrain(train);
        }
        return false;
    }

    /**
     * Sucht eine Schiene auf dem Schienennetz
     * 
     * @param pos   Position der Schiene
     * @param direc Richtung
     * @return gefunde Schiene
     * @throws LogicalException , wenn Schiene nicht existiert
     */
    public Rail findTrack(Vertex pos, DirectionalVertex direc) throws LogicalException {
        for (Knode knode : knodes) {
            if (knode.sameVertex(pos)) {
                Rail r = knode.getTrack(direc);
                if (r == null) {
                    throw new LogicalException("track not found");
                }
                return r;
            }
        }
        for (Rail rail : rails) {
            if (rail.contains(pos) && rail.isCorrectDirec(direc)) {
                return rail;
            }
        }
        throw new LogicalException("track not found");
    }

    /**
     * Setzt eine Weiche in Richtung des neuen Punktes
     * 
     * @param id    der Weiche
     * @param point der das neue Ende sein soll
     * @throws IllegalInputException , wenn Point is not an End of the Switch oder
     *                               die ID nicht existiert
     * @throws LogicalException      , wenn Fehle rim MarkOccupied
     */
    public void setSwitch(int id, Vertex point) throws IllegalInputException, LogicalException {
        for (Switch s : switches) {
            if (id == s.getId()) {
                s.setSwitch(point);
                if (s.isOccupied()) {
                    for (SetTrain t : s.getCopyTrains()) {
                        t.getModel().setInUse(false);
                        rSys.removeTrain(t);
                    }
                    rSys.resetMArkers();
                    rSys.renewMarked();
                    s.clearTrains();
                }
                return;
            }
        }
        throw new IllegalInputException("switch not existing");
    }

    /**
     * Fügt dem Schienennetz eine neue Gleisweiche hinzu
     * 
     * @param start  Starpunkt
     * @param endOne erster Endpunkt
     * @param endTwo zweiter Endpunkt
     * @return eindeutige ID der neuen Weiche
     * @throws IllegalInputException , wenn Länge null
     * @throws LogicalException      , wenn der Track schon existiert oder bei
     *                               Schienennetzkollisioen
     */
    public int addSwitch(Vertex start, Vertex endOne, Vertex endTwo) throws IllegalInputException, LogicalException {
        Switch newSw = new Switch(start, endOne, endTwo, getNextFreeID());
        if (contains(rails, newSw)) {
            throw new LogicalException("track existing");
        }
        if (newSw.getMinLength() == 0) {
            throw new IllegalInputException("length needs to be not null");
        }
        if (!rails.isEmpty()) {
            newSw.setSwitch(newSw.getEnd());
            // Es wird geprüft, ob es irgendwelche kollisionen mit anderen Schienen gibt
            Vertex checker = newSw.getStart();
            for (int i = 1; i < newSw.getSetLength(); i++) {
                checker.add(newSw.getDirection());
                if (knodes.contains(checker)) {
                    throw new LogicalException("Switch cutting another Rail");
                }
            }
            newSw.setSwitch(newSw.getEndTwo());
            checker = newSw.getStart();
            for (int i = 1; i < newSw.getSetLength(); i++) {
                checker.add(newSw.getDirectionTwo());
                if (knodes.contains(checker)) {
                    throw new LogicalException("Switch cutting another Rail");
                }
            }
            newSw.unSet();
            if (checkTrackCollision(newSw.getStart(), newSw.getEnd())
                    || checkTrackCollision(newSw.getStart(), newSw.getEndTwo())) {
                throw new LogicalException("Switch cutting another Rail.");
            }
            // Überprüft, ob die Knoten nocht frei sind, an die die Weiche andocken soll
            checkFreeKnodes(newSw.getKnodes());
            // Alle Knoten werden mit der Schiene verbunden
            for (Knode knode : knodes) {
                if (knode.sameVertex(newSw.getStart())) {
                    knode.setRailOut(newSw);
                    knode.getRailIn().connectEasy(newSw, newSw.getStart());
                    newSw.setPrevious(knode.getRailIn());
                }
                if (knode.sameVertex(newSw.getEnd())) {
                    knode.setRailOut(newSw);
                    knode.getRailIn().connectEasy(newSw, newSw.getEnd());
                    newSw.setNext(knode.getRailIn());
                }
                if (knode.sameVertex(newSw.getEndTwo())) {
                    knode.setRailOut(newSw);
                    knode.getRailIn().connectEasy(newSw, newSw.getEndTwo());
                    newSw.setNextTwo(knode.getRailIn());
                }
            }
            // oder erstellt, wenn diese noch nicht existieren
            if (Knode.contains(knodes, newSw.getStart()) == null) {
                knodes.add(new Knode(newSw.getStart(), newSw));
            }
            if (Knode.contains(knodes, newSw.getEnd()) == null) {
                knodes.add(new Knode(newSw.getEnd(), newSw));
            }
            if (Knode.contains(knodes, newSw.getEndTwo()) == null) {
                knodes.add(new Knode(newSw.getEndTwo(), newSw));
            }
        } else {
            knodes.add(new Knode(newSw.getStart(), newSw));
            knodes.add(new Knode(newSw.getEnd(), newSw));
            knodes.add(new Knode(newSw.getEndTwo(), newSw));
        }
        switches.add(newSw);
        rails.add(newSw);
        return rails.getLast().getId();
    }

    /**
     * Fügt dem Schienennetz ein neues Gleis hinzu
     * 
     * @param start Startpunkt
     * @param end   Endpunkt
     * @return eindeutige ID der neuen Schiene
     * @throws IllegalInputException , wenn Länge null
     * @throws LogicalException      , wenn der Track schon existiert oder bei
     *                               Schienennetzkollisioen
     */
    public int addRail(Vertex start, Vertex end) throws IllegalInputException, LogicalException {
        Rail newRail = new Rail(start, end, getNextFreeID());
        if (contains(rails, newRail)) {
            throw new LogicalException("track existing.");
        }
        if (newRail.getLength() == 0) {
            throw new IllegalInputException("length needs to be not null.");
        }
        if (!rails.isEmpty()) {
            // Es wird geprüft, ob es irgendwelche kollisionen mit anderen Schienen gibt
            Vertex checker = newRail.getStart();
            for (int i = 1; i < newRail.getLength(); i++) {
                checker.add(newRail.getDirection());
                if (knodes.contains(checker)) {
                    throw new LogicalException("rail cutting another Rail");
                }
            }
            if (checkTrackCollision(newRail.getStart(), newRail.getEnd())) {
                throw new LogicalException("rail cutting another Rail");
            }
            // Überprüft, ob die Knoten nocht frei sind, an die die Weiche andocken soll
            checkFreeKnodes(newRail.getKnodes());
            // Alle Knoten werden mit der Schiene verbunden
            for (Knode knode : knodes) {
                if (knode.sameVertex(newRail.getStart())) {
                    knode.setRailOut(newRail);
                    knode.getRailIn().connectEasy(newRail, newRail.getStart());
                    newRail.setPrevious(knode.getRailIn());
                }
                if (knode.sameVertex(newRail.getEnd())) {
                    knode.setRailOut(newRail);
                    knode.getRailIn().connectEasy(newRail, newRail.getEnd());
                    newRail.setNext(knode.getRailIn());
                }
            }
            // oder neu erstellt
            if (Knode.contains(knodes, newRail.getStart()) == null) {
                knodes.add(new Knode(newRail.getStart(), newRail));
            }
            if (Knode.contains(knodes, newRail.getEnd()) == null) {
                knodes.add(new Knode(newRail.getEnd(), newRail));
            }
        } else {
            knodes.add(new Knode(newRail.getStart(), newRail));
            knodes.add(new Knode(newRail.getEnd(), newRail));
        }
        rails.add(newRail);
        return rails.getLast().getId();
    }

    @Override
    public String toString() {
        if (rails.isEmpty()) {
            return "No track exists";
        }
        rails.sort(null);
        StringBuilder result = new StringBuilder();
        for (Rail rail : rails) {
            result.append(rail.toString());
            result.append("\n");
        }
        return result.toString().substring(0, result.length() - 1).trim();
    }

    /**
     * Gibt zurück, ob ein solches Objekt in der Liste existiert: nach der
     * entsorechenden equals Funktion.
     * 
     * @param list   die durchsucht werden soll
     * @param object nachdem gesucht werden soll
     * @return WW, ob die Liste die Schiene enthält
     */
    public boolean contains(List<Rail> list, Rail object) {
        for (Rail obj : list) {
            if (object.sameRail(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt zurück, ob ein solches Objekt in der Liste existiert: nach der
     * entsorechenden equals Funktion.
     * 
     * @param list   die durchsucht werden soll
     * @param object nachdem gesucht werden soll
     * @return WW, ob die Liste den Knoten enthält
     */
    public boolean contains(List<Vertex> list, Knode object) {
        for (Vertex obj : list) {
            if (obj.sameVertex(object)) {
                return true;
            }
        }
        return false;
    }
}
