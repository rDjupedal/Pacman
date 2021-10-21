public class characterFactory {


    protected AliveComponent getCharacter(String type, int startX, int startY) {
        if (type == "pacman") return new Pac(startX, startY);
        return null;
    }
}
