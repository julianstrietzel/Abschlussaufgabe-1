/**
 * Ein Vektor, der einen normierte Richtung repräsentiert.
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

public class DirectionalVertex extends Vertex {

    /**
     * Erstellt einen neuen Richtungvektor
     * 
     * @param xcoord 
     * @param ycoord
     */
    public DirectionalVertex(int xcoord, int ycoord) {
        super((int) Math.signum(xcoord), (int) Math.signum(ycoord));
    }

    /**
     * Vergleicht zwei Richtungsvektoren
     * @param vertex anderer Richtungvektor
     * @return WW, ob die gleiche Richtung
     */
    public boolean equalsDirection(DirectionalVertex vertex) {
        return (vertex.getXcoord() == this.getXcoord()) && (vertex.getYcoord() == this.getYcoord());
    }

    /**
     * Vergleicht, ob der Parameter eine vielfaches des Richtungvektors ist.
     * @param vertex zu vergleichen
     * @return ob kompatible Richtung
     */
    public boolean compatibleDirection(Vertex vertex) {
        return (Math.abs(Math.signum(vertex.getXcoord())) == Math.abs(Math.signum(this.getXcoord()))
                && Math.abs(Math.signum(vertex.getYcoord())) == Math.abs(Math.signum(this.getYcoord())));
    }
    
    /**
     * Gibt die entgegengerichtete Richtung des Vektors zurück.
     * @return
     */
    public DirectionalVertex getInverseDirection() {
        return new DirectionalVertex(this.xcoord * (-1), this.ycoord * (-1));
    }
}
