public class FactoryProducer {

    public static AbstractFactory getFactory(boolean pacMan) {

        if (pacMan) {
            return new PacmanFactory();

        } else {
            return new GhostFactory();
        }
    }

}
