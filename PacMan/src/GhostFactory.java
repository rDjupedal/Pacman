/**
 * Concrete Ghost Factory, returns a new ghost.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */

public class GhostFactory extends AbstractFactory {

    /**
     * used in PacManFActory. Returns null.
     */
    @Override
    Pacman getCharacter(String type, int x, int y) {

        return null;
    }

    /**
     * Creation of a ghost.
     * 
     * @return a new instance of Ghost class.
     */
    @Override
    Ghost getCharacter(String type, int x, int y, String color) {
        if (type.equalsIgnoreCase("ghost")) {
            return new Ghost(x, y, color);
        }
        return null;

    }
}
