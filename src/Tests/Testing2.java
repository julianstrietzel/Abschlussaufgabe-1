package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.informatik.Terminal;
import julian.modelrailway.main.Commands;
import julian.modelrailway.main.ModelRailWay;

/**
 * @author Julian Strietzel
 * @version 08.03.2020
 */
public class Testing2 {

    ModelRailWay m;
    Commands ui;

    /**
     * Resets before every Test
     */
    @Before
    public void start() {
        Terminal.silent = false;
        // Bei Bedarf hier die ausgabe wieder einschalten!
        reset();
    }

    private void reset() {
        m = new ModelRailWay();

    }

    @After
    public void TerminalBackToNormal() {
        Terminal.silent = false;
    }

    @SuppressWarnings("static-access")
    private void e(String command) {
        ui.execute(command, m);
    }

    private void c(String checkInput) {
        assertTrue(Terminal.buffer.contentEquals(checkInput));
    }

    private void iE() {
        assertTrue(Terminal.buffer.contains("Error, "));
    }

    @Test
    public void bigInt() {
        e("add track (0,0) -> (10,0)");
        e("add switch (-5,0) -> (0,0),(-5,5)");
        e("set switch 2 position (-5,5)");
        e("create engine diesel T1 Alpha 1 true true");
        e("add train 1 T1-Alpha");
//        e("put train 1 at (5,0) in direction 1,0");
//        e("step -5");
//        e("create train-set ICE 1 2 true true");
//        e("add train 2 ICE-1");
//        e("create train-set ICE 2 2 true true");
//        e("add train 3 ICE-2");
//        e("create train-set ICE 2E 2 true true");
//        e("add train 4 ICE-2E");
//        e("create train-set ICE 3 2 true true");
//        e("add train 5 ICE-3");
//        e("add track (10,0) -> (20,0)");
//        e("add track (20,0) -> (30,0)");
//        e("add track (30,0) -> (40,0)");
//        e("add track (40,0) -> (50,0)");
//        e("put train 1 at (9,0) in direction 1,0");
//        e("put train 2 at (41,0) in direction -1,0");
//        e("put train 3 at (25,0) in direction 1,0");
//        e("put train 4 at (19,0) in direction 1,0");
//        e("put train 5 at (31,0) in direction -1,0");
//        e("step 2");
//        e("add switch (50,0) -> (55,0),(50,5)");
//        e("put train 1 at (51,0) in direction 1,0");
//        e("delete track 7");
//        
//        e("list tracks");
        e("put train 1 at (0,0) in direction 0,-1");
        e("exit");

    }

}
