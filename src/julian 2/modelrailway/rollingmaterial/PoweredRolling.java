
package julian.modelrailway.rollingmaterial;

/**
 * Überklasse für alle Schienenfahrzeuge mit Motor
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public abstract class PoweredRolling extends RollingMaterial {

    private final String series;
    private final String name;

    /**
     * Erstellt ein neues Scheinenfahrzeug mit Motor.
     * 
     * @param series      Baureihe
     * @param name        Name
     * @param length      Länge
     * @param clutchFront Ob Kupplung vorne
     * @param clutchBack  Ob Kupplung hinten
     */
    public PoweredRolling(String series, String name, int length, boolean clutchFront, boolean clutchBack) {
        super(length, clutchFront, clutchBack);
        this.series = series;
        this.name = name;
    }

    /**
     * 
     * @return die String ID des Fahrzeugs <Baureihe>-<Name>
     */
    public String getID() {
        return series + "-" + name;
    }

    /**
     * 
     * @return die Baureihe des Fahrzeugs
     */
    public String getSeries() {
        return series;
    }

    /**
     * 
     * @return der Name des Fahrzeugs
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean sameRollingMat(RollingMaterial p) {
        if (p == null) {
            return false;
        }
        return this.getWStringID().contentEquals(p.getWStringID());
    }

    @Override
    public boolean hasPower() {
        return true;
    }

    @Override
    public int compareTo(RollingMaterial o) {
        if (!o.hasPower()) {
            return 0;
        }
        PoweredRolling po = (PoweredRolling) o;
        return this.getID().compareTo(po.getID());
    }

    @Override
    public String getStringID() {
        return getID();
    }

}
