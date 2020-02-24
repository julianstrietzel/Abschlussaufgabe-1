
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;
import java.util.List;

import julian.modelrailway.Exceptions.*;
import julian.modelrailway.events.*;
import julian.modelrailway.rollingmaterial.SetTrain;

/**
 * Verwaltet das Schienennetz und fahrende Züge. Benutzt für die Verwaltung des
 * Schienennetzes die Klasse RailNetwork
 * 
 * @author Julian Strietzel
 */
public class Railsystem {
    private final RailNetwork railnet;
    private final List<SetTrain> trainsOnTrack;
    private final List<Crash> crashes;

    /**
     * Erstellt eine neues Schienennetz und initialisiert alle Listen. Erstellt
     * außerdem ein neues Netzwerk.
     */
    public Railsystem() {
        railnet = new RailNetwork(this);
        trainsOnTrack = new LinkedList<SetTrain>();
        crashes = new LinkedList<Crash>();
        resetMArkers();
    }

    /**
     * @return die aktuellen Krasches
     */
    public List<Crash> getCrashes() {
        LinkedList<Crash> workCrashes = new LinkedList<Crash>();
        for (Crash c : crashes) {
            boolean there = false;
            for (Crash wc : workCrashes) {
                for (SetTrain s : c.getInvolved()) {
                    if (wc.isInvolved(s)) {
                        there = true;
                        wc.addInvolved(c.getInvolved());
                    }
                }
            }
            if (!there) {
                workCrashes.add(c);
            }
        }
        return workCrashes;
    }

    /**
     * @return Eine Kopie der Liste aller Züge auf dem Schienennetz
     */
    public List<SetTrain> getToTCopy() {

        List<SetTrain> l = new LinkedList<SetTrain>();
        l.addAll(trainsOnTrack);
        return l;

    }

    /**
     * 
     * @return das Schienennetz
     */
    public RailNetwork getRailNet() {
        return railnet;
    }

    /**
     * Setzt einen Zug auf das Schienenetz
     * 
     * @param train gesetzter Zug
     * @param direc Richtung des Zuges
     * @param pos   Position des Zuges
     * @return ZugId als Nutzerausgabe
     * @throws LogicalException , wenn einer der benötigten Schienen besetzt ist
     */
    public String putTrain(SetTrain train, DirectionalVertex direc, Vertex pos) throws LogicalException {
        Rail track = railnet.findTrack(pos, direc);
        if (track == null) {
            throw new LogicalException("wrong placement");
        }
        if (track.isOccupied()) {
            throw new LogicalException("track occupied.");
        }
        if (ListUtility.contains(railnet.getCopyKnodes(), pos) != null) {
            train.setDirection(track.getDirectionTo(pos)); // Auch wenn an Ecke gesetzt muss die richtige richtung
                                                           // eingespeihcer sein
        }
        try {
            if (railnet.markBackOccupied(train, train.getPosition(), train.getDirection(), track, true)) {
                throw new LogicalException("tracks behind occupied.");
            } else {
                trainsOnTrack.add(train);
            }
        } catch (IllegalInputException e) {
            throw new LogicalException("wrong set.");
        }
        return "" + train.getId();

    }

    /**
     * Bewegt alle Züge um einen Schritt
     * 
     * @param forwards Ob der zug vorwärts fährt
     * @throws LogicalException      , wenn der Zug entgleist
     * @throws IllegalInputException , wenn Fehler im markoccupied()
     */
    public void move(boolean forwards) throws LogicalException, IllegalInputException {
        resetMArkers();
        List<SetTrain> workTrains = new LinkedList<SetTrain>();
        for (SetTrain s : trainsOnTrack) {
            workTrains.add(s);
        }
        for (SetTrain train : workTrains) {
            if (!train.move(forwards)) {
                trainsOnTrack.remove(train);
                crashes.add(new Crash(train));
            } else {
                try {
                    railnet.markBackOccupied(train, train.getPosition(), train.getDirection(), train.getRail(), false);
                } catch (LogicalException e) {
                    train.getModel().setInUse(false);
                    trainsOnTrack.remove(train);
                    crashes.add(new Crash(train));
                }
            }
        }
        checkCollision();
    }

    /**
     * sucht nach Collisionen im Schienennetz und fügt diese der Liste an Crashes
     * hinzu.
     * 
     */
    public void checkCollision() {
        for (Rail r : railnet.getCopyRails()) {
            List<SetTrain> workTrains = ListUtility.copyWithoutDuplicates(r.getCopyTrains());
            if (workTrains.size() > 1) {
                crashes.add(new Crash(r.getCopyTrains()));
                for (SetTrain t : r.getCopyTrains()) {
                    t.getModel().setInUse(false);
                    trainsOnTrack.remove(t);
                }
            }
        }
        for (Knode k : railnet.getCopyKnodes()) {
            List<SetTrain> workTrains = ListUtility.copyWithoutDuplicates(k.getTrains());
            if (workTrains.size() > 1) {
                crashes.add(new Crash(k.getTrains()));
                for (SetTrain t : k.getTrains()) {
                    t.getModel().setInUse(false);
                    trainsOnTrack.remove(t);
                }
            }
        }
    }

    /**
     * Checkt, ob alle Weichen gesetzt sind
     * 
     * @return ob alle Weichen gesetzt sind
     */
    public boolean isAllSet() {
        for (Switch s : railnet.getCopySwitches()) {
            if (!s.isSet()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Erneuert alle Marker auf dem Schienennetz
     * 
     * @throws LogicalException , wenn interner Fehler
     */
    public void renewMarked() throws LogicalException {

        for (SetTrain s : trainsOnTrack) {
            try {
                railnet.markBackOccupied(s, s.getPosition(), s.getDirection(), s.getRail(), false);
            } catch (IllegalInputException e) {
                System.out.println("ERROR");
            }
        }
    }

    /**
     * resetted belegten Schienen
     */
    public void resetMArkers() {
        for (Rail rail : railnet.getCopyRails()) {
            rail.clearTrains();
        }
        for (Knode kn : railnet.getCopyKnodes()) {
            kn.clearTrains();
        }
    }

    /**
     * Löscht das Crash-Protokoll.
     */
    public void clearCrashes() {
        this.crashes.clear();
    }

    /**
     * 
     * @return Ob ein Zug auf der Strecke steht
     */
    public boolean trainsOn() {
        return !trainsOnTrack.isEmpty();
    }

    /**
     * Entfernt einen Zug vom Netz
     * 
     * @param train dieser Zug wird entfernt, wenn existierend.
     */
    public void removeTrain(SetTrain train) {
        trainsOnTrack.remove(train);
    }

}