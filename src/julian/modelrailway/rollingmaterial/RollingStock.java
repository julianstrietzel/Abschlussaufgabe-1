/**
 * Diese Klasse speichert das gesamte RollMaterial
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

import java.util.HashMap;
import java.util.LinkedList;

import julian.modelrailway.Exceptions.*;
import julian.modelrailway.trackmaterial.ListUtility;

public class RollingStock {

    private LinkedList<PoweredRolling> powered;
    private HashMap<Integer, Coach> coaches;

    /**
     * Erstellt eine neue Verwaltungsklasse
     */
    public RollingStock() {
        powered = new LinkedList<PoweredRolling>();
        coaches = new HashMap<Integer, Coach>();
    }

    /**
     * Erstellt eine neue Lokomotive
     * @param engineType    (electrical|diesel|steam) -> Typ der Lokomotive 
     * @param series        Baureihe
     * @param name          Name
     * @param length        Länge
     * @param cFront        Ob Kupplung vorne
     * @param cBack         Ob Kupplung hinten
     * @return  ID der neuen Lokomotive
     * @throws LogicalException, wenn Lokomotive schon existiert
     */
    public String createEngine(String engineType, String series, String name, int length, boolean cFront, boolean cBack)
            throws LogicalException {
        String id = series + "-" + name;
        if (RollingMaterial.exists(powered, id) != null) {
            throw new LogicalException("Engine already existing.");
        }
        if ("electrical".contentEquals(engineType)) {
            powered.add(new ElectricLocomotive(series, name, length, cFront, cBack));
        }
        if ("steam".contentEquals(engineType)) {
            powered.add(new SteamLocomotive(series, name, length, cFront, cBack));
        }
        if ("diesel".contentEquals(engineType)) {
            powered.add(new DieselLocomotive(series, name, length, cFront, cBack));
        }
        return powered.getLast().getID();
    }

    /**
     * Erstellt einen neuen Triebzug
     * @param series    Baureihe
     * @param name      Name
     * @param length    Länge
     * @param cFront    Ob Kupplung vorne
     * @param cBack     Ob Kupplung hinten
     * @return  ID des neuen Triebzuges
     * @throws LogicalException, wenn Triebzug schon existiert
     */
    public String createTrainSet(String series, String name, int length, boolean cFront, boolean cBack)
            throws LogicalException {
        String id = series + "-" + name;
        if (RollingMaterial.exists(powered, id) != null) {
            throw new LogicalException("train-set already existing.");
        }
        powered.add(new TrainSet(series, name, length, cFront, cBack));
        return powered.getLast().getID();
    }

    /**
     * Erstellt einen neuen Waggon
     * @param coachType Typ des Waggons
     * @param length    Länge
     * @param cFront    Ob Kupplung vorne
     * @param cBack     Ob Kupplung hinten
     * @return ID des neuen Waggons
     */
    public String createCoach(String coachType, int length, boolean cFront, boolean cBack) {
        if ("passenger".contentEquals(coachType)) {
            coaches.put(coaches.size() + 1, new PassengerWagon(length, cFront, cBack, coaches.size() + 1));
        }
        if ("freight".contentEquals(coachType)) {
            coaches.put(coaches.size() + 1, new FreightCar(length, cFront, cBack, coaches.size() + 1));
        }
        if ("special".contentEquals(coachType)) {
            coaches.put(coaches.size() + 1, new SpecialCar(length, cFront, cBack, coaches.size() + 1));
        }
        return "" + coaches.size();
    }

    /**
     * entfernt das entsprechende RollMaterial
     * @param isPowered Ob es sich um einen Waggon mit Motor handelt
     * @param id        Id des Materials
     * @return  "OK"
     * @throws IllegalInputException, wenn ID für Coach kein Integer
     * @throws LogicalException, wenn Material in Nutzung oder nicht existiert.
     */
    public String delete(boolean isPowered, String id) throws IllegalInputException, LogicalException {
        if (isPowered) {
            PoweredRolling p = RollingMaterial.exists(powered, id); // TODO what happens, when Stock in Train
            if (p == null) {
                throw new LogicalException("there is no powered stock with that id.");
            }
            if (p.getTrain() != null) {
                throw new LogicalException("rolling stock is used in train.");
            }
            powered.remove(p);
        } else {
            int intID;
            try {
                intID = Integer.parseInt("id");
            } catch (NumberFormatException e) {
                throw new IllegalInputException("id of Coach needs to be an Integer.");
            }
            if (!coaches.containsKey(intID)) {
                throw new LogicalException("coach not existing.");
            }
            if (coaches.get(intID).getTrain() != null) {
                throw new LogicalException("coach is used in Train.");
            }
            coaches.remove(intID);
        }
        return "OK";
    }

    /**
     * 
     * @return Liste der Lokomotiven
     */
    public String enginestoString() {
        powered.sort(null);
        StringBuilder sb = new StringBuilder();
        for (PoweredRolling p : powered) {
            if (p instanceof Engine && p != null) {
                sb.append(p.toString() + "\n");
            }
        }
        if (sb.length() == 0) {
            return "No engine exists.";
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 
     * @return Liste der Triebzüge
     */
    public String trainSettoString() {
        powered.sort(null);
        StringBuilder sb = new StringBuilder();
        for (PoweredRolling p : powered) {
            if (p instanceof TrainSet && p != null) {
                sb.append(p.toString() + "\n");
            }
        }
        if (sb.length() == 0) {
            return "No train-set exists";
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 
     * @return Liste der Waggons
     */
    public String coachestoString() {
        if (coaches.isEmpty()) {
            return "No coach exists";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= coaches.size(); i++) {
            sb.append(coaches.get(i).toString() + "\n");

        }
        return sb.substring(0, sb.length() - 1);

    }
    
    /**
     * Sucht das Material mit der ID
     * @param id    als String
     * @return  das gefunden Material oder null, wenn nicht existend
     */
    public RollingMaterial getWagon(String id) {
        for(PoweredRolling p: powered) {
            if(p.getID().contentEquals(id)) {
                return p;
            }
        }
        int intID;
        try {
            intID = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return null;
        }
        return coaches.get(intID);
    }

}
