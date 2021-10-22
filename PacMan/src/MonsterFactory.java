public class MonsterFactory extends AbstractFactory {

    @Override
    Character getCharacter(String type, int x, int y) {

        return null;
    }

    @Override
    Character getCharacter(String type, int x, int y, int number) {
        if (type.equalsIgnoreCase("MONSTER")) {
            return new Monster(x, y, number);

        }
        return null;
    }

}
