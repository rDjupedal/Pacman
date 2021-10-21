public class characterFactory {


    protected Character getCharacter(String type, int startX, int startY) {
        if (type.equalsIgnoreCase("pacman")) return new Pacman(startX, startY);
        return null;
    }
}
