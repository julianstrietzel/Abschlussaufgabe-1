package julian.modelrailway.main;

import edu.kit.informatik.Terminal;
import julian.modelrailway.Commands;
import julian.modelrailway.ModelRailWay;

/**
 * Main-Klasse, die die Modelleisenbahn laufen lässt
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class Main {

    /**
     * Ruft mit dem Konsoleninput immer wieder Commands.execute auf, bis dieses den
     * isExit Befehl zurück gibt.
     * 
     * @param args werden nicht verarbeitet
     */
    public static void main(String[] args) {
        ModelRailWay model = new ModelRailWay();
        Commands c;
        do {
            c = Commands.execute(Terminal.readLine(), model);
        } while (!c.isExit());
    }

}
