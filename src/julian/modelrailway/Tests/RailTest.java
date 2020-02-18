/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.Tests;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;

import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.trackmaterial.DirectionalVertex;
import julian.modelrailway.trackmaterial.Rail;
import julian.modelrailway.trackmaterial.Vertex;

public class RailTest {
Rail t ;
Rail t2;
    

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#Rail(julian.modelrailway.trackmaterial.Vertex, julian.modelrailway.trackmaterial.Vertex, int)}.
     * @throws IllegalInputException 
     */
    @Before
    public void testRail() throws IllegalInputException {
        t = new Rail(new Vertex(0,0), new Vertex(10,0), 1);
        t2 = new Rail(new Vertex(10,0), new Vertex(10,10), 1);
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getTrains()}.
     */
    @Test
    public void testGetTrains() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#setTrains(java.util.LinkedList)}.
     */
    @Test
    public void testSetTrains() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getId()}.
     */
    @Test
    public void testGetId() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getNextInDirection(julian.modelrailway.trackmaterial.DirectionalVertex)}.
     * @throws IllegalInputException 
     */
    @Test
    public void testGetNextInDirection() throws IllegalInputException {
        testSetNext();
        assertTrue(t.getNextInDirection(new DirectionalVertex(1, 0)).equals(t2));
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getNext()}.
     */
    @Test
    public void testGetNext() {
         testSetNext();
         assertTrue(t.getNext().equals(t2));
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#setNext(julian.modelrailway.trackmaterial.Rail)}.
     */
    @Test
    public void testSetNext() {
         t.setNext(t2);
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getPrevious()}.
     */
    @Test
    public void testGetPrevious() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#setPrevious(julian.modelrailway.trackmaterial.Rail)}.
     */
    @Test
    public void testSetPrevious() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#isOccupied()}.
     */
    @Test
    public void testIsOccupied() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getStart()}.
     */
    @Test
    public void testGetStart() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getEnd()}.
     */
    @Test
    public void testGetEnd() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getLength()}.
     */
    @Test
    public void testGetLength() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getDirection()}.
     */
    @Test
    public void testGetDirection() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getDirectionFrom(julian.modelrailway.trackmaterial.Vertex)}.
     */
    @Test
    public void testGetDirectionFrom() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#connectsFreeTo(julian.modelrailway.trackmaterial.Vertex)}.
     */
    @Test
    public void testConnectsFreeTo() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#connect(julian.modelrailway.trackmaterial.Rail)}.
     */
    @Test
    public void testConnect() {
         
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#isSetCorrectly(julian.modelrailway.trackmaterial.Vertex)}.
     */
    @Test
    public void testIsSetCorrectly() {
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#connectEasy(julian.modelrailway.trackmaterial.Rail, julian.modelrailway.trackmaterial.Vertex)}.
     */
    @Test
    public void testConnectEasy() {
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#createKnode(boolean)}.
     */
    @Test
    public void testCreateKnode() {
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#equals(julian.modelrailway.trackmaterial.Rail)}.
     */
    @Test
    public void testEqualsRail() {
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getKnodes()}.
     */
    @Test
    public void testGetKnodes() {
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getEndInDirection(julian.modelrailway.trackmaterial.DirectionalVertex)}.
     */
    @Test
    public void testGetEndInDirection() {
        assertTrue(t.getEndInDirection(new DirectionalVertex(1, 0)).equals(new Vertex(10,0)));
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getConnected(julian.modelrailway.trackmaterial.Rail)}.
     */
    @Test
    public void testGetConnected() {
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#toString()}.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getSetDirection()}.
     */
    @Test
    public void testGetSetDirection() {
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#contains(julian.modelrailway.trackmaterial.Vertex)}.
     */
    @Test
    public void testContains() {
        
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#isCorrectDirec(julian.modelrailway.trackmaterial.DirectionalVertex)}.
     */
    @Test
    public void testIsCorrectDirec() {
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Rail#getSpaceLeftBehind(julian.modelrailway.trackmaterial.Vertex, julian.modelrailway.trackmaterial.DirectionalVertex)}.
     */
    @Test
    public void testGetSpaceLeftBehind() {
        t.getSpaceLeftBehind(new Vertex(5,0), new DirectionalVertex(1, 0));
        assertTrue(5 == t.getSpaceLeftBehind(new Vertex(5,0), new DirectionalVertex(1, 0)));
    }

}
