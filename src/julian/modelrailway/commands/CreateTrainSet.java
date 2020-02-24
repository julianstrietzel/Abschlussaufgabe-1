
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.main.ModelRailWay;

/**
 * Der create Egine Befehl fügt einen neuen Triebwagen hinzu.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class CreateTrainSet extends Command {

    private static final String REGEX = "create train-set (\\w+) (\\w+) (\\d+) (true|false) (true|false)";

    /**
     * Erstellt einen neuen Command, der das create Train-Set Pattern akzeptiert.
     * 
     * @param model Bezugsmodelleisenbahn
     */
    public CreateTrainSet(ModelRailWay model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {
        try {
            Terminal.printLine(model.createTrainSet(getMatcher(command).group(1), getMatcher(command).group(2),
                    Integer.parseInt(getMatcher(command).group(3)), "true".contentEquals(getMatcher(command).group(4)),
                    "true".contentEquals(getMatcher(command).group(5))));
        } catch (NumberFormatException | LogicalException e) {
            Terminal.printError(e.getMessage());
        }
    }

}
