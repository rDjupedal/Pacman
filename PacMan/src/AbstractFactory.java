
/**
 * The Abstract Factory, defining the two methods used for creating pacman and
 * the ghosts.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */
public abstract class AbstractFactory {
    abstract Pacman getCharacter(String type, int x, int y);

    abstract Ghost getCharacter(String type, int x, int y, String color);

}
