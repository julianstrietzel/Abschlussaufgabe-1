
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.exceptions.IllegalInputException;
import julian.modelrailway.exceptions.LogicalException;
import julian.modelrailway.main.ModelRailWay;

/**
 * Der Add-Track befehl fügt eine normale Schiene hinzu.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class AddTrack extends Command {
    private static final String REGEX = "add track \\(([-+]?\\d+),([-+]?\\d+)\\) -> \\(([-+]?\\d+),([-+]?\\d+)\\)";

    /**
     * Erstellt einen neuen Command, der das AddTrack Pattern akzeptiert.
     * 
     * @param model Bezugsmodelleisenbahn
     */
    public AddTrack(ModelRailWay model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {

        try {
            int sx;
            int sy;
            int ex;
            int ey;
            try {
                sx = Integer.parseInt(getMatcher(command).group(1));
                sy = Integer.parseInt(getMatcher(command).group(2));
                ex = Integer.parseInt(getMatcher(command).group(3));
                ey = Integer.parseInt(getMatcher(command).group(4));
            } catch (NumberFormatException e) {
                throw new IllegalInputException("input needs to contain valid integers");
            }

            Terminal.printLine(model.addTrack(sx, sy, ex, ey));
        } catch (IllegalInputException | LogicalException e) {
            Terminal.printError(e.getMessage());
        }

    }

}
