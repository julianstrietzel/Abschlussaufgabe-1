/**
 * Ein Zug, konkateniert aus Rollmaterial
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

import java.util.LinkedList;

import julian.modelrailway.Exceptions.LogicalException;

public class Train {

    private LinkedList<RollingMaterial> wagons;
    private final int id;
    private boolean inUse;

    /**
     * Erstellt einen neuen Zug
     * 
     * @param first erstes Rollmaterial
     * @param id    Id des Zuges
     * @throws LogicalException, wenn first schon benutzt wird
     */
    public Train(RollingMaterial first, int id) throws LogicalException {
        wagons = new LinkedList<RollingMaterial>();
        if (first.isUsed()) {
            throw new LogicalException("wagon is already in use.");
        }
        wagons.add(first);
        first.concat(this);
        this.id = id;
        inUse = false;
    }

    /**
     * Hängt hinten ein Rollmaterial an
     * 
     * @param newWagon
     * @return <newWagon> added to <ZugID>
     * @throws LogicalException, wenn Kupplungen nicht kompatibel oder falscher
     *                           Triebzug
     */
    public String add(RollingMaterial newWagon) throws LogicalException {
        //Überprüfung nach passenden Clutches
        if (!(wagons.getLast().isClutchBack() && newWagon.isClutchFront())) {
            throw new LogicalException("clutches not compatible.");
        }
        //Überprüfung nach Train-Sets
        boolean lastTrainSet = wagons.getLast() instanceof TrainSet;
        if (lastTrainSet && newWagon instanceof TrainSet) {
            if (!wagons.getLast().getSeries().equals(newWagon.getSeries())) { 
                throw new LogicalException("only train-sets of the same class can be concatenated.");
            }
        } else if (wagons.getLast() instanceof TrainSet || newWagon instanceof TrainSet) {
            throw new LogicalException("trains-sets can bei concatenated only with other train-sets.");

        }
        newWagon.concat(this);
        wagons.add(newWagon);
        return newWagon.getTypeForAdding() + " " + newWagon.getWStringID() + " added to train " + getID();
    }

    /**
     * 
     * @return die ID des Zuges
     */
    public int getID() {
        return id;
    }

    /**
     * Setzt use auf den neuen Wert
     * 
     * @param use
     */
    public void setInUse(boolean use) {
        this.inUse = use;
    }

    /**
     * 
     * @return , ob der Zug ein motorisiertes Element hat
     */
    public boolean hasPower() {
        return wagons.getFirst().hasPower() || wagons.getLast().hasPower();

    }

    /**
     * 
     * @return die Summe der Längen aller Rollmaterialien
     */
    public int getLength() {
        int i = 0;
        for (RollingMaterial r : wagons) {
            i += r.getLength();
        }
        return i;
    }

    /**
     * 
     * @return ob Zug in Benutzung
     */
    public boolean inUse() {
        return inUse;
    }

    /**
     * 
     * @return die maximale Höhe der Elemente im Zug
     */
    public int getMaxHeight() {
        int h = 0;
        for (RollingMaterial r : wagons) {
            h = Math.max(h, r.getVisualHeight());
        }
        return h;
    }

    /**
     * 
     * @return die String Visualisierung des Zuges
     */
    public String getRepre() {
        StringBuilder[] visualArray = new StringBuilder[getMaxHeight()];
        for (int i = 0; i < visualArray.length; i++) {
            visualArray[i] = new StringBuilder();
        }
        for (RollingMaterial r : wagons) {
            String[] cVisual = r.getVisual();
            cVisual.clone();
            int i;
            for (i = 0; i < cVisual.length; i++) {
                visualArray[i].append(cVisual[i] + " ");

            }
            for (int f = i; f < visualArray.length; f++) {
                visualArray[f].append(r.getLeerzeile() + " ");
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = visualArray.length - 1; i > -1; i--) {

            sb.append(visualArray[i].toString().replaceAll("\\s+$", ""));
            sb.append("\n");
        }
        return sb.substring(0, sb.length() - 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id + " ");
        for (RollingMaterial r : wagons) {
            sb.append(r.getWStringID() + " ");
        }
        return sb.toString().trim();
    }

    /**
     * Wenn der Zug gelöscht wird, werden alle verwendeten MAterialien als unused
     * markiert.
     */
    public void markUnUsed() {
        while (!wagons.isEmpty()) {
            wagons.getFirst().unconcat();
            wagons.removeFirst();
        }
    }

}
