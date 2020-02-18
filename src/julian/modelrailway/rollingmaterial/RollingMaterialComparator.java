/**
 * Ein Comparator für RollMaterial
 * Sortiert Lexikografisch nach String ID des ELemnets
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

import java.util.Comparator;

public class RollingMaterialComparator implements Comparator <RollingMaterial>{


    /**
     * Erstellt einen neuen RollingMaterialCompaarator
     */
    public RollingMaterialComparator() {
    }

    @Override
    public int compare(RollingMaterial o1, RollingMaterial o2) {
        String one = o1.getStringID();
        String two = o2.getStringID();
        if (one == null) {
            return -1;
        }
        if(two == null) {
            return 1;
        }
        return one.compareToIgnoreCase(two);
    }

}
