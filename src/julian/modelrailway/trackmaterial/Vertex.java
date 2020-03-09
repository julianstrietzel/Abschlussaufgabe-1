
package julian.modelrailway.trackmaterial;

/**
 * Ein Vektor
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class Vertex {

    /**
     * X-Koordinate des Punktes
     */
    protected final int xcoord;
    /**
     * Y-Koordinate des Punktes
     */
    protected final int ycoord;

    /**
     * Erstellt einen euene vektor mit den Koordinaten
     * 
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public Vertex(int x, int y) {
        this.xcoord = x;
        this.ycoord = y;
    }

    /**
     * 
     * @return die X-Koordinate
     */
    public int getXcoord() {
        return xcoord;
    }

    /**
     * 
     * @return die Y-Koordinate
     */
    public int getYcoord() {
        return ycoord;
    }

    /**
     * Addiert zwei Vektoren. Nur der Rückgabewert wird verändert.
     * 
     * @param summand wird auf den aktuellen aufadiert.
     * @return neuer Vektor, die Summe vom Summand und dem aktuleln
     */
    public Vertex add(Vertex summand) {
        return new Vertex(this.xcoord + summand.xcoord, this.ycoord + summand.ycoord);
    }

    /**
     * Vergleicht zwei Vektoren
     * 
     * @param vertex , zu vergleichender Vektro
     * @return ob gleiche Koordinaten
     */
    public boolean sameVertex(Vertex vertex) {
        if (vertex == null) {
            return false;
        }
        return (vertex.getXcoord() == this.xcoord) && (vertex.getYcoord() == this.ycoord);
    }

    /**
     * Gibt die normierte Richtung vom Parameter zu diesem, wenn horizontal oder
     * vertikal
     * 
     * @param vertex anderer Vektor
     * @return Richtungsvektor
     */
    DirectionalVertex normedDirection(Vertex vertex) {
        if (vertex.getXcoord() == this.xcoord) {
            return new DirectionalVertex(0, (int) Math.signum((long) vertex.ycoord - (long) this.ycoord));
        }
        if (vertex.getYcoord() == this.ycoord) {
            return new DirectionalVertex((int) Math.signum((long) vertex.xcoord - (long) this.xcoord), 0);
        }
        return new DirectionalVertex(0, 0);
    }

    @Override
    public String toString() {
        return "(" + this.xcoord + "," + this.ycoord + ")";
    }

    @Override
    public Vertex clone() {
        return new Vertex(xcoord, ycoord);
    }
}
