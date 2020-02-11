/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

public class Rail {

    private final Vertex start;
    private final Vertex end;
    private Rail next;
    private Rail previous;
    private boolean occupied;
    
    public Rail(Vertex start, Vertex end) {
        this.end = end;
        this.start = start;
        this.occupied = false;
    }

    public Rail getNext() {
        return next;
    }

    public void setNext(Rail next) {
        this.next = next;
    }

    public Rail getPrevious() {
        return previous;
    }

    public void setPrevious(Rail previous) {
        this.previous = previous;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }
    
    public int getLength() {
        return Math.max(start.getXcoord() - end.getXcoord(), start.getYcoord() - end.getYcoord());
    }
    
    /**
     * Prüft, ob die Schiene zu dem Punkt eine freie Verbindung hat.
     * @param point überprüfender Punkt.
     * @return boolean, ob freie Verbindung
     */
    public boolean connectsFreeTo(Vertex point) {
        return (point.equals(end) && this.next == null ) || (point.equals(start) && this.previous == null);
    }
    
    /**
     * Verbindet zwei Schienen miteinander
     * @param rail wird verbunden
     */
    public void connect(Rail rail) {
        if(this.start.equals(rail.start)) {
            this.previous = rail;
            rail.previous = this;
        }
        if(this.end.equals(rail.end)) {
            this.next = rail;
            rail.next = this;
        }
        if(this.end.equals(rail.start)) {
            this.next = rail;
            rail.previous = this;
        }
        if(this.start.equals(rail.end)) {
            this.previous = rail;
            rail.next = this;
        }
    }
    
    /**
     * Vergleicht zwei Rails anhand ihrer Start und Endpunkte
     * @param rail
     * @return
     */
    public boolean equals(Rail rail) {
        if(!(rail instanceof Rail)) {
            //TODO check equals Switch
        }
        return (this.start.equals(rail.end) && this.end.equals(rail.start) || 
                this.start.equals(rail.start) && this.end.equals(rail.end));
    }
    

}
