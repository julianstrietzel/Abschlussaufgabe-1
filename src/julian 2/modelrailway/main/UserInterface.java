package julian.modelrailway.main;

import java.util.LinkedList;
import java.util.List;

import julian.modelrailway.commands.*;

/**
 * Das UserInterface verwaltet alle Commands und sucht einen zur Eingabe
 * passenden raus.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class UserInterface {

    private final List<Command> commands;
    private Command lastCommand;
    private final Command fail;

    /**
     * Erstellt ein Interface mit allen Befehlen
     * 
     * @param model Bezugseisenbahn
     */
    public UserInterface(ModelRailWay model) {
        commands = new LinkedList<Command>();
        commands.add(new AddTrack(model));
        commands.add(new AddSwitch(model));
        commands.add(new DeleteTrack(model));
        commands.add(new ListTracks(model));
        commands.add(new SetSwitch(model));
        commands.add(new CreateEngine(model));
        commands.add(new CreateCoach(model));
        commands.add(new CreateTrainSet(model));
        commands.add(new ListRollingStock(model));
        commands.add(new DeleteRollingStock(model));
        commands.add(new AddTrain(model));
        commands.add(new DeleteTrain(model));
        commands.add(new ListTrains(model));
        commands.add(new ShowTrain(model));
        commands.add(new PutTrain(model));
        commands.add(new Step(model));
        commands.add(new Exit(model));
        lastCommand = new Fail(model);
        fail = lastCommand;
    }

    /**
     * Führt den Befehl mit dem Userinput aus
     * 
     * @param input String des Userinputs
     */
    public void executeCommand(String input) {
        findCommand(input).execute(input);
    }

    /**
     * Sucht einen zum Input passenden Befehl raus.
     * 
     * @param input Userinput
     * @return gefundener Command oder fail-Command
     */
    private Command findCommand(String input) {
        for (Command found : commands) {
            if (found.matches(input)) {
                lastCommand = found;
                return found;
            }
        }
        return fail;
    }

    /**
     * Checkt, ob der letzte Befehl exit war
     * 
     * @return isExit von letztem befehl
     */
    public boolean isExit() {
        return lastCommand.isExit();
    }
}
