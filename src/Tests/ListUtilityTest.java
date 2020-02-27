/**
 * 
 * @author Julian Strietzel
 */
package Tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.BeforeClass;
import org.junit.Test;

import julian.modelrailway.exceptions.IllegalInputException;
import julian.modelrailway.trackmaterial.ListUtility;
import julian.modelrailway.trackmaterial.Rail;
import julian.modelrailway.trackmaterial.Vertex;

public class ListUtilityTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

//    /**
//     * Test method for {@link julian.modelrailway.trackmaterial.ListUtility#contains(java.util.LinkedList, java.lang.Object)}.
//     * @throws IllegalInputException 
//     */
//    @Test
//    public void testContainsLinkedListOfTT() throws IllegalInputException {
//        LinkedList<Rail> test = new LinkedList<Rail>();
//        test.add(new Rail(new Vertex(0,0), new Vertex(0,10),0));
//        assertTrue(test.getFirst().sameRail(new Rail(new Vertex(0,0), new Vertex(0,10),0)));
//        assertTrue(ListUtility.contains(test, new Rail(new Vertex(0,0), new Vertex(0,10),0)));
//     }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.ListUtility#contains(java.util.LinkedList, julian.modelrailway.trackmaterial.Vertex)}.
     */
    @Test
    public void testContainsLinkedListOfKnodeVertex() {
     }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.ListUtility#contains(java.util.LinkedList, julian.modelrailway.trackmaterial.Knode)}.
     */
    @Test
    public void testContainsLinkedListOfVertexKnode() {
     }

    /**
     * Test method for {@link julian.modelrailway.trackmaterial.ListUtility#deleteDuplicates(java.util.LinkedList)}.
     */
    @Test
    public void testDeleteDuplicates() {

    }

}
