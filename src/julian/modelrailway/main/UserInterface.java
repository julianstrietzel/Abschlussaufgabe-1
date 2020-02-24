package julian.modelrailway.main;

import java.util.LinkedList;
import java.util.List;

import julian.modelrailway.commands.*;

/**
 * Das UserInterface verwaltet alle Commands und sucht einen zur Eingabe
 * passenden raus.
 * 
 * @author Julian Strietzel
 */
public class UserInterface {

    private final List<Command> commands;
    private Command lastFoundCommand;
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
        lastFoundCommand = new Fail(model);
        fail = new Fail(model);
    }

    /**
     * Führt den Befehl mit dem Userinput aus
     * 
     * @param command String des Userinputs
     */
    public void executeCommand(String command) {
        getCommand(command).execute(command);
    }

    /**
     * Sucht einen zum Input passenden Befehl raus.
     * 
     * @param command Userinput
     * @return gefundener Command oder fail-Command
     */
    private Command getCommand(String command) {
        for (Command found : commands) {
            if (found.matches(command)) {
                lastFoundCommand = found;
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
        return lastFoundCommand.isExit();
    }
}
