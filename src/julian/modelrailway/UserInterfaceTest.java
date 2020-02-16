/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.informatik.Terminal;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.rollingmaterial.DieselLocomotive;
import julian.modelrailway.rollingmaterial.Engine;
import julian.modelrailway.rollingmaterial.PoweredRolling;
import julian.modelrailway.trackmaterial.DirectionalVertex;
import julian.modelrailway.trackmaterial.ListUtility;
import julian.modelrailway.trackmaterial.Rail;
import julian.modelrailway.trackmaterial.Vertex;

public class UserInterfaceTest {
    ModelRailWay model = new ModelRailWay();
    UserInterface ui = new UserInterface(model);

    @Test
    public void testAddTrack() {
        ui.executeCommand("add track (0,0) -> (10,0)");
        ui.executeCommand("add track (0,0) -> (10,0)");
        ui.executeCommand("add track (10,0) -> (10,0)");
        ui.executeCommand("add track (20,0) -> (10,0)");
        ui.executeCommand("add track (2,0) -> (13,0)");
        ui.executeCommand("add track (20,0) -> (20,3)");
        ui.executeCommand("add track (20,3) -> (0,3)");
        ui.executeCommand("add track (0,3) -> (0,0)");
        ui.executeCommand("list tracks");
        ui.executeCommand("step 1");
        ui.executeCommand("delete track 3");
        ui.executeCommand("list tracks");
//        model.putTestTrain(0, new Vertex(5, 0), new DirectionalVertex(1, 0));
        ui.executeCommand("step 1");
        PoweredRolling p = new DieselLocomotive("10", "EMma", 3, false, true);
        assertTrue(p instanceof Engine);
    }

}
