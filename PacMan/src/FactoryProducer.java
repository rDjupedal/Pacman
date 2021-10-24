public class FactoryProducer {

    public static AbstractFactory getFactory(boolean pacMan) {

        if (pacMan) {
            return new pacmanFactory();

        } else {
            return new GhostFactory();
        }
    }

}
