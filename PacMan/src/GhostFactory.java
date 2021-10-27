public class GhostFactory extends AbstractFactory {

    @Override
    Pacman getCharacter(String type, int x, int y) {

        return null;
    }

    @Override
    Ghost getCharacter(String type, int x, int y, String color) {
        return new Ghost(x, y, color);

    }
}
