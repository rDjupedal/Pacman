import java.awt.*;
import java.awt.image.BufferedImage;

public class MazeBrick {
    int x, y, width, height;
    int state;
    BufferedImage image;

    public MazeBrick(BufferedImage image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        System.out.println("brick created at " + x + ", " + y );
    }

    protected void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }


}
