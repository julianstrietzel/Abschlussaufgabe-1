
package julian.modelrailway.commands;

import julian.modelrailway.main.ModelRailWay;

/**
 * Ein Command, der exit akzeptiert. Er soll das Programm beenden, deshalb gibt
 * er bei isExit() true zurück.
 * 
 * @author Julian Strietzel
 */
public class Exit extends Command {

    private static final String REGEX = "(exit)";

    /**
     * Erstellt einen neuen Befehl mit exit-Pattern.
     * 
     * @param model Bezugsmodelleisenbahn
     */
    public Exit(ModelRailWay model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {

    }

    @Override
    public boolean isExit() {
        return true;
    }
}
