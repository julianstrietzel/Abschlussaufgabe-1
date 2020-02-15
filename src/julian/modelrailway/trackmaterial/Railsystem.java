/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;
import julian.modelrailway.*;
import julian.modelrailway.Exceptions.*;
import julian.modelrailway.events.Crash;
import julian.modelrailway.rollingmaterial.SetTrain;

public class Railsystem {

    public LinkedList<Rail> rails; // TODO change to private after testing
    public LinkedList<Knode> knodes;
    private LinkedList<Switch> switches;
    private LinkedList<Rail> occupiedRails;
    private LinkedList<SetTrain> trainsOnTrack;
    private LinkedList<Crash> crashes;
    
    public LinkedList<Rail> getRails() {
        return rails;
    }

    public void setRails(LinkedList<Rail> rails) {
        this.rails = rails;
    }

    public LinkedList<Knode> getKnodes() {
        return knodes;
    }

    public void setKnodes(LinkedList<Knode> knodes) {
        this.knodes = knodes;
    }

    public LinkedList<Switch> getSwitches() {
        return switches;
    }

    public void setSwitches(LinkedList<Switch> switches) {
        this.switches = switches;
    }

    public LinkedList<Rail> getOccupiedRails() {
        return occupiedRails;
    }

    public void setOccupiedRails(LinkedList<Rail> occupiedRails) {
        this.occupiedRails = occupiedRails;
    }

    public int getIdCount() {
        return idCount;
    }

    public void setIdCount(int idCount) {
        this.idCount = idCount;
    }

    public void setTrainsOnTrack(LinkedList<SetTrain> trainsOnTrack) {
        this.trainsOnTrack = trainsOnTrack;
    }

    public void setCrashes(LinkedList<Crash> crashes) {
        this.crashes = crashes;
    }

    private int idCount;

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

    public int addSwitch(Vertex start, Vertex endOne, Vertex endTwo) throws IllegalInputException, LogicalException {
        Switch newSw = new Switch(start, endOne, endTwo, idCount++);
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
                    throw new IllegalInputException("Switch cutting another Rail");
                }
            }
            newSw.unSet();
            if (checkTrackCollision(newSw.getStart(), newSw.getEnd())
                    || checkTrackCollision(newSw.getStart(), newSw.getEndTwo())) {
                throw new IllegalInputException("Switch cutting another Rail.");
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
            if (!knodes.contains(newSw.start)) {
                knodes.add(new Knode(newSw.getStart(), newSw));
            }
            if (!knodes.contains(newSw.end)) {
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
        return rails.getLast().getId();
    }

    public int addRail(Vertex start, Vertex end) throws IllegalInputException, LogicalException {
        Rail newRail = new Rail(start, end, idCount++);
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
        return rails.getLast().getId();
    }

    public void deleteTrack(int id) throws IllegalInputException, LogicalException {
        Rail delRail = trackOnBoard(id);
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

        if (thereIsAWayWithout(notUse, delRail.getNext(), delRail.getPrevious(), delRail)) {
            rails.remove(delRail);
            switches.remove(delRail);
        } else {
            throw new LogicalException("track is necessary.");
        }
    }

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

    public LinkedList<SetTrain> getTrainsOnTrack() {
        return trainsOnTrack;
    }
    
    private Rail trackOnBoard(int id) throws IllegalInputException {
        for (Rail rail : rails) {
            if (rail.getId() == id) {
                return rail;
            }
        }
        throw new IllegalInputException("track not existing");
    }

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

    public LinkedList<Crash> getCrashes() {
        return crashes;
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

    public String addTrain(SetTrain train) throws LogicalException {
        Vertex pos = train.getPosition();
        DirectionalVertex direc  = train.getDirection();
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

    public boolean markBackOccupied(SetTrain train, Vertex pos, DirectionalVertex dire, Rail rail, boolean breakUp) {
        int i = rail.getSpaceLeftBehind(pos, dire);
        LinkedList<Rail> newlyOccupied = new LinkedList<Rail>();
        newlyOccupied.add(rail);
        train.setRail(rail);
        Rail next = rail;
        Rail previous;
        while (i < train.getLength()) {
            previous = next;
            try {
                next = previous.getNextInDirection(dire.getInverseDirection());
            } catch (IllegalInputException e) {
//                throw new LogicalException("i dont know whjat happend.");
            }
            dire = next.getDirectionFrom(previous.getEndInDirection(dire.getInverseDirection()));
            i += next.getLength();
            System.out.println(i);
            if (next.isOccupied() && breakUp) {
                return true;
            }
            newlyOccupied.add(next);
        }
        occupiedRails.addAll(newlyOccupied);
        for (Rail newRail : newlyOccupied) {
            newRail.trains.add(train);
        }
        return false;

    }

    public void move(boolean forwards) throws LogicalException { //TODO backwards driving
        crashes = new LinkedList<Crash>();
        resetOccupied();
        for (SetTrain train : trainsOnTrack) {
            if (!train.move(forwards)) {
                trainsOnTrack.remove(train);
                crashes.add(new Crash(train, "rolled into outside"));
            } else {
                markBackOccupied(train, train.getPosition(), train.getDirection(), train.getRail(), false);
            }
        }
        checkCollision();
    }

    @SuppressWarnings("unchecked")
    public void checkCollision() {
        LinkedList<Rail> workingoccu = (LinkedList<Rail>) occupiedRails.clone();
        for(Rail rs: occupiedRails) {
            if(rs.getTrains().size() <= 1) {
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
            for(SetTrain t: involved) {
                trainsOnTrack.remove(t);
            }
        }
//        }
    }

    public boolean isAllSet() {
        for(Switch s:switches) {
            if(!s.isSet()) {
                return false;
            }
        }
        return true;
    }
    
    public LinkedList<Rail> resetOccupied() {
        for (Rail rail : rails) {
            rail.trains.clear();
        }
        occupiedRails.clear();
        crashes.clear();
        return occupiedRails;
    }

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
}
