package julian.modelrailway.trackmaterial;

import java.util.LinkedList;

import julian.modelrailway.Exceptions.*;
import julian.modelrailway.events.*;
import julian.modelrailway.rollingmaterial.SetTrain;

/**
 * Verwaltet das Schienennetz und fahrende Züge. Benutzt für die Verwaltung des
 * Schienennetzes die Klasse RailNetwork
 * 
 * @author Julian Strietzel
 */
public class Railsystem2 {
    private final RailNetwork railnet;
    private final LinkedList<SetTrain> trainsOnTrack;
    private final LinkedList<Crash> crashes;

    /**
     * Erstellt eine neues Schienennetz und initialisiert alle Listen. Erstellt
     * außerdem ein neues Netzwerk.
     */
    public Railsystem2() {
        railnet = new RailNetwork(this);
        trainsOnTrack = new LinkedList<SetTrain>();
        crashes = new LinkedList<Crash>();
        resetMarkersAndCrashes();
    }

    /**
     * @return die aktuellen Krasches
     */
    public LinkedList<Crash> getCrashes() {
        return crashes;
    }

    /**
     * @return alle Züge auf dem Schienennetz
     */
    public LinkedList<SetTrain> getTrainsOnTrack() {
        return trainsOnTrack;
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
     * @return ZugId als Nutzerausgabe
     * @throws LogicalException,     wenn einer der benötigten Schienen besetzt ist
     */
    public String putTrain(SetTrain train, DirectionalVertex direc, Vertex pos)
            throws LogicalException {
        Rail track = railnet.findTrack(pos, direc);
        if (track == null) {
            throw new LogicalException("wrong placement");
        }
        if (track.isOccupied()) {
            throw new LogicalException("track occupied.");
        }
        if (ListUtility.contains(railnet.getKnodes(), pos) != null) {
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
     * @param forwards
     * @throws LogicalException
     * @throws IllegalInputException
     */
    public void move(boolean forwards) throws LogicalException, IllegalInputException {
        resetMarkersAndCrashes();
        for (SetTrain train : trainsOnTrack) {
            if (!train.move(forwards)) {
                train.getModel().setInUse(false);
                trainsOnTrack.remove(train);
                crashes.add(new Crash(train, "rolled into outside"));
            } else {
                railnet.markBackOccupied(train, train.getPosition(), train.getDirection(), train.getRail(), false);
            }
        }
        checkCollision();
    }

    /**
     * sucht nach Collisionen im Schienennetz und fügt diese der Liste an Crashes
     * hinzu.
     * 
     * @throws IllegalInputException
     */
    public void checkCollision() throws IllegalInputException {
        for (Rail r : railnet.getRails()) {
            LinkedList<SetTrain> workTrains = ListUtility.deleteDuplicates(r.getTrains());
            if (workTrains.size() > 1) {
                crashes.add(new Crash(r.getTrains(), "crash"));
                for (SetTrain t : r.getTrains()) {
                    t.getModel().setInUse(false);
                    trainsOnTrack.remove(t);
                }
            }
        }
        for (Knode k : railnet.getKnodes()) {
            LinkedList<SetTrain> workTrains = ListUtility.deleteDuplicates(k.getTrains());
            if (workTrains.size() > 1) {
                crashes.add(new Crash(k.getTrains(), "crash"));
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
     * @return
     */
    public boolean isAllSet() {
        for (Switch s : railnet.getSwitches()) {
            if (!s.isSet()) {
                return false;
            }
        }
        return true;
    }
    
    public void renewMarked() {
        for(SetTrain s: trainsOnTrack) {
            try {
                railnet.markBackOccupied(s, s.getPosition(), s.getDirection(), s.getRail(), false);
            } catch (IllegalInputException e) {
                System.out.println("ERROR");
            }
        }
    }

    /**
     * resetted alle Crashes und belegten Schienen
     */
    public void resetMarkersAndCrashes() {
        for (Rail rail : railnet.getRails()) {
            rail.getTrains().clear();
        }
        for (Knode kn : railnet.getKnodes()) {
            kn.getTrains().clear();
        }
//        crashes.clear();
    }

}