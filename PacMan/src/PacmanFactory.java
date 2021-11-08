
/**
 * Concrete class of Abstract Factory. Used for creating Pacman
 * 
 * @author Tobias Liljeblad & Rasumus Djupedal
 */
public class PacmanFactory extends AbstractFactory {

    /**
     * Creation of Pacman.
     * 
     * @return Instance of Pacman
     */
    protected Pacman getCharacter(String type, int startX, int startY) {
        if (type.equalsIgnoreCase("pacman"))
            return new Pacman(startX, startY);

        return null;
    }

    /**
     * Used in GhostFactory, returns null if used in PacMAn factory.
     * 
     * @return null
     */
    @Override
    Ghost getCharacter(String type, int x, int y, String color) {
        return null;
    }
}
