
package julian.modelrailway.rollingmaterial;

import java.util.HashMap;

import julian.modelrailway.Exceptions.LogicalException;

/**
 * Verwaltet die zusammengesetzten Züge
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class TrainStock {

    private final HashMap<Integer, Train> trains;
    private final RollingStock rStock;

    /**
     * Erstellt einen neuen Trainstock mit model als Bezugsobjekt
     * 
     * @param rStock die Lagerung aller Waggons
     */
    public TrainStock(RollingStock rStock) {
        this.rStock = rStock;
        trains = new HashMap<Integer, Train>();
    }

    /**
     * Erstellt einen neuen Zug oder hängt dem entsprechenden hinten ein
     * RollmAterial an.
     * 
     * @param trainID Zug, an den angehöngt wrrden soll
     * @param rollID  Id des RollMaterials
     * @return MatType <MatID> added to <ZugID>
     * @throws LogicalException , wenn der Waggon nicht angehängt werden darf
     */
    public String addTrain(int trainID, String rollID) throws LogicalException {
        RollingMaterial r = rStock.getWagon(rollID);
        if (r == null) {
            throw new LogicalException("rolling material not existing.");
        }
        if (r.isUsed()) {
            throw new LogicalException("rolling material is used.");
        }
        if (trains.containsKey(trainID)) {
            Train t = trains.get(trainID);
            if (t.inUse()) {
                throw new LogicalException("train on track.");
            }
            return t.add(r);
        } else {
            for (int i = trainID - 1; i > 0; i--) {
                if (!trains.containsKey(i)) {
                    throw new LogicalException("free train ID " + i + " needs to be used first!");
                }
            }
            trains.put(trainID, new Train(r, trainID));
            return r.getTypeForAdding() + " " + r.getWStringID() + " added to train " + trainID;
        }
    }

    /**
     * Löscht einen Zug und entfernt ihn vom
     * 
     * @param id des gesuchten Zuges
     * @return String "OK" wenn Löscung erfolgreich
     * @throws LogicalException , wenn der Zug nicht existiert
     */
    public String deleteTrain(int id) throws LogicalException {
        Train t = trains.get(id);
        if (t == null) {
            throw new LogicalException("train not existing.");
        }
        t.markUnUsed();
        trains.remove(id);
        return "OK";
    }

    /**
     * 
     * @param id des gesuchten Zuges
     * @return gesuchter Zug oder null wenn dieser nicht existiert.
     */
    public Train getTrain(int id) {
        return trains.get(id);
    }

    @Override
    public String toString() {
        if (trains.isEmpty()) {
            return "No train exists";
        }
        StringBuilder sb = new StringBuilder();
        int f = 0;
        for (int i = 0; f < trains.size(); i++) {
            if (trains.containsKey(i)) {
                sb.append(trains.get(i).toString() + "\n");
                f++;
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

}
