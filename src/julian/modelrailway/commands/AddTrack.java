/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;

public class AddTrack extends Command {

private static final String REGEX = "add track \\((-?\\d+),(-?\\d+)\\) -> \\((-?\\d+),(-?\\d+)\\)"; //TODO Regex für Befehl
    
    public AddTrack(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
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
            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input needs to be an Integer.");
            }
            Terminal.printLine(model.addTrack(sx, sy, ex, ey));
        }   catch (LogicalException ea) {
            Terminal.printError(ea.getMessage());
        } catch (IllegalInputException e) {
            Terminal.printError(e.getMessage());
        }
    }
    
    @Override
    public boolean isExit() {
        return false;
    }

}
