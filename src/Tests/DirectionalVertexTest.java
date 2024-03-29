/**
 * 
 * @author Julian Strietzel
 */
package Tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Test;

import julian.modelrailway.trackmaterial.DirectionalVertex;
import julian.modelrailway.trackmaterial.Vertex;

public class DirectionalVertexTest {
    static DirectionalVertex d;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        d = new DirectionalVertex(10, 0);

    }

    /**
     * Test method for
     * {@link julian.modelrailway.trackmaterial.DirectionalVertex#equalsDirection(julian.modelrailway.trackmaterial.DirectionalVertex)}.
     */
    @Test
    public void testEqualsDirection() {
        assertFalse(d.sameVertex(d.add(new Vertex(10, 0))));
        assertTrue(d.equalsDirection(new DirectionalVertex(1, 0)));
        assertFalse(d.equalsDirection(new DirectionalVertex(-1, 0)));
        assertFalse(d.equalsDirection(new DirectionalVertex(0, 1)));
    }

    /**
     * Test method for
     * {@link julian.modelrailway.trackmaterial.DirectionalVertex#compatibleDirection(julian.modelrailway.trackmaterial.DirectionalVertex)}.
     */
    @Test
    public void testCompatibleDirection() {
        assertTrue(d.compatibleDirection(new DirectionalVertex(1, 0)));
        assertTrue(d.compatibleDirection(new DirectionalVertex(-1, 0)));
        assertFalse(d.compatibleDirection(new DirectionalVertex(0, 1)));
    }

    /**
     * Test method for
     * {@link julian.modelrailway.trackmaterial.DirectionalVertex#getInverseDirection()}.
     */
    @Test
    public void testGetInverseDirection() {
        assertTrue(d.getInverseDirection().sameVertex(new DirectionalVertex(-1, 0)));
    }

}
