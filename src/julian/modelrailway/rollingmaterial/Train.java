/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

import java.util.LinkedList;

import julian.modelrailway.Exceptions.LogicalException;

public class Train {

    private LinkedList<RollingMaterial> wagons;
    private final int id;
    private boolean inUse;

    public Train(RollingMaterial first, int id) throws LogicalException {
        wagons = new LinkedList<RollingMaterial>();
        if (first.used) {
            throw new LogicalException("wagon is already in use.");
        }
        wagons.add(first);
        first.concat(this);
        this.id = id;
        inUse = false;
    }

    public String add(RollingMaterial newWagon) throws LogicalException {
        if (!(wagons.getLast().isClutchBack() && newWagon.isClutchFront())) {
            throw new LogicalException("clutches not compatible.");
        }
        if (wagons.getLast() instanceof TrainSet || newWagon instanceof TrainSet) {
            if (!wagons.getLast().getClass().equals(newWagon.getClass())) {
                throw new LogicalException("only train-sets of the same class can be concatenated.");
            }
        }
        newWagon.concat(this);
        wagons.add(newWagon);
        return newWagon.getTypeForAdding() + " " + newWagon.getStringID() + " added to " + getID();
    }

    public int getID() {
        return id;
    }
    
    public void setInUse(boolean use) {
        this.inUse = use;
    }

    public boolean hasPower() {
        for (RollingMaterial p : wagons) {
            if (p instanceof PoweredRolling) {
                return true;
            }
        }
        return false;
    }

    public int getMaxHeight() {
        int h = 0;
        for (RollingMaterial r : wagons) {
            h = Math.max(h, r.getVisualHeight());
        }
        return h;
    }

    public String getRepre() {
        StringBuilder[] visualArray = new StringBuilder[getMaxHeight()];
        for(int i = 0; i < visualArray.length; i++) {
            visualArray[i] = new StringBuilder();
        }
        for(RollingMaterial r: wagons) {
            String[] cVisual = r.getVisual();
            cVisual.clone();
            int i; 
            for(i = 0; i < cVisual.length; i++) {
                visualArray[i].append(cVisual[i] + " ");
                
            }
            for(int f = i; f < visualArray.length; f++) {
                visualArray[f].append("                     ");
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = visualArray.length - 1; i > -1; i--) {
            
            sb.append(visualArray[i].toString().replaceAll("\\s+$", ""));
            sb.append("\n");
        }
        return sb.substring(0, sb.length() - 1);
    }
    
    public int getLength() {
        int i = 0;
        for(RollingMaterial r: wagons) {
            i += r.getLength();
        }
        return i;
    }
    
    public boolean inUse() {
        return inUse;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id + " ");
        for(RollingMaterial r: wagons) {
            sb.append(r.getWStringID() + " ");
        }
        return sb.toString().trim();
    }

}
