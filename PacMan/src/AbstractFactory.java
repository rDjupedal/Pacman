public abstract class AbstractFactory {
    abstract Pacman getCharacter(String type, int x, int y);

    abstract RedGhost getCharacter(String type, int x, int y, int number);

}
