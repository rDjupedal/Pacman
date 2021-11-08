public class PacmanFactory extends AbstractFactory {

    protected Pacman getCharacter(String type, int startX, int startY) {
        if (type.equalsIgnoreCase("pacman"))
            return new Pacman(startX, startY);

        return null;
    }

    @Override
    Ghost getCharacter(String type, int x, int y, String color) {
        // TODO Auto-generated method stub
        return null;
    }
}