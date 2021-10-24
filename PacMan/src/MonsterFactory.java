public class MonsterFactory extends AbstractFactory {

    @Override
    Pacman getCharacter(String type, int x, int y) {

        return null;
    }

    @Override
    RedGhost getCharacter(String type, int x, int y, int number) {
        if (type.equalsIgnoreCase("MONSTER")) {
            return new RedGhost(x, y, number);

        }
        return null;
    }

}
