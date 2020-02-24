
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.main.ModelRailWay;
import julian.modelrailway.trackmaterial.DirectionalVertex;
import julian.modelrailway.trackmaterial.Vertex;

/**
 * Setzt einene neuen Zug auf die Schienen.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class PutTrain extends Command {

    private static final String REGEX 
        = "put train (\\d+) at \\(([-+]?\\d+),([-+]?\\d+)\\) in direction ([+-]?\\d+),([+-]?\\d+)";

    /**
     * Erstellt einen neuen Command mit put train PAttern.
     * 
     * @param model Bezugsmodelleisenbahn
     */
    public PutTrain(ModelRailWay model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {
        try {
            int id;
            int px;
            int py;
            int dx;
            int dy;
            try {
                id = Integer.parseInt(getMatcher(command).group(1));
                px = Integer.parseInt(getMatcher(command).group(2));
                py = Integer.parseInt(getMatcher(command).group(3));
                dx = Integer.parseInt(getMatcher(command).group(4));
                dy = Integer.parseInt(getMatcher(command).group(5));

            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input is not a valid number.");
            }
            model.putTrain(id, new Vertex(px, py), new DirectionalVertex(dx, dy));
            Terminal.printLine("OK");
        } catch (LogicalException ea) {
            Terminal.printError(ea.getMessage());
        } catch (IllegalInputException e) {
            Terminal.printError(e.getMessage());
        }
    }
}
