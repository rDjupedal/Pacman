public class MonsterFactory extends AbstractFactory {

    @Override
    Pacman getCharacter(String type, int x, int y) {

        return null;
    }

    @Override
    Ghost getCharacter(String type, int x, int y, int number) {
        if (type.equalsIgnoreCase("MONSTER")) {
            return new BlueGhost(x, y, number);

        }
        return null;
    }

}
