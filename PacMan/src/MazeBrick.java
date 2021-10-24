import java.awt.*;
import java.awt.image.BufferedImage;

public class MazeBrick {
    int x, y, width, height;
    int state;
    String type;
    BufferedImage image;

    public MazeBrick(String type, BufferedImage image, int x, int y, int width, int height) {
        this.image = image;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        // DEBUg
        // System.out.println("brick created at " + x + ", " + y );
    }

    protected Rectangle getBrickRectangle() {
        return new Rectangle(x, y, width, height);
    }

    protected String getType() { return type; }

    protected boolean isWall() { return (type == "wall"); }

    protected void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

}
