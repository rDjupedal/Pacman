public abstract class AbstractFactory {
    abstract Pacman getCharacter(String type, int x, int y);

    abstract Monster getCharacter(String type, int x, int y, int number);

}
