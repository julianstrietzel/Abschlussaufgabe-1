/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public class MultipleUnit extends PoweredRolling {

    /**
     * @param noC
     * @param series
     * @param name
     * @param length
     */
    public MultipleUnit(int noC, String series, String name, int length) {
        super(noC, series, name, length);
    }

    @Override
    public String[] getVisual() {
        String[] visual = { "   (O)        (O)   ", "|__________________|", "|__________________|",
                "|  |_| |_| |_| |_| |", "|  ___ ___ ___ ___ |", "_________||_________", "         ||         ",
                "         ++         " };
        return visual;
    }

}
