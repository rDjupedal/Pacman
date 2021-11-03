import java.awt.*;
import java.awt.image.BufferedImage;

public class MazeBrick {
    int x, y, width, height;
    String type;
    BufferedImage image;

    protected MazeBrick() {}

    public void setupBrick(String type, BufferedImage image, int x, int y, int width, int height) {
        this.image = image;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void changeBrick(String newType, BufferedImage newImage) {
        this.type = newType;
        this.image = newImage;
    }

    protected void changeType(String newType) {

        this.type = newType;
    }

    protected Rectangle getBrickRectangle() {
        return new Rectangle(x, y, width, height);
    }

    protected String getType() {
        return type;
    }

    protected boolean isWall() {
        return (type == "wall");
    }

    protected void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

}
