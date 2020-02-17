package julian.modelrailway.trackmaterial;

import java.util.LinkedList;
import julian.modelrailway.Exceptions.*;
import julian.modelrailway.events.*;
import julian.modelrailway.rollingmaterial.SetTrain;

/**
 * Verwaltet das Streckennetz und fahrende Züge.
 * 
 * @author Julian Strietzel
 */
public class Railsystem {
    private final LinkedList<Rail> rails;
    private final LinkedList<Knode> knodes;
    private final LinkedList<Switch> switches;
    private LinkedList<Rail> occupiedRails;
    private LinkedList<SetTrain> trainsOnTrack;
    private LinkedList<Crash> crashes;
    private int idCount;

    /**
     * Erstellt eine neues Schienennetz und initialisiert alle Listen.
     */
    public Railsystem() {
        rails = new LinkedList<Rail>();
        knodes = new LinkedList<Knode>();
        switches = new LinkedList<Switch>();
        idCount = 1;
        trainsOnTrack = new LinkedList<SetTrain>();
        crashes = new LinkedList<Crash>();
        occupiedRails = new LinkedList<Rail>();
        resetOccupied();
    }

    /**
     * 
     * @return Alle Schienen im Netz
     */
    public LinkedList<Rail> getRails() {
        return rails;
    }

    /**
     * @return the knodes
     */
    public LinkedList<Knode> getKnodes() {
        return knodes;
    }

    /**
     * 
     * @return die aktuellen Kraschs
     */
    public LinkedList<Crash> getCrashes() {
        return crashes;
    }

