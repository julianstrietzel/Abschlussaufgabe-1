/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;
import java.util.List;

import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.rollingmaterial.SetTrain;

public class RailNetwork {

    private final LinkedList<Rail> rails;
    private final List<Knode> knodes;
    private final List<Switch> switches;
    private final Railsystem2 rSys;

    /**
     * 
     */
    public RailNetwork(Railsystem2 rSys) {
        rails = new LinkedList<Rail>();
        this.rSys = rSys;
        knodes = new LinkedList<Knode>();
        switches = new LinkedList<Switch>();
    }

    /**
     * @return Alle Schienen im Netz
     */
    public List<Rail> getRails() {
        return rails;
    }

    /**
     * @return Alle Weichen im Netz
     */
    public List<Switch> getSwitches() {
        return switches;
    }

    /**
     * @return the knodes
     */
    public List<Knode> getKnodes() {
        return knodes;
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
     * @throws LogicalException, wenn Schiene notwendig oder besetzt
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
     * Guckt ob es einen Weg ohne notSUe gibtvon from zu to
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
        if (from.equals(to)) {
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
     * @throws LogicalException, wenn nicht existend
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
                if (checker.equals(start) || checker.equals(end)) {
                    return true;
                }
            }
            if (switches.contains(rail)) {
                Switch rails = (Switch) rail;
                checker = rails.getStart();
                for (int i = 1; i < rails.getLengthTwo(); i++) {
                    checker = checker.add(rails.getDirectionTwo());
                    if (checker.equals(start) || checker.equals(end)) {
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
     * @throws IllegalInputException, wenn einer der Knoten ncith im System ist
     */
    private void checkFreeKnodes(List<Vertex> knodes) throws LogicalException {
        int freeKnodes = 0;
        for (Knode knode : this.knodes) {
            if (ListUtility.contains(knodes, knode)) {
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
     * Schienen als besetzt.
     * 
     * @param train   Zug um den es geht
     * @param pos     aktuelle Position
     * @param dire    aktuelle Richtung
     * @param rail    aktuele Schiene
     * @param breakUp ob die Funktion bei einer Collision weiter markiere n soll
     *                oder eben nicht
     * @return
     * @throws IllegalInputException
     */
    public boolean markBackOccupied(SetTrain train, Vertex pos, DirectionalVertex dire, Rail rail, boolean breakUp)
            throws IllegalInputException {

        if (ListUtility.contains(knodes, train.getPosition()) != null) {
            Knode newLy = ListUtility.contains(knodes, train.getPosition());
            newLy.addTrain(train);
        }
        int i = rail.getSpaceLeftBehind(pos, dire);
        LinkedList<Rail> newlyOccupied = new LinkedList<Rail>();
        newlyOccupied.add(rail);
        train.setRail(rail);
        Rail next = rail;
        Rail previous;
        while (i < train.getLength()) {
            previous = next;
            next = previous.getNextInDirection(dire.getInverseDirection());
            dire = next.getDirectionFrom(previous.getEndInDirection(dire.getInverseDirection()));
            i += next.getLength();
            if (next.isOccupied() && breakUp) {
                return true;
            }
            newlyOccupied.add(next);
            if (i == train.getLength()) {
                ListUtility.contains(knodes, next.getEndInDirection(dire)).addTrain(train);
            }
        }
        for (Rail newRail : newlyOccupied) {
            newRail.getTrains().add(train);
        }
        return false;
    }

    /**
     * Sucht eine Schiene auf dem Schienennetz
     * 
     * @param pos   Position der Schiene
     * @param direc Richtung
     * @return gefunde Schiene
     * @throws LogicalException, wenn Schiene nicht existiert
     */
    public Rail findTrack(Vertex pos, DirectionalVertex direc) throws LogicalException {
        for (Knode knode : knodes) {
            if (knode.equals(pos)) {
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
     * @throws IllegalInputException, wenn Point is not an End of the Switch
     */
    public void setSwitch(int id, Vertex point) throws IllegalInputException {
        for (Switch s : switches) {
            if (id == s.getId()) {
                s.setSwitch(point);
                if (s.isOccupied()) {
                    for (SetTrain t : s.getTrains()) {
                        t.getModel().setInUse(false);
                        rSys.getTrainsOnTrack().remove(t);
                    }
                    rSys.renewMarked();
                    s.setTrains(new LinkedList<SetTrain>());
                }
            }
        }
    }

    /**
     * Fügt dem Schienennetz eine neue Gleisweiche hinzu
     * 
     * @param start  Starpunkt
     * @param endOne erster Endpunkt
     * @param endTwo zweiter Endpunkt
     * @return eindeutige ID der neuen Weiche
     * @throws IllegalInputException, wenn Länge null
     * @throws LogicalException,      wenn der Track schon existiert oder bei
     *                                Schienennetzkollisioen
     */
    public int addSwitch(Vertex start, Vertex endOne, Vertex endTwo) throws IllegalInputException, LogicalException {

        Switch newSw = new Switch(start, endOne, endTwo, getNextFreeID());
        if (ListUtility.contains(switches, newSw)) {
            throw new LogicalException("track existing");
        }
        if (newSw.getMinLength() == 0) {
            throw new IllegalInputException("length needs to be not null");
        }

        if (!rails.isEmpty()) {
            newSw.setSwitch(newSw.getEnd());
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
            checkFreeKnodes(newSw.getKnodes());
            for (Knode knode : knodes) {
                if (knode.equals(newSw.getStart())) {
                    knode.setRailOut(newSw);
                    knode.getRailIn().connectEasy(newSw, newSw.getStart());
                    newSw.setPrevious(knode.getRailIn());
                }
                if (knode.equals(newSw.getEnd())) {
                    knode.setRailOut(newSw);
                    knode.getRailIn().connectEasy(newSw, newSw.getEnd());
                    newSw.setNext(knode.getRailIn());
                }
                if (knode.equals(newSw.getEndTwo())) {
                    knode.setRailOut(newSw);
                    knode.getRailIn().connectEasy(newSw, newSw.getEndTwo());
                    newSw.setNextTwo(knode.getRailIn());
                }
            }

            if (ListUtility.contains(knodes, newSw.getStart()) == null) {
                knodes.add(new Knode(newSw.getStart(), newSw));
            }
            if (ListUtility.contains(knodes, newSw.getEnd()) == null) {
                knodes.add(new Knode(newSw.getEnd(), newSw));
            }
            if (ListUtility.contains(knodes, newSw.getEndTwo()) == null) {
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
     * @throws IllegalInputException, wenn Länge null
     * @throws LogicalException,      wenn der Track schon existiert oder bei
     *                                Schienennetzkollisioen
     */
    public int addRail(Vertex start, Vertex end) throws IllegalInputException, LogicalException {
        Rail newRail = new Rail(start, end, getNextFreeID());
        if (ListUtility.contains(rails, newRail)) {
            throw new LogicalException("track existing.");
        }
        if (newRail.getLength() == 0) {
            throw new IllegalInputException("length needs to be not null.");
        }
        if (!rails.isEmpty()) {
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
            checkFreeKnodes(newRail.getKnodes());
            for (Knode knode : knodes) {
                if (knode.equals(newRail.getStart())) {
                    knode.setRailOut(newRail);
                    knode.getRailIn().connectEasy(newRail, newRail.getStart());
                    newRail.setPrevious(knode.getRailIn());
                }
                if (knode.equals(newRail.getEnd())) {
                    knode.setRailOut(newRail);
                    knode.getRailIn().connectEasy(newRail, newRail.getEnd());
                    newRail.setNext(knode.getRailIn());
                }
            }
            if (ListUtility.contains(knodes, newRail.getStart()) == null) {
                knodes.add(new Knode(newRail.getStart(), newRail));
            }
            if (ListUtility.contains(knodes, newRail.getEnd()) == null) {
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
}
