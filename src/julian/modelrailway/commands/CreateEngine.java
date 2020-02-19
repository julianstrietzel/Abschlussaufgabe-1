/**
 * Der create Egine Befehl fügt eine Lokomotive hinzu
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.LogicalException;

public class CreateEngine extends Command {

private static final String REGEX = "create engine (electrical|steam|diesel) (\\w+) (\\w+) (\\d+) (true|false) (true|false)"; 
    
    /**
     * Erstellt einen neuen Command, der das create Engine Pattern akzeptiert.
     * @param model Bezugsmodelleisenbahn
     */
    public CreateEngine(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        try {
            Terminal.printLine(model.createEngine(getMatcher(command).group(1), getMatcher(command).group(2),
                    getMatcher(command).group(3), Integer.parseInt(getMatcher(command).group(4)),
                    "true".contentEquals(getMatcher(command).group(5)),
                    "true".contentEquals(getMatcher(command).group(6))));
        } catch (NumberFormatException | LogicalException e) {
            Terminal.printError(e.getMessage());
        }
    }

}
