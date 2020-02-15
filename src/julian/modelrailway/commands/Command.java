/**
 * Ein Command speichert die korrekte Eingabeform und führt den Befehl aus.
 * Es kann sich um den Exit-Befehl handeln.
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import  julian.modelrailway.ModelRailWay;

public abstract class Command {

    protected String regex = "";
    private final Pattern commandPattern;
    protected final ModelRailWay model;

    /**
     * Erstellt einen neuen Command mit einer Modelleisenbahn als Bezugsobjekt.
     * 
     * @param model Bezugsobjekt, auf dem die Befehle ausgeführt werden sollen.
     * @param regex regulärer Ausdruck, der die Eingabestruktur des Befhels festlegt.
     */
    protected Command(ModelRailWay model, String regex) {
        this.regex = regex;
        this.model = model;
        commandPattern = Pattern.compile(regex);
    }

    /**
     * Führt aus, was der Befehl machen soll.
     * @param command Eingabe, die der Befehl ausführen soll.
     */
    public abstract void execute(String command);

    /**
     * Checkt, ob die Eingabe zum Befehlspattern passt.
     * @param command String Eingabe
     * @return Wahrheitswert, ob passend.
     */
    public boolean matches(String command) {
        return getMatcher(command).matches();
    }

    /**
     * Gibt einen Matcher zum entsprechenden Regex Pattern zurück.
     * @param command Eingabe
     * @return neuer Matcher zu Klassen entsprechendem commandPattern.
     */
    protected Matcher getMatcher(String command) {
        Matcher matcher = commandPattern.matcher(command);
        matcher.matches();
        return matcher;
    }

    /**
     * Gibt zurück, ob es sich bei dem Befehl um den Exit-Befehl handelt, welcher das Programm beenden soll.
     * @return WW, ob exit
     */
    public boolean isExit() {
        return false;
    }
}

