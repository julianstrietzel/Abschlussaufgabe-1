package julian.modelrailway;
/**
 * 
 * @author Julian Strietzel
 */


import edu.kit.informatik.Terminal;
import julian.modelrailway.commands.*;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ModelRailWay model = new ModelRailWay();
        UserInterface userInterface = new UserInterface(model);

        do {
            userInterface.executeCommand(Terminal.readLine());
        } while (!userInterface.isExit());
    }

}
