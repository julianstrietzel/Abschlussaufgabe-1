/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public class DieselLocomotive extends Locomotive {

    /**
     * @param noC
     * @param series
     * @param name
     * @param length
     */
    public DieselLocomotive(int noC, String series, String name, int length) {
        super(noC, series, name, length);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String[] getVisual() {
        String[] visual = { "  (O)(O)      (O)(O)  ", " \\__________________/ ", "\\                    /",
                "/   |____________|   \\", " /_| ____________ |_\\ ", "  _____________|____  " };
        return visual;
    }

}
