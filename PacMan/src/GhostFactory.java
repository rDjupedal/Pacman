public class GhostFactory extends AbstractFactory {

    @Override
    Pacman getCharacter(String type, int x, int y) {

        return null;
    }

    @Override
    Ghost getCharacter(String type, int x, int y, String color) {
        if (color.equalsIgnoreCase("blue")) {
            return new BlueGhost(x, y);

        } else if (color.equalsIgnoreCase("red")) {
            return new RedGhost(x, y);

        } else if (color.equalsIgnoreCase("yellow")) {
            return new YellowGhost(x, y);

        }

        else if (color.equalsIgnoreCase("pink")) {
            return new PinkGhost(x, y);
        }
        return null;
    }

}
