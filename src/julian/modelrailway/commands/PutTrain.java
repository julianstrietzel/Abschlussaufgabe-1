/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.trackmaterial.DirectionalVertex;
import julian.modelrailway.trackmaterial.Vertex;

public class PutTrain extends Command {

private static final String REGEX = "put train (\\d+) at \\((-?\\d+),(-?\\d+)\\) in direction (-?\\d+),(-?\\d+)";
    
    public PutTrain(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
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
            model.putTrain(id, new Vertex(px,py), new DirectionalVertex(dx,  dy));
            Terminal.printLine("OK");
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