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
        e("add track (2147483647,0) -> (0,0)");
        e("add track (0,0) -> (-2147483648,0)");
        e("list tracks");
        e("create engine electrical T3 Marie 1 false true");
        e("add train 1 T3-Marie");
        e("put train 1 at (0,0) in direction 0,-1");
        e("put train 1 at (0,0) in direction -1,0");
        e("step 1000");
        

    }

}
