/**
 * Der Add-Switch befehl fügt eine Gleis-Schiene hinzu.
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;

public class AddSwitch extends Command {

    private static final String REGEX = "add switch \\(([-+]?\\d+),([-+]?\\d+)\\) -> \\(([-+]?\\d+),([-+]?\\d+)\\),\\(([-+]?\\d+),([-+]?\\d+)\\)";

    /**
     * Erstellt einen neuen Command, der das AddTrack Pattern akzeptiert.
     * 
     * @param model Bezugsmodelleisenbahn
     */
    public AddSwitch(ModelRailWay model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command){

        try {
            int sx;
            int sy;
            int ex;
            int ey;
            int e2x;
            int e2y;
            try {
                sx = Integer.parseInt(getMatcher(command).group(1));
                sy = Integer.parseInt(getMatcher(command).group(2));
                ex = Integer.parseInt(getMatcher(command).group(3));
                ey = Integer.parseInt(getMatcher(command).group(4));
                e2x = Integer.parseInt(getMatcher(command).group(5));
                e2y = Integer.parseInt(getMatcher(command).group(6));
            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input needs to be an Integer.");
            }
            Terminal.printLine(model.addSwitch(sx, sy, ex, ey, e2x, e2y));
        } catch (IllegalInputException | LogicalException e) {
            Terminal.printError(e.getMessage());
        }

    }

}
