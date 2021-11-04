import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

abstract class LivingCharacter extends JComponent {
    protected int x, y;
    boolean animation;
    boolean isMoving = false;

    // . means no current direction
    char direction = '.';

    final int cSize = 30;
    final int moveDistance = 2;
    ArrayList<BufferedImage> charImages = new ArrayList<BufferedImage>();

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
     * @param g
     */
    abstract public void draw(Graphics g);

    protected boolean withinBoundary() {
        if ((y >= cSize && y <= Maze.INSTANCE.height - cSize) && (x >= cSize && x <= Maze.INSTANCE.width - cSize))
            return true;
        else {
            System.out.println("Out of boundary, " + x + ", " + y);
            return false;
        }
    }

    protected boolean inVerticalGrid() {
        return (x % Maze.INSTANCE.gridWidth <= 1);
    }

    protected boolean inHorizontalGrid() {
        return (y % Maze.INSTANCE.gridHeight <= 1);
    }

    protected boolean goUp() {
        return (!Maze.INSTANCE.getBrick(x + cSize / 2, y - moveDistance).isWall());
    }

    protected boolean goDown() {
        return (!Maze.INSTANCE.getBrick(x + cSize / 2, y + cSize).isWall());
    }

    protected boolean goLeft() {
        return (!Maze.INSTANCE.getBrick(x - moveDistance, y + cSize / 2).isWall());
    }

    protected boolean goRight() {
        return (!Maze.INSTANCE.getBrick(x + cSize, y + cSize / 2).isWall());
    }

    /**
     * Returns a surrounding rectangle of pacman in order to determine repaint area
     *
     * @return rectangle surrounding pacman
     */
    public Rectangle getRectangle() {
        // If the character is just crossing through the shortcut return a wider
        // rectangle
        // covering both exists
        if ((x < cSize && direction == 'R') || x + cSize > Maze.INSTANCE.width && direction == 'L') {
            return new Rectangle(0, y - 2, Maze.INSTANCE.width - x, cSize + 4);
        } else {
            return new Rectangle(x - 2, y - 2, cSize + 4, cSize + 4);
        }
    }

    /**
     * Returns a rectangle for collision check
     * 
     * @return rectangle only covering pacman
     */
    public Rectangle getCollisionRectangle() {
        //return new Rectangle(x + moveDistance, y + moveDistance, cSize - moveDistance, cSize - moveDistance);
        return new Rectangle(x + cSize / 4, y + cSize / 4, cSize /2, cSize / 2);
    }

    protected int get_X() {
        return x;
    }

    protected int get_Y() {
        return y;
    }

    protected void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
