/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public class SteamLocomotive extends Engine {

    public SteamLocomotive(String series, String name, int length, boolean clutchFront, boolean clutchBack) {
        super(series, name, length, clutchFront, clutchBack);
    }

    @Override
    public String[] getVisual() {
        String[] visual = { "//// \\_/      \\_/   ", " _|--/~\\------/~\\-+ ", "  + ========  +-+ | ",
                "   /---------|| | |", "     ||      |+-+ | ", "     ++      +------" };
        return visual;

    }
    
    @Override
    public String getType() {
        return "s";
    }
    
    @Override
    public String getTypeForAdding() {
        return "steam engine";
    }

}