    /**
     * 
     * @return alle Züge auf dem Schienennetz
     */
    public LinkedList<SetTrain> getTrainsOnTrack() {
        return trainsOnTrack;
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
        Switch newSw = new Switch(start, endOne, endTwo, idCount);
        if (ListUtility.contains(switches, newSw)) {
            throw new LogicalException("track existing.");
        }
        if (newSw.getMinLength() == 0) {
            throw new IllegalInputException("length needs to be not null.");
        }

        if (!rails.isEmpty()) {
            newSw.setSwitch(newSw.getDirection());
            Vertex checker = newSw.getStart();
            for (int i = 1; i < newSw.getSetLength(); i++) {
                checker.add(newSw.getDirection());
                if (knodes.contains(checker)) {
                    throw new LogicalException("Switch cutting another Rail");
                }
            }
            newSw.setSwitch(newSw.getDirectionTwo());
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
            if (!knodes.contains(newSw.getStart())) {
                knodes.add(new Knode(newSw.getStart(), newSw));
            }
            if (!knodes.contains(newSw.getEnd())) {
                knodes.add(new Knode(newSw.getEnd(), newSw));
            }
            if (!knodes.contains(newSw.getEndTwo())) {
                knodes.add(new Knode(newSw.getEndTwo(), newSw));
            }
        } else {
            knodes.add(new Knode(newSw.getStart(), newSw));
            knodes.add(new Knode(newSw.getEnd(), newSw));
            knodes.add(new Knode(newSw.getEndTwo(), newSw));
        }
        switches.add(newSw);
        rails.add(newSw);
        idCount++;
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
        Rail newRail = new Rail(start, end, idCount);
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
                ;
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
            if (!ListUtility.contains(knodes, newRail.getStart())) {
                knodes.add(new Knode(newRail.getStart(), newRail));
            }
            if (!ListUtility.contains(knodes, newRail.getEnd())) {
                knodes.add(new Knode(newRail.getEnd(), newRail));
            }
        } else {
            knodes.add(new Knode(newRail.getStart(), newRail));
            knodes.add(new Knode(newRail.getEnd(), newRail));
        }
        rails.add(newRail);
        idCount++;
        return rails.getLast().getId();
    }

    /**
     * ENtfernt eine Schiene aus dem Netz, wenn diese nicht notwendig ist
     * 
     * @param id der Schiene
     * @throws LogicalException, wenn Schiene notwendig
     */
    public void deleteTrack(int id) throws LogicalException {
        Rail delRail = getRailinSystem(id);
        int access = 0;
        for (Rail accessed : delRail.getConnected(null)) {
            if (accessed != null) {
                access++;
            }
        }
        if (access < 2) { // wenn eine schiene nur einen Anschluss hat, dann kann sie ohne Bedenken
                          // gelöscht werden.
            rails.remove(delRail);
            switches.remove(delRail);
            return;
        }
        LinkedList<Rail> notUse = new LinkedList<Rail>();
        notUse.add(delRail);
        // TODO löschen von Switches
        if (thereIsAWayWithout(notUse, delRail.getNext(), delRail.getPrevious(), delRail)) {
            rails.remove(delRail);
            switches.remove(delRail);
        } else {
            throw new LogicalException("track is necessary.");
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
    public boolean thereIsAWayWithout(LinkedList<Rail> notUse, Rail from, Rail to, Rail now) {
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
            if (thereIsAWayWithout(notUse, next, to, from)) {
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
        throw new LogicalException("track not existing");
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
    private void checkFreeKnodes(LinkedList<Vertex> knodes) throws IllegalInputException {
        int freeKnodes = 0;
        for (Knode knode : this.knodes) {
            if (ListUtility.contains(knodes, knode)) {
                freeKnodes++;
                if (!knode.isFree()) {
                    throw new IllegalInputException("knodes occupied.");

                }
            }
        }
        if (freeKnodes == 0) {
            throw new IllegalInputException("knodes not connected.");
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Rail rail : rails) {
            result.append(rail.toString());
            result.append("\n");
        }
        return result.toString().substring(0, result.length() - 1).trim();

    }

    /**
     * Setzt einen Zug auf das Schienenetz
     * 
     * @param train gesetzter Zug
     * @return ZugId
     * @throws LogicalException, wenn einer der benötigten Schienen besetzt ist
     */
    public String putTrain(SetTrain train) throws LogicalException {
        Vertex pos = train.getPosition();
        DirectionalVertex direc = train.getDirection();
        Rail track = findTrack(pos, direc);
        if (track.isOccupied()) {
            throw new LogicalException("track occupied.");
        }
        if (markBackOccupied(train, pos, direc, track, true)) {
            throw new LogicalException("tracks behind occupied.");
        } else {
            trainsOnTrack.add(train);
            train.setPosition(pos);
            train.setDirection(direc);
        }
        return "" + train.getId();

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
     */
    public boolean markBackOccupied(SetTrain train, Vertex pos, DirectionalVertex dire, Rail rail, boolean breakUp) {
        int i = rail.getSpaceLeftBehind(pos, dire);
        LinkedList<Rail> newlyOccupied = new LinkedList<Rail>();
        train.setRail(rail);
        Rail next = rail;
        Rail previous;
        while (i < train.getLength()) {
            previous = next;
            try {
                next = previous.getNextInDirection(dire.getInverseDirection());
            } catch (IllegalInputException e) {
            }
            dire = next.getDirectionFrom(previous.getEndInDirection(dire.getInverseDirection()));
            i += next.getLength();
            if (next.isOccupied() && breakUp) {
                return true;
            }
            newlyOccupied.add(next);
        }
        occupiedRails.addAll(newlyOccupied);
        occupiedRails = ListUtility.deleteDuplicates(occupiedRails);
        for (Rail newRail : newlyOccupied) {
            newRail.getTrains().add(train);
        }
        return false;
    }

    /**
     * Bewegt alle Züge um einen Schritt
     * 
     * @param forwards
     * @throws LogicalException
     */
    public void move(boolean forwards) throws LogicalException { // TODO backwards driving
        crashes = new LinkedList<Crash>();
        resetOccupied();
        for (SetTrain train : trainsOnTrack) {
            if (!train.move(forwards)) {
                train.getModel().setInUse(false);
                trainsOnTrack.remove(train);
                crashes.add(new Crash(train, "rolled into outside"));
            } else {
                markBackOccupied(train, train.getPosition(), train.getDirection(), train.getRail(), false);
            }
        }

        checkCollision();
    }

    /**
     * sucht nach Collisionen im Schienennetz und fügt diese der Liste an Crashes
     * hinzu.
     */
    public void checkCollision() {
        LinkedList<Rail> workingoccu = (LinkedList<Rail>) occupiedRails.clone();
        for (Rail rs : occupiedRails) {
            if (rs.getTrains().size() < 2) {
                workingoccu.remove(rs);
            }
        }
        while (workingoccu.size() > 0) {
            LinkedList<SetTrain> involved = (LinkedList<SetTrain>) workingoccu.get(0).getTrains().clone();
            LinkedList<SetTrain> newInv = (LinkedList<SetTrain>) involved.clone();
            do {
                involved = ListUtility.deleteDuplicates(involved);
                for (Rail or : occupiedRails) {
                    for (SetTrain tr : involved) {
                        if (or.getTrains().contains(tr)) {
                            newInv.addAll(or.getTrains());
                            workingoccu.remove(tr);
                        }
                    }
                }
            } while (!involved.containsAll(newInv));
            crashes.add(new Crash(involved, "crash"));
            for (SetTrain t : involved) {
                t.getModel().setInUse(false);
                trainsOnTrack.remove(t);
            }
        }
    }

    /**
     * Checkt, ob alle Weichen gesetzt sind
     * 
     * @return
     */
    public boolean isAllSet() {
        for (Switch s : switches) {
            if (!s.isSet()) {
                return false;
            }
        }
        return true;
    }

    /**
     * resetted alle Crashes und belegten Schienen
     */
    public void resetOccupied() {
        for (Rail rail : rails) {
            rail.getTrains().clear();
        }
        occupiedRails.clear();
        crashes.clear();
    }

    /**
     * Sucht eine Schiene auf dem Schienennetz
     * @param pos Position der Schiene
     * @param direc Richtung
     * @return gefunde Schiene
     * @throws LogicalException, wenn Schiene nicht existiert
     */
    public Rail findTrack(Vertex pos, DirectionalVertex direc) throws LogicalException {
        for (Knode knode : knodes) {
            if (knode.equals(pos)) {
                return knode.getTrack(direc);
            }
        }
        for (Rail rail : rails) {
            if (rail.contains(pos) && rail.isCorrectDirec(direc)) {
                return rail;
            }
        }
        throw new LogicalException("no fitting Track existing.");
    }

    /**
     * Setzt eine Weiche in Richtung des neuen Punktes
     * @param id der Weiche
     * @param point der das neue Ende sein soll
     * @throws IllegalInputException, wenn Point is not an End of the Switch
     */
    public void setSwitch(int id, Vertex point) throws IllegalInputException {
        for (Switch s : switches) {
            if (id == s.getId()) {
                s.setSwitch(point);
            }
        }
    }
}
