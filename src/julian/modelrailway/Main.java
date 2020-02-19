package julian.modelrailway;

/**
 * Main-Klasse, die die Modelleisenbahn laufen lässt
 * @author Julian Strietzel
 */

import edu.kit.informatik.Terminal;

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

        do {
            userInterface.executeCommand(Terminal.readLine());
        } while (!userInterface.isExit());
    }

}
