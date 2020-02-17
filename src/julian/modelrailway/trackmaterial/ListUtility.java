/**
 * Utility-Klasse für Operationen auf einer Liste
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;



public abstract class ListUtility {

    /**
     * Gibt zurück, ob ein solches Objekt in der Liste existiert: nach der entsorechenden equals Funktion.
     * @param <T> Objekttyp
     * @param list  Liste die durchsucht werden soll
     * @param object nach dem gesucht werden soll
     * @return
     */
    public static <T> boolean contains(LinkedList<T> list, T object) {
        for (T obj : list) {
            if (object.equals(obj)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gibt zurück, ob ein solches Objekt in der Liste existiert: nach der entsorechenden equals Funktion.
     * @param list
     * @param object
     * @return
     */
    public static boolean contains(LinkedList<Rail> list, Rail object) {
        for (Rail obj : list) {
            if (object.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt zurück, ob ein solches Objekt in der Liste existiert: nach der entsorechenden equals Funktion.
     * @param list
     * @param object
     * @return
     */
    public static boolean contains(LinkedList<Knode> list, Vertex object) {
        for (Knode obj : list) {
            if (obj.equals(object)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt zurück, ob ein solches Objekt in der Liste existiert: nach der entsorechenden equals Funktion.
     * @param list
     * @param object
     * @return
     */
    public static boolean contains(LinkedList<Vertex> list, Knode object) {
        for (Vertex obj : list) {
            if (obj.equals(object)) {
                return true;
            }
        }
        return false;
    }
    
//    /**
//     * Guckt, ob eine Liste aus RollingMaterial einen Zug mti der ID beinhaltet
//     * @param list zu überpfüfendes ELemnt von Iterable
//     * @param id zu suchende String ID
//     * @return RollingMAterial, falls dieses existiert.
//     */
//    public static RollingMaterial exists(Iterable<RollingMaterial> list, String id) {
//        for(RollingMaterial p: list) {
//            if(id.contentEquals(p.getStringID())) {
//                return p;
//            }
//        }
//        return null;
//    }
//    
////    public static PoweredRolling exists(Iterable<PoweredRolling> list, String id) {
////        for(PoweredRolling p: list) {
////            if(id.contentEquals(p.getID())) {
////                return p;
////            }
////        }
////        return null;
////    }
////    
////    public static Coach exists(Iterable<Coach> list, int id) {
////        for(Coach c: list) {
////            if(id == c.getID()) {
////                return c;
////            }
////        }
////        return null;
////    }

    /**
     * Löscht Duplikate aus einer Líste, ohne diese zu verändern
     * @param <T>   Typparameter für die Objekte
     * @param list  zu bearbeitende Liste
     * @return neue Kopie der Liste ohne Duplikate
     */
    public static <T> LinkedList<T> deleteDuplicates(LinkedList<T> list) {
        LinkedList<T> workingList = new LinkedList<T>();
        for(T ts: list) {
            if(!workingList.contains(ts)) {
                workingList.add(ts);
            }
        }
        return workingList;
        
    }

}
