/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public class ElectricLocomotive extends Engine {

    

    public ElectricLocomotive(String series, String name, int length, boolean clutchFront, boolean clutchBack) {
        super(series, name, length, clutchFront, clutchBack);
    }

    @Override
    public String[] getVisual() {
        String[] visual = { "  (O)(O)      (O)(O)  ", " \\__________________/ ", "\\                    /", 
                "/   |____________|   \\", " /_| ____________ |_\\ ", "  _______________/__  ", "                 \\    ",
                "               ___    "};
        return visual;
    }

    @Override
    public String getType() {
        return "e";
    }
    
    @Override
    public String getTypeForAdding() {
        return "electrical engine";
    }
}