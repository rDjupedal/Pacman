public class characterFactory {

    protected Character getCharacter(String type, int startX, int startY) {
        if (type.equalsIgnoreCase("pacman"))
            return new Pacman(startX, startY);
        else if (type.equalsIgnoreCase("monster"))
            return new Monster(startX, startY);
        return null;
    }
}
