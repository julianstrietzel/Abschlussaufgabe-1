
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.main.ModelRailWay;

/**
 * x * Der Add-Track befehl fügt eine normale Schiene hinzu.
 * 
 * @author Julian Strietzel
 */
public class AddTrain extends Command {
    private static final String REGEX = "add train (\\d+) (W?)((-|\\w)+)";

    /**
     * Erstellt einen neuen Command, der das AddTrack Pattern akzeptiert.
     * 
     * @param model Bezugsmodelleisenbahn
     */
    public AddTrain(ModelRailWay model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {
        try {
            Terminal.printLine(
                    model.addTrain(Integer.parseInt(getMatcher(command).group(1)), getMatcher(command).group(3)));
        } catch (NumberFormatException | LogicalException e) {
            Terminal.printError(e.getMessage());
        }
    }

}
