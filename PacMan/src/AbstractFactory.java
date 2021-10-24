public abstract class AbstractFactory {
    abstract Pacman getCharacter(String type, int x, int y);

    abstract Ghost getCharacter(String type, int x, int y, int number);

}
