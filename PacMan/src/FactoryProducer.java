
/**
 * The Factory producer, producing either a pacman or ghost Factory. Part of the
 * Abstract Factory pattern.
 */
public class FactoryProducer {

    /**
     * Getter / Creator of factories. Creates either a pacman of ghost factory.
     * 
     * @param pacMan Boolean to determine what kind of factory to be created.
     * @return either a pacmanfactory or ghostfactory.
     */
    public static AbstractFactory getFactory(boolean pacMan) {

        if (pacMan) {
            return new PacmanFactory();

        } else {
            return new GhostFactory();
        }
    }

}
