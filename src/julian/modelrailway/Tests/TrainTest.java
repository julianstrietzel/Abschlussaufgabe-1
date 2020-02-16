/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.Tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.rollingmaterial.DieselLocomotive;
import julian.modelrailway.rollingmaterial.FreightCar;
import julian.modelrailway.rollingmaterial.PassengerWagon;
import julian.modelrailway.rollingmaterial.RollingMaterial;
import julian.modelrailway.rollingmaterial.Train;

public class TrainTest {
static Train t;
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        t = new Train(new DieselLocomotive("123", "Emma", 10, false, true), 1);
        t.add(new PassengerWagon(10,true,true, 10));
        t.add(new FreightCar(10, true, true, 3));
    }

    

    /**
     * Test method for {@link julian.modelrailway.rollingmaterial.Train#add(julian.modelrailway.rollingmaterial.RollingMaterial)}.
     * @throws LogicalException 
     */
    @Test
    public void testAdd() throws LogicalException {
        t.add(new PassengerWagon(10,true,true, 10));
    }

    /**
     * Test method for {@link julian.modelrailway.rollingmaterial.Train#getID()}.
     */
    @Test
    public void testGetID() {
        System.out.println(t.getID());
    }

    @Test
    public void testMAxHeight() {
        System.out.println(t.getMaxHeight());
    }
    /**
     * Test method for {@link julian.modelrailway.rollingmaterial.Train#hasPower()}.
     */
    @Test
    public void testHasPower() {
        assertTrue(t.hasPower());
    }

    /**
     * Test method for {@link julian.modelrailway.rollingmaterial.Train#getRepre()}.
     */
    @Test
    public void testGetRepre() {
        System.out.println(t.getRepre());
    }

}
