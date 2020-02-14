/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

public class Vertex {

    protected final int xcoord;
    protected final int ycoord;
    
    public Vertex(int x, int y) {
        this.xcoord = x;
        this.ycoord = y;
    }

    public int getXcoord() {
        return xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }
    public Vertex add(Vertex summand) {
        return new Vertex(this.xcoord + summand.xcoord, this.ycoord + summand.ycoord);
    }
    
    public boolean equals(Vertex vertex) {
        return (vertex.xcoord == this.xcoord) && (vertex.ycoord == this.ycoord);
    }
    
    public DirectionalVertex normedDirection(Vertex vertex) {
        if(vertex.getXcoord() == this.xcoord) {
            return new DirectionalVertex(0, Math.abs(vertex.ycoord - this.ycoord) / (vertex.ycoord - this.ycoord));
        }
        if(vertex.getYcoord() == this.ycoord) {
            return new DirectionalVertex(Math.abs(vertex.xcoord - this.xcoord) / (vertex.xcoord - this.xcoord), 0);
        }
        return new DirectionalVertex(0,0);
    }
    
    public Vertex clone() {
        return new Vertex(xcoord, ycoord);
    }
    
    public int distance(Vertex vert) {
        return Math.min(Math.abs(vert.xcoord - this.xcoord), Math.abs(vert.ycoord - this.ycoord));
    }
    
    

}
