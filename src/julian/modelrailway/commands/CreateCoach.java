/**
 * Der create Egine Befehl fügt einen neuen Wagon hinzu.
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;

public class CreateCoach extends Command {

    private static final String REGEX = "create coach (passenger|freight|special) (\\d+) (true|false) (true|false)";

    /**
     * Erstellt einen neuen Command, der das create Coach Pattern akzeptiert.
     * 
     * @param model Bezugsmodelleisenbahn
     */
    public CreateCoach(ModelRailWay model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {
        try {
            Terminal.printLine(model.createCoach(getMatcher(command).group(1),
                    Integer.parseInt(getMatcher(command).group(2)), "true".contentEquals(getMatcher(command).group(3)),
                    "true".contentEquals(getMatcher(command).group(4))));
        } catch (NumberFormatException e) {
            Terminal.printError(e.getMessage());
        }
    }

}
