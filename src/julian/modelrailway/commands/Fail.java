/**
 * Ein Befehl, der ausgeführt wird, wenn kein anderes Pattern passt.
 * MAcht nix außer "command unknown" ausgeben.
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;

public class Fail extends Command{
    
    /**
     * Erstellt einen neuen Befehl ohne Pattern.
     * @param model Bezugsmodelleisenbahn
     */
    public Fail(ModelRailWay model) {
        super(model, "");
    }
    
    @Override
    public void execute(String command) {
        Terminal.printError("command unknown.");
    }
}
