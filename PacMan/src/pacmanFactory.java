public class pacmanFactory extends AbstractFactory {

    protected Character getCharacter(String type, int startX, int startY) {
        if (type.equalsIgnoreCase("pacman"))
            return new Pacman(startX, startY);

        return null;
    }

    @Override
    Character getCharacter(String type, int x, int y, int number) {
        // TODO Auto-generated method stub
        return null;
    }
}
