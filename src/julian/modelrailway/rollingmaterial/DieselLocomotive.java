/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public class DieselLocomotive extends Engine {

    

    public DieselLocomotive(String series, String name, int length, boolean clutchFront, boolean clutchBack) {
        super(series, name, length, clutchFront, clutchBack);
    }

    @Override
    public String[] getVisual() {
        String[] visual = { "  (O)(O)      (O)(O)  ", " \\__________________/ ", "\\                    /",
                "/   |____________|   \\", " /_| ____________ |_\\ ", "  _____________|____  " };
        return visual;
    }
    
    @Override
    public String getType() {
        return "d";
    }
    
    @Override
    public String getTypeForAdding() {
        return "diesel engine";
    }

}
