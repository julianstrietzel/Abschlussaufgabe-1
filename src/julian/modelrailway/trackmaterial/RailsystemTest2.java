/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

import static org.junit.Assert.*;

import org.junit.*;

import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.rollingmaterial.SetTrain;

public class RailsystemTest2 {

    Railsystem rs;

    @Before
    public void before() throws IllegalInputException, LogicalException {
        rs = new Railsystem();
        Vertex one = new Vertex(0, 0);
        Vertex two = new Vertex(10, 0);
        Vertex three = new Vertex(10, 20);
        Vertex four = new Vertex(0, 20);
        rs.addRail(one, two);
        rs.addRail(two, three);
        rs.addRail(three, four);
        rs.addRail(four, one);
    }

    /**
     * Test method for
     * {@link julian.modelrailway.trackmaterial.Railsystem#addTrain(julian.modelrailway.rollingmaterial.SetTrain)}.
     * 
     * @throws LogicalException
     */
    @Test (expected = LogicalException.class)
    public void testAddTrain() throws LogicalException {
        SetTrain one = new SetTrain(1, new DirectionalVertex(1, 0), new Vertex(5, 0), 2);
        
        
        assertTrue(rs.addTrain(one).contentEquals("1"));
        assertFalse(rs.addTrain(new SetTrain(2, new DirectionalVertex(-1, 0), new Vertex(6, 0), 2)).contentEquals("2"));
        assertTrue(rs.addTrain(new SetTrain(2, new DirectionalVertex(-1, 0), new Vertex(5, 20), 2)).contentEquals("2"));
        
    }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.Railsystem#move(boolean)}.
     * @throws LogicalException 
     */
    @Test
    public void testMove() throws LogicalException {
        SetTrain one = new SetTrain(1, new DirectionalVertex(1, 0), new Vertex(5, 0), 2);
        assertTrue(rs.addTrain(one).contentEquals("1"));

    }
//
//    /**
//     * Test method for {@link julian.modelrailway.trackmaterial.Railsystem#checkCollision()}.
//     */
//    @Test
//    public void testCheckCollision() {
//        fail("Not yet implemented");
//    }
//
//    /**
//     * Test method for {@link julian.modelrailway.trackmaterial.Railsystem#isAllSet()}.
//     */
//    @Test
//    public void testIsAllSet() {
//        fail("Not yet implemented");
//    }
//
//    /**
//     * Test method for {@link julian.modelrailway.trackmaterial.Railsystem#findTrack(julian.modelrailway.trackmaterial.Vertex, julian.modelrailway.trackmaterial.DirectionalVertex)}.
//     */
//    @Test
//    public void testFindTrack() {
//        fail("Not yet implemented");
//    }
//
}
