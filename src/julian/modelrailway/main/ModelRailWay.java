
package julian.modelrailway.main;

import java.util.LinkedList;

import julian.modelrailway.events.Event;
import julian.modelrailway.events.TrainMoved;
import julian.modelrailway.exceptions.IllegalInputException;
import julian.modelrailway.exceptions.LogicalException;
import julian.modelrailway.rollingmaterial.*;
import julian.modelrailway.trackmaterial.*;

/**
 * Oberste Verwaltungsklasse. Verwaltet Züge im TrainStock, das Schienennetz
 * inkl. fahrender Züge im Railsystem und das RollMaterial im RollingStock.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class ModelRailWay {

    private final Railsystem rSystem;
    private final TrainStock ts;
    private final RollingStock rstock;

    /**
     * Erstellt eine neue Modelleisenbahn mit allem, was dazu gehört.
     */
    public ModelRailWay() {
        rSystem = new Railsystem();
        rstock = new RollingStock();
        ts = new TrainStock(rstock);
    }

    /**
     * Setzt im Schienennetzwerk eine neue normale Schiene
     * 
     * @param startX X-Koordinate des Startpunktes
     * @param startY Y-Koordinate des Startpunktes
     * @param endX   X-Koordinate des Endpunktes
     * @param endY   Y-Koordinate des Endpunktes
     * @return Ausgabe für den Nutzer
     * @throws IllegalInputException , wenn die Länge der Schiene null ist.
     * @throws LogicalException      , wenn es logische Fehler in der Interaktion
     *                               mit dem restlichen Schienennetz gibt.
     */
    public String addTrack(int startX, int startY, int endX, int endY) throws IllegalInputException, LogicalException {
        return "" + rSystem.addRail(new Vertex(startX, startY), new Vertex(endX, endY));
    }

    /**
     * Setzt im Schienennetzwerk eine neue normale Schiene
     * 
     * @param startX X-Koordinate des Startpunktes
     * @param startY Y-Koordinate des Startpunktes
     * @param endX   X-Koordinate des Endpunktes
     * @param endY   Y-Koordinate des Endpunktes
     * @param end2x  X-Koordinate des zweiten Endpunktes
     * @param end2y  Y-Koordinate des zweiten Endpunktes
     * @return Ausgabe für den Nutzer
     * @throws IllegalInputException , wenn die Länge der Schiene null ist.
     * @throws LogicalException      , wenn es logische Fehler in der Interaktion
     *                               mit dem restlichen Schienennetz gibt.
     */
    public String addSwitch(int startX, int startY, int endX, int endY, int end2x, int end2y)
            throws IllegalInputException, LogicalException {

        return "" + rSystem.addSwitch(new Vertex(startX, startY), new Vertex(endX, endY), new Vertex(end2x, end2y));

    }

    /**
     * Erstellt einen neuen Waggon im RollingStock
     * 
     * @param coachType Typ des Waggons. (freight, passenger, special)
     * @param length    Länge des Waggons
     * @param cFront    Ob der Waggon vorne eine Kupplung hat
     * @param cBack     Ob der Waggon hinten eine Kupplung hat
     * @return Ausgabe für den Nutzer
     * @throws IllegalInputException , wenn Länge = null
     */
    public String createCoach(String coachType, int length, boolean cFront, boolean cBack)
            throws IllegalInputException {
        if (length == 0) {
            throw new IllegalInputException("length null not allowed.");
        }
        return rstock.createCoach(coachType, length, cFront, cBack);
    }

    /**
     * Erstellt einen neue Lokomotive im RollingStock
     * 
     * @param engineType Typ der Lok (diesel,strom,Dampf)
     * @param series     Baureihe
     * @param name       Name der lok
     * @param length     Länge der Lok
     * @param cFront     Ob die Lok vorne eine Kupplung hat
     * @param cBack      Ob die Lok hinten eine Kupplung hat
     * @return Nutzer Ausgabe
     * @throws LogicalException , wenn Lok schon existiert oder Länge gliech null
     * @throws IllegalInputException wenn Series mit W beginnt
     */
    public String createEngine(String engineType, String series, String name, int length, boolean cFront, boolean cBack)
            throws LogicalException, IllegalInputException {
        if (length == 0) {
            throw new LogicalException("length null not allowed.");
        }
        return rstock.createEngine(engineType, series, name, length, cFront, cBack);
    }

    /**
     * Erstellt einen neuen Triebwagen im RollingStock
     * 
     * @param series Baureihe
     * @param name   Name
     * @param length Länge des triebwagnes
     * @param cFront Ob der Triebwagen vorne eine Kupplung hat
     * @param cBack  Ob der Triebwagen hinten eine Kupplung hat
     * @return Nutzer Ausgabe
     * @throws LogicalException , wenn Triebwagen schon existiert oder Länge gleich
     *                          null
     * @throws IllegalInputException wenn Series mit W beginnt
     */
    public String createTrainSet(String series, String name, int length, boolean cFront, boolean cBack)
            throws LogicalException, IllegalInputException {
        if (length == 0) {
            throw new LogicalException("length null not allowed.");
        }
        return rstock.createTrainSet(series, name, length, cFront, cBack);
    }

    /**
     * Löäscht ein bestimmtes RollMaterial
     * 
     * @param isPowered Ob es sich um etwas motorisiertes handelt
     * @param id        String Id des gesuchten Materials
     * @return Nutzerausgabe
     * @throws IllegalInputException , wenn ID für Waggon kein Integer.
     * @throws LogicalException      , Material in Benutzung oder nicht existiert.
     */
    public String delete(boolean isPowered, String id) throws IllegalInputException, LogicalException {
        return rstock.delete(isPowered, id);
    }

    /**
     * Löscht bestimmtes SchienenMaterial
     * 
     * @param id Id des Materials
     * @return Nutzerausgbae
     * @throws LogicalException , wenn die Schiene für den Zusammenhalt nötig ist.
     */
    public String deleteTrack(int id) throws LogicalException {
        rSystem.deleteTrack(id);
        return "OK";
    }

    /**
     * Hängt an einen Zug das RollMaterial an. Wenn dieser noch nicht existiert wird
     * er neu erstellt
     * 
     * @param trainID neue ID des Zuges
     * @param rollID  ID des RollMaterials, das angehängt werden soll
     * @param powered ob das gesuchte Material motorisiert ist
     * @return Nutzerausgabe
     * @throws LogicalException , RollMaterial nicht existiert oder schon verbaut
     *                          ist, der Zug fährt oder die ID Vergabe falsch ist
     * @throws IllegalInputException wenn id falsch
     */
    public String addTrain(int trainID, String rollID, boolean powered) throws LogicalException, IllegalInputException {
        return ts.addTrain(trainID, rollID, powered);
    }

    /**
     * Löscht einen Zug aus dem Stock und von der Strecke.
     * 
     * @param id ID des Zuges
     * @return Nutzerausgabe
     * @throws LogicalException , wenn Zug nicht existiert.
     */
    public String deleteTrain(int id) throws LogicalException {
        for (SetTrain s : rSystem.getToTCopy()) {
            if (s.getId() == id) {
                rSystem.removeTrain(s);
            }
        }
        rSystem.resetMarkers();
        rSystem.renewMarked();
        return ts.deleteTrain(id);
    }

    /**
     * Setzt einen Zug auf das Schienennetz
     * 
     * @param id        des Zuges
     * @param pos       neue Position auf die gesetzt werden soll
     * @param direction Richtung in die gesetzt werden soll
     * @return Nutzerausgabe
     * @throws LogicalException , wenn Zug schon existiert, oder auf dem Netz steht
     */
    public String putTrain(int id, Vertex pos, DirectionalVertex direction) throws LogicalException {
        Train model = ts.getTrain(id);
        if (model == null) {
            throw new LogicalException("train does not exist.");
        }
        if (model.inUse()) {
            throw new LogicalException("train already on track.");
        }
        if (!model.hasPower()) {
            throw new LogicalException("train has no power.");
        }
        rSystem.putTrain(new SetTrain(model, direction, pos), direction, pos);
        model.setInUse(true);
        return "OK";

    }

    /**
     * Bewegt alle Züge auf dem Schienennetz um <speed> Schritte Dabei wird nach
     * jedem Schritt nach kollisionen überprüft (siehe move in Railsystem) In Events
     * werden alle Crashes gespeichert
     * 
     * @param speed Anzahl an Schritten
     * @return Neue Positionen und Crashes
     * @throws LogicalException      , wenn Weichen noch nicht gesetzt wurden
     * @throws IllegalInputException , wenn Fehler im Schienennetz
     */
    public String move(short speed) throws LogicalException, IllegalInputException {
        rSystem.clearCrashes();
        if (!rSystem.isAllSet()) {
            throw new LogicalException("position of switches not set.");
        }
        if (!rSystem.trainsOn()) {
            return "OK";
        }
        LinkedList<Event> events = new LinkedList<Event>();
        for (int i = 0; i < Math.abs(speed); i++) {
            rSystem.move(Math.abs(speed) == speed);
        }
        for (SetTrain t : rSystem.getToTCopy()) {
            events.add(new TrainMoved(t));
        }
        events.addAll(rSystem.getCrashes());
        events.sort(null);
        StringBuilder sb = new StringBuilder();
        for (Event e : events) {
            sb.append(e.getMessage());
            sb.append("\n");
        }
        rSystem.resetMarkers();
        for (SetTrain train : rSystem.getToTCopy()) {
            rSystem.markBackOccupied(train, train.getPosition(), train.getDirection(), train.getRail(), false);

        }
        return sb.substring(0, sb.length() - 1).toString();
    }

    /**
     * 
     * @return Listet alle Schienen auf
     */
    public String listTracks() {
        return rSystem.listRailNet();
    }

    /**
     * 
     * @return Liste aller Züge
     */
    public String listTrains() {
        return ts.toString();
    }

    /**
     * Zeigt eine Visualisierung eines bestimmten Zuges
     * 
     * @param trainID ID des Zuges
     * @return STring Visualisierung eines Zuges
     * @throws LogicalException , wenn Zug nicht existiert
     */
    public String showTrain(int trainID) throws LogicalException {
        try {
            return ts.getTrain(trainID).getRepre();
        } catch (NullPointerException e) {
            throw new LogicalException("train does not exist.");
        }
    }

    /**
     * 
     * @return Stringrepräsentation aller Waggons
     */
    public String coachestoString() {
        return rstock.coachestoString();
    }

    /**
     * 
     * @return Liste aller Lokomotiven
     */
    public String enginestoString() {
        return rstock.enginestoString();
    }

    /**
     * 
     * @return Liste aller Triebzüge
     */
    public String trainSettoString() {
        return rstock.trainSettoString();
    }

    /**
     * Setzt eine bestimmte Weiche auf eine bestimmte Position
     * 
     * @param id    der Weiche
     * @param point neue Einstellung der Weiche
     * @throws IllegalInputException , wenn Weiche nicht existiert oder der Punkt
     *                               nicht auf der Weiche liegt oder die Falsche ID übergeeben wurde
     * @throws LogicalException      , wenn Fehler in markoccupied()
     */
    public void setSwitch(int id, Vertex point) throws IllegalInputException, LogicalException {
        rSystem.setSwitch(id, point);
    }

}
