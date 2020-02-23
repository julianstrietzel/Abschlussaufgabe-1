/**
 * Der set switch Befehl setzt eine Weiche.
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.trackmaterial.Vertex;

public class SetSwitch extends Command {

private static final String REGEX = "set switch (\\d+) position \\(([-+]?\\d+),([-+]?\\d+)\\)"; 
    
    /**
     * Erstellt einen neuen Command, der das set switch Pattern akzeptiert.
     * @param model Bezugsmodelleisenbahn
     */
    public SetSwitch(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        try {
            int id;
            int xcoord;
            int ycoord;
            try {
                id = Integer.parseInt(getMatcher(command).group(1));
                xcoord = Integer.parseInt(getMatcher(command).group(2));
                ycoord = Integer.parseInt(getMatcher(command).group(3));
            } catch (NumberFormatException e) {
                Terminal.printError("input needs to be an Integer.");
                return;
            }
            model.setSwitch(id, new Vertex(xcoord, ycoord));
            Terminal.printLine("OK");
        } catch (IllegalInputException e) {
            Terminal.printError(e.getMessage());
        } catch (LogicalException e) {
            Terminal.printError(e.getMessage()); 
        }
    }

}
