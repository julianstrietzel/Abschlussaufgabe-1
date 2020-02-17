/**
 * Überklasse für alle Schienenfahrzeuge mit Motor
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public abstract class PoweredRolling extends RollingMaterial {

    private final String series;
    private final String name;
    
    /**
     * Erstellt ein neues Scheinenfahrzeug mit Motor.
     * @param series    Baureihe
     * @param name  Name
     * @param length    Länge
    * @param clutchFront   Ob Kupplung vorne
     * @param clutchBack    Ob Kupplung hinten
     */
    public  PoweredRolling(String series, String name, int length, boolean clutchFront, boolean clutchBack) {
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
    public boolean equals(RollingMaterial p) {
        if(p instanceof PoweredRolling) {
            PoweredRolling pr = (PoweredRolling) p;
            return pr.getID().contentEquals(getID());
        }
        return false;
    }
    
    @Override
    public int compareTo(RollingMaterial o) {
        if(!(o instanceof PoweredRolling)) {
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
