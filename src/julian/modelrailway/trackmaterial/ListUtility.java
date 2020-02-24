
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;
import java.util.List;

/**
 * Utility-Klasse für Operationen auf einer Liste Ist gerechtfertigt, da mehrere
 * voneinander unabhängige Klassen sehr ähnliche Funktionen brauchen
 * 
 * @author Julian Strietzel
 */
public abstract class ListUtility {

    /**
     * Gibt zurück, ob ein solches Objekt in der Liste existiert: nach der
     * entsorechenden equals Funktion.
     * 
     * @param <T>    Objekttyp
     * @param list   Liste die durchsucht werden soll
     * @param object nach dem gesucht werden soll
     * @return Wahrheitswert, ob die Liste das Objekt enthält
     */
    public static <T> boolean contains(List<T> list, T object) {
        for (T obj : list) {
            if (object.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt zurück, ob ein solches Objekt in der Liste existiert: nach der
     * entsorechenden equals Funktion.
     * 
     * @param list   die durchsucht werden soll
     * @param object nachdem gesucht werden soll
     * @return WW, ob die Liste die Schiene enthält
     */
    public static boolean contains(List<Rail> list, Rail object) {
        for (Rail obj : list) {
            if (object.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt zurück, ob ein solches Objekt in der Liste existiert: nach der
     * entsorechenden equals Funktion.
     * 
     * @param list   , die diruchsucht werden soll
     * @param object , nach dem gesucht werden soll
     * @return Knoten, nach dem gesucht wurde, wenn dieser existiert, sonst null.
     */
    public static Knode contains(List<Knode> list, Vertex object) {
        for (Knode obj : list) {
            if (obj.equals(object)) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Gibt zurück, ob ein solches Objekt in der Liste existiert: nach der
     * entsorechenden equals Funktion.
     * 
     * @param list   die durchsucht werden soll
     * @param object nachdem gesucht werden soll
     * @return WW, ob die Liste den Knoten enthält
     */
    public static boolean contains(List<Vertex> list, Knode object) {
        for (Vertex obj : list) {
            if (obj.equals(object)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Löscht Duplikate aus einer Líste, ohne diese zu verändern
     * 
     * @param <T>  Typparameter für die Objekte
     * @param list zu bearbeitende Liste
     * @return neue Kopie der Liste ohne Duplikate
     */
    public static <T> List<T> copyWithoutDuplicates(List<T> list) {
        List<T> workingList = new LinkedList<T>();
        for (T ts : list) {
            if (!workingList.contains(ts)) {
                workingList.add(ts);
            }
        }
        return workingList;

    }

}
