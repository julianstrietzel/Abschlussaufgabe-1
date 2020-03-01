package julian.modelrailway.main;

import edu.kit.informatik.Terminal;
import julian.modelrailway.main.ModelRailWay;

/**
 * Main-Klasse, die die Modelleisenbahn laufen lässt
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class Main {

    /**
     * Ruft mit dem Konsoleninput immer wieder das Userinterface auf, bis dieses den
     * isExit Befehl zurück gibt.
     * 
     * @param args werden nicht verarbeitet
     */
    public static void main(String[] args) {
        ModelRailWay model = new ModelRailWay();
        UserInterface userInterface = new UserInterface(model);
        while (!userInterface.isExit()) {
            userInterface.executeCommand(Terminal.readLine());
        }
    }

}
