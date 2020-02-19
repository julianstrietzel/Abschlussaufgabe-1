/**
 * Oberste Verwaltungsklasse.
 * Verwaltet Züge im TrainStock, das Schienennetz inkl. fahrender Züge im Railsystem und 
 * das RollMaterial im RollingStock.
 * @author Julian Strietzel
 */
package julian.modelrailway;

import java.util.LinkedList;

import julian.modelrailway.Exceptions.*;
import julian.modelrailway.events.Event;
import julian.modelrailway.events.TrainMoved;
import julian.modelrailway.rollingmaterial.*;
import julian.modelrailway.trackmaterial.*;

public class ModelRailWay {

    private final Railsystem2 rSystem;
    private final TrainStock ts;
    private final RollingStock rstock;

    public ModelRailWay() {
        rSystem = new Railsystem2();
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
     * @throws IllegalInputException, wenn die Länge der Schiene null ist.
     * @throws LogicalException,      wenn es logische Fehler in der Interaktion mit
     *                                dem restlichen Schienennetz gibt.
     */
    public String addTrack(int startX, int startY, int endX, int endY) throws IllegalInputException, LogicalException {
        return "" + rSystem.getRailNet().addRail(new Vertex(startX, startY), new Vertex(endX, endY));
    }

    /**
     * Setzt im Schienennetzwerk eine neue normale Schiene
     * 
     * @param startX X-Koordinate des Startpunktes
     * @param startY Y-Koordinate des Startpunktes
     * @param endX   X-Koordinate des Endpunktes
     * @param endY   Y-Koordinate des Endpunktes
     * @param end2x  X-Koordinate des zweiten Endpunktes
     * @param end2Y  Y-Koordinate des zweiten Endpunktes
     * @return Ausgabe für den Nutzer
     * @throws IllegalInputException, wenn die Länge der Schiene null ist.
     * @throws LogicalException,      wenn es logische Fehler in der Interaktion mit
     *                                dem restlichen Schienennetz gibt.
     */
    public String addSwitch(int startX, int startY, int endX, int endY, int end2x, int end2y)
            throws IllegalInputException, LogicalException {

        return "" + rSystem.getRailNet().addSwitch(new Vertex(startX, startY), new Vertex(endX, endY),
                new Vertex(end2x, end2y));

    }

    /**
     * Erstellt einen neuen Waggon im RollingStock
     * 
     * @param coachType Typ des Waggons. (freight, passenger, special)
     * @param length    Länge des Waggons
     * @param cFront    Ob der Waggon vorne eine Kupplung hat
     * @param cBack     Ob der Waggon hinten eine Kupplung hat
     * @return Ausgabe für den Nutzer
     */
    public String createCoach(String coachType, int length, boolean cFront, boolean cBack) {
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
     * @throws LogicalException, wenn Lok schon existiert
     */
    public String createEngine(String engineType, String series, String name, int length, boolean cFront, boolean cBack)
            throws LogicalException {
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
     * @throws LogicalException, wenn Triebwagen schon existiert
     */
    public String createTrainSet(String series, String name, int length, boolean cFront, boolean cBack)
            throws LogicalException {
        return rstock.createTrainSet(series, name, length, cFront, cBack);
    }

    /**
     * Löäscht ein bestimmtes RollMaterial
     * 
     * @param isPowered Ob es sich um etwas motorisiertes handelt
     * @param id        String Id des gesuchten Materials
     * @return Nutzerausgabe
     * @throws IllegalInputException, wenn ID für Waggon kein Integer.
     * @throws LogicalException,      Material in Benutzung oder nicht existiert.
     */
    public String delete(boolean isPowered, String id) throws IllegalInputException, LogicalException {
        return rstock.delete(isPowered, id);
    }

    /**
     * Löscht bestimmtes SchienenMaterial
     * 
     * @param id Id des Materials
     * @return Nutzerausgbae
     * @throws LogicalException, wenn die Schiene für den Zusammenhalt nötig ist.
     */
    public String deleteTrack(int id) throws LogicalException {
        rSystem.getRailNet().deleteTrack(id);
        return "OK";
    }

    /**
     * Hängt an einen Zug das RollMaterial an. Wenn dieser noch nicht existiert wird
     * er neu erstellt
     * 
     * @param trainID neue ID des Zuges
     * @param rollID  ID des RollMaterials, das angehängt werden soll
     * @return Nutzerausgabe
     * @throws LogicalException, RollMaterial nicht existiert oder schon verbaut
     *                           ist, der Zug fährt oder die ID Vergabe falsch ist
     */
    public String addTrain(int trainID, String rollID) throws LogicalException {
        return ts.addTrain(trainID, rollID);
    }

    /**
     * Löscht einen Zug aus dem Stock und von der Strecke.
     * 
     * @param id ID des Zuges
     * @return Nutzerausgabe
     * @throws LogicalException, wenn Zug nicht existiert.
     */
    public String deleteTrain(int id) throws LogicalException {
        for (SetTrain s : rSystem.getTrainsOnTrack()) {
            if (s.getId() == id) {
                rSystem.getTrainsOnTrack().remove(s);
            }
        }
        return ts.deleteTrain(id);
    }

    /**
     * Setzt einen Zug auf das Schienennetz
     * 
     * @param id        des Zuges
     * @param pos       neue Position auf die gesetzt werden soll
     * @param direction Richtung in die gesetzt werden soll
     * @return Nutzerausgabe
     * @throws LogicalException, wenn Zug schon existiert, oder auf dem Netz steht
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
     * Bewegt alle Züge auf dem Schienennetz um <speed> Schritte
     * 
     * @param speed Anzahl an Schritten
     * @return Neue Positionen und Crashes
     * @throws LogicalException,      wenn Weichen noch nicht gesetzt wurden
     * @throws IllegalInputException, wenn Fehler im Schienennetz
     */
    public String move(int speed) throws LogicalException, IllegalInputException {
        rSystem.getCrashes().clear();
        if (!rSystem.isAllSet()) {
            throw new LogicalException("position of switches not set.");
        }
        if (rSystem.getTrainsOnTrack().isEmpty()) {
            return "OK";
        }
        LinkedList<Event> events = new LinkedList<Event>();
        for (int i = 0; i < Math.abs(speed); i++) {
            rSystem.move(Math.abs(speed) == speed);
        }
        for (SetTrain t : rSystem.getTrainsOnTrack()) {
            events.add(new TrainMoved(t, ""));
        }
        events.addAll(rSystem.getCrashes());
        events.sort(null);
        StringBuilder sb = new StringBuilder();
        for (Event e : events) {
            sb.append(e.getMessage());
            sb.append("\n");
        }
        rSystem.resetMarkersAndCrashes();
        for (SetTrain train : rSystem.getTrainsOnTrack()) {
            rSystem.getRailNet().markBackOccupied(train, train.getPosition(), train.getDirection(), train.getRail(),
                    false);

        }
        return sb.substring(0, sb.length() - 1).toString();

    }

    /**
     * 
     * @return Listet alle Schienen auf
     */
    public String listTracks() {
        return rSystem.getRailNet().toString();
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
     * @throws LogicalException, wenn Zug nicht existiert
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
     * @throws IllegalInputException, wenn Weiche nicht existiert oder der Punkt
     *                                nicht auf der Weiche lilegt.
     */
    public void setSwitch(int id, Vertex point) throws IllegalInputException {
        rSystem.getRailNet().setSwitch(id, point);
    }

}
