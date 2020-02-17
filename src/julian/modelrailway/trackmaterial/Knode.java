/**
 * Ein Knotenpunkt im Schienensystem speichert ein und ausgehende Verbindungen und seine eigene Position.
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

public class Knode extends Vertex{

    private Rail railIn;
    private Rail railOut;
    
    /**
     * Erstellt einen neuen Knoten mit einer eingehenden Verbindung und der Position aus den Koordinaten
     * @param xcoord
     * @param ycoord
     * @param railIn eingehende Verbindung
     */
    public Knode(int xcoord, int ycoord, Rail railIn) {
        super(xcoord, ycoord);
        this.railIn = railIn;
    }
    
    /**
     * Erstellt einen neuen Knoten mit einer eingehenden Verbindung und der Position des Vektors.
     * @param pos Position als Vektor
     * @param railIn eingehende Verbindung
     */
    public Knode(Vertex pos, Rail railIn) {
        super(pos.getXcoord(), pos.getYcoord());
        this.railIn = railIn;
    }

    /**
     * 
     * @return dei eingehende Verbindung
     */
    public Rail getRailIn() {
        return railIn;
    }

    /**
     * 
     * @param railIn die neue eingehende Verbindung
     */
    public void setRailIn(Rail railIn) {
        this.railIn = railIn;
    }

    /**
     * 
     * @return die ausgehende Verindung
     */
    public Rail getRailOut() {
        return railOut;
    }

    /**
     * 
     * @param railOut neue ausgehende Verbindung
     */
    public void setRailOut(Rail railOut) {
        this.railOut = railOut;
    }

    /**
     * 
     * @return WW, ob noch ein Anschluss frei.
     */
    public boolean isFree() {
        return railOut == null || railIn == null;
    }
   
    /**
     * 
     * @param railone nciht interessierende Schiene
     * @return interessierende Schiene
     */
    public Rail getNext(Rail railone) {
        if(railone.equals(railIn)) {
            return railOut;
        } else if(railone.equals(railOut)) {
            return railIn;
        }
        return null;
    }
    
    /**
     * 
     * @param direc Ricchtung, zu der der Track passen soll
     * @return gesuchter Trakc, bei entsprechneder Richtung, oder null, falls nicht kompatibel
     */
    public Rail getTrack(DirectionalVertex direc){
        if(railIn.getEndInDirection(direc).equals(this)) {
            return railIn;
        }
       
        try {
            if(railOut.getEndInDirection(direc.getInverseDirection()).equals(this)) {
                return railIn;
            }
        } catch (NullPointerException e) {
        }
        if(!isFree()) {
            try {
                    if(railOut.getEndInDirection(direc).equals(this) || railIn.getEndInDirection(direc.getInverseDirection()).equals(this)) {
                        return railOut;
                    }
            } catch ( NullPointerException ex) {
                
            }
        }
        return null;
    }
}