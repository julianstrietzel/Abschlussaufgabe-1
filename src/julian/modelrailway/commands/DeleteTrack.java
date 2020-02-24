
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.main.ModelRailWay;

/**
 * Ein Befehl, der ein bestimmtes Track Material entfernt
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class DeleteTrack extends Command {

    private static final String REGEX = "delete track (\\d+)";

    /**
     * Erstellt einen neuen Befehl mit delete-Pattern.
     * 
     * @param model Bezugsmodelleisenbahn
     */
    public DeleteTrack(ModelRailWay model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {
        try {
            int id;
            try {
                id = Integer.parseInt(getMatcher(command).group(1));
            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input needs to be an Integer.");
            }
            Terminal.printLine(model.deleteTrack(id));
        } catch (IllegalInputException | LogicalException e) {
            Terminal.printError(e.getMessage());
        }
    }

}
