/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public class SteamLocomotive extends Locomotive {

    /**
     * @param noC
     * @param series
     * @param name
     * @param length
     */
    public SteamLocomotive(int noC, String series, String name, int length) {
        super(noC, series, name, length);
    }

    @Override
    public String[] getVisual() {
        String[] visual = { "//// \\_/      \\_/   ", " _|--/~\\------/~\\-+ ", "  + ========  +-+ | ",
                "   /---------|| | |", "     ||      |+-+ | ", "     ++      +------" };
        return visual;

    }

}
