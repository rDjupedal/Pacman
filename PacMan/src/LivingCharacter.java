import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Superclass for Pacman and Ghost
 * @author Rasmus Djupedal, Tobias Liljeblad
 */
abstract class LivingCharacter extends JComponent {
    protected int x, y;
    protected boolean animation;
    protected boolean isMoving = false;

    // (.) means no current direction
    char direction = '.';

    final int cSize = GameEngine.INSTANCE.getGridSize().width;
    final int moveDistance = 2;
    ArrayList<BufferedImage> charImages = new ArrayList<>();

    /**
     * Template method pattern to make a move
     */
    protected final void doMove() {
        setDirection();
        moveCharacter();
    }

    /**
     * Sets an allowed direction of the character
     */
    protected abstract void setDirection();

    /**
     * Physically moves the character
     */
    protected abstract void moveCharacter();

    /**
     * Hook method, not mandatory for baseclass to implement if it doesn't want to
     * listen to key presses
     * 
     * @param e KeyEvent
     */
    protected void keyPressed(KeyEvent e) {
    }

    /**
     * Draws the character
     * 
     * @param g Graphics object
     */
    abstract public void draw(Graphics g);

    /**
     * Checks if X- and Y- coordinates are inside the playable area
     * @return whether current position is inside playable area
     */
    protected boolean withinBoundary() {
        //if ((y >= cSize && y <= Maze.INSTANCE.height - cSize) && (x >= 0 && x <= Maze.INSTANCE.width - cSize)) {
        if ((y >= 0 && y <= Maze.INSTANCE.height - cSize) && (x >= 0 && x <= Maze.INSTANCE.width - cSize)) {
            return true;
        }

        return false;
    }

    /**
     * Checks if character is in the center of a vertical grid
     * @return whether in center
     */
    protected boolean inVerticalGrid() {
        return (x % Maze.INSTANCE.gridWidth <= 1);
    }

    /**
     * Checks if the character is in the center of horizontal grid
     * @return whether in center
     */
    protected boolean inHorizontalGrid() {
        return (y % Maze.INSTANCE.gridHeight <= 1);
    }

    /**
     * Checks if possible to go up
     * @return if possible
     */
    protected boolean goUp() {
        return (!Maze.INSTANCE.getBrick(x + cSize / 2, y - moveDistance).isWall());
    }

    /**
     * Checks if possible to down
     * @return if possible
     */
    protected boolean goDown() {
        return (!Maze.INSTANCE.getBrick(x + cSize / 2, y + cSize).isWall());
    }

    /**
     * Checks if possible to go left
     * @return if possible
     */
    protected boolean goLeft() {
        return (!Maze.INSTANCE.getBrick(x - moveDistance, y + cSize / 2).isWall());
    }

    /**
     * Checks if possible to go right
     * @return if possible
     */
    protected boolean goRight() {
        return (!Maze.INSTANCE.getBrick(x + cSize, y + cSize / 2).isWall());
    }

    /**
     * Returns a surrounding rectangle of the character in order to determine repaint area
     * @return rectangle surrounding character
     */
    public Rectangle getRectangle() {
        // If the character is just crossing through the shortcut
        // return a wider rectangle covering both exists
        if ((x < cSize && direction == 'R') || x + cSize > Maze.INSTANCE.width && direction == 'L') {
            return new Rectangle(0, y - moveDistance,
                    Maze.INSTANCE.width - x, cSize + 2 * moveDistance);
        } else {
            return new Rectangle(x - moveDistance, y - moveDistance,
                    cSize + 2 * moveDistance, cSize + 2 * moveDistance);
        }
    }

    /**
     * Returns a rectangle for collision check
     * @return rectangle only covering the character
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(x + cSize / 4, y + cSize / 4, cSize /2, cSize / 2);
    }

    /**
     * Returns X-coordinate of character
     * @return X- coordinate
     */
    protected int get_X() {
        return x;
    }

    /**
     * Returns Y-coordinate of character
     * @return Y- coordinate
     */
    protected int get_Y() {
        return y;
    }

    /**
     * Sets the position of the character
     * @param x X-coordinate
     * @param y Y-coordinate
     */
    protected void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
