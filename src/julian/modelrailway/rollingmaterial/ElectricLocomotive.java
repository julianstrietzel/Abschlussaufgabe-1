/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public class ElectricLocomotive extends Locomotive {

    /**
     * @param noC
     * @param series
     * @param name
     * @param length
     */
    public ElectricLocomotive(int noC, String series, String name, int length) {
        super(noC, series, name, length);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String[] getVisual() {
        String[] visual = { "  (O)(O)      (O)(O)  ", " \\__________________/ ", "\\                    /", 
                "/   |____________|   \\", " /_| ____________ |_\\ ", "  _______________/__  ", "                 \\    ",
                "               ___    "};
        return visual;
    }

}