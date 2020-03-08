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
        e("add track (0,0) -> (0,0)");
        e("add track (0,0) -> (2,0)");
        e("add track (2,2) -> (2,0)");
        e("add track (2,2) -> (0,2)");
        e("add track (0,2) -> (0,0)");
        e("add track (0,0) -> (-2,0)");
        e("create engine diesel T3 Emma 1 false true");
        e("create coach passenger 2 true true");
        e("create coach passenger 3 true true");
        e("create coach passenger 421000 true false");
        e("add train 1 T3-Emma");
        e("add train 1 W1");
        e("add train 1 W2");
        e("add train 1 W3");
        e("put train 1 at (0,0) in direction 1,0");
        e("exit");
        
    }

}
