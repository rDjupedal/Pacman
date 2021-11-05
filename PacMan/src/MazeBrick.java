import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class for bricks of the maze
 * @author Rasmus Djupedal, Tobias Liljeblad
 */
public class MazeBrick {
    int x, y, width, height;
    String type;
    BufferedImage image;

    /**
     * Constructor empty in order to easily create and supply objects from the Object Pool
     */
    protected MazeBrick() {}

    /**
     * Sets up the brick
     * @param type type of brick
     * @param image image for the brick
     * @param x X-coord of the brick
     * @param y Y-coord of the brick
     * @param width width of the brick
     * @param height height of the brick
     */
    public void setupBrick(String type, BufferedImage image, int x, int y, int width, int height) {
        this.image = image;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Changes the type and image of a brick
     * @param newType new type
     * @param newImage new image
     */
    protected void changeBrick(String newType, BufferedImage newImage) {
        this.type = newType;
        this.image = newImage;
    }

    /**
     * Changes the type of a brick
     * @param newType new type
     */
    protected void changeBrick(String newType) {
        this.type = newType;
    }

    /**
     * Returns a rectangle surrounding the brick
     * @return a rectangle
     */
    protected Rectangle getBrickRectangle() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Returns the type of brick
     * @return type of brick
     */
    protected String getType() {
        return type;
    }

    /**
     * Returns whether the brick is a wall
     * @return if brick is wall
     */
    protected boolean isWall() {
        return (type == "wall");
    }

    /**
     * Draws the brick
     * @param g Graphics object
     */
    protected void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

}
