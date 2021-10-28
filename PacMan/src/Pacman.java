import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
//Image imports
import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;

class Pacman extends JComponent implements LivingCharacter {
    int x, y;
    final int pacSize = 30;
    final int moveDistance = 2;
    // . means no current direction
    char direction = '.';
    // . means no lastKey waiting
    char lastKey = '.';
    boolean isMoving = false;
    int highOnCandyMs = 0;

    ArrayList<BufferedImage> pacImages = new ArrayList<BufferedImage>();
    BufferedImage currentImgBig;
    BufferedImage currentImgSmall;
    Boolean animation = true;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
        Maze.INSTANCE.setPacManPos(x, y);
        System.out.println("Creating a Pac at " + x + ", " + y);

        // Load images.
        try {

            pacImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacLeftBig.png"))); // 0
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacRightBig.png"))); // 1
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacUpBig.png"))); // 2
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacDownBig.png"))); // 3

            pacImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacLeftSmall.png"))); // 4
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacRightSmall.png"))); // 5
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacUpSmall.png"))); // 6
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacDownSmall.png"))); // 7

            // Initial pic.
            currentImgBig = pacImages.get(0);
            currentImgSmall = pacImages.get(4);

        } catch (IOException e) {
            System.out.println("Pacmans bild kunde inte hämtas: " + e.getMessage());
        }
    }

    public BufferedImage getImage() {
        return currentImgBig;
    }

    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
            lastKey = 'U';
            break;
        case KeyEvent.VK_DOWN:
            lastKey = 'D';
            break;
        case KeyEvent.VK_LEFT:
            lastKey = 'L';
            break;
        case KeyEvent.VK_RIGHT:
            lastKey = 'R';
            break;
        case 27:
            // key = 'E'; // todo Escape (Stop playing)
            break;
        case 80:
            // key = 'P'; // todo P (Pause)
        }
    }

    private boolean withingBoundary() {
        if ((y >= pacSize && y <= Maze.INSTANCE.height - pacSize)
                && (x >= pacSize && x <= Maze.INSTANCE.width - pacSize)) return true;
        else {
            System.out.println("Out of boundary, " + x + ", " + y);
            return false;
        }
    }

    public void doMove() {
        if (highOnCandyMs > 0) highOnCandyMs -= 1;

        // Check if a key has been pressed...
        if (lastKey != '.') {

            // Only evaluate for free path when Pacman is at the center of a grid

            // Pacman is inside a vertical grid
            if (inVerticalGrid() && withingBoundary()) {
                if ((lastKey == 'U' && goUp()) || (lastKey == 'D' && goDown())) {
                    direction = lastKey;
                    lastKey = '.';
                }
            }

            // Pacman is inside a horizontal grid
            if (inHorizontalGrid() && withingBoundary()) {
                if ((lastKey == 'L' && goLeft()) || lastKey == 'R' && goRight()) {
                    direction = lastKey;
                    lastKey = '.';
                }
            }
        }

        /**
         * Rita pacman med munnen åt rätt håll samt förflytta honom Kolla innan varje
         * förflyttning at närliggande ruta ej är en vägg
         */

        Maze.INSTANCE.setPacManDirection(direction);
        isMoving = false;
        switch (direction) {

        case 'U':
            if (goUp() && inVerticalGrid()) {
                y -= moveDistance;
                currentImgBig = pacImages.get(2);
                currentImgSmall = pacImages.get(6);
                isMoving = true;
                Maze.INSTANCE.setPacManPos(x, y);
            }
            break;

        case 'D':
            if (goDown() && inVerticalGrid()) {
                y += moveDistance;
                currentImgBig = pacImages.get(3);
                currentImgSmall = pacImages.get(7);
                isMoving = true;
                Maze.INSTANCE.setPacManPos(x, y);
            }
            break;

        case 'L':
            // if pacman went thru tunnel
            if (x - moveDistance < 0)
                x = Maze.INSTANCE.width;

            if (goLeft() && inHorizontalGrid()) {
                x -= moveDistance;
                currentImgBig = pacImages.get(0);
                currentImgSmall = pacImages.get(4);
                isMoving = true;
                Maze.INSTANCE.setPacManPos(x, y);
            }
            break;

        case 'R':
            // if pacman went thru tunnel
            if (x + pacSize + moveDistance > Maze.INSTANCE.width)
                // x = -pacSize;
                x = 0;

            if (goRight() && inHorizontalGrid()) {
                x += moveDistance;
                currentImgBig = pacImages.get(1);
                currentImgSmall = pacImages.get(5);
                isMoving = true;
                Maze.INSTANCE.setPacManPos(x, y);
            }
            break;

        }

        // Check for food or candy at new position
        if (isMoving && x >= 0) {
            MazeBrick brick = Maze.INSTANCE.getBrick(x, y);

            if (brick.getType() == "food") {
                Maze.INSTANCE.ateFood();
                GameEngine.INSTANCE.ateFood();
                brick.changeBrick("space", Maze.INSTANCE.space);
            }

            if  (brick.getType() == "candy") {
                brick.changeBrick("space", Maze.INSTANCE.space);
                // Sets pacman to be invincible for 800 ticks
                highOnCandyMs = 800;
            }
        }

    }

    private boolean inVerticalGrid() {
        return (x % Maze.INSTANCE.gridWidth <= 1);
    }

    private boolean inHorizontalGrid() {
        return (y % Maze.INSTANCE.gridHeight <= 1);
    }

    private boolean goUp() {
        return (!Maze.INSTANCE.getBrick(x + pacSize / 2, y - moveDistance).isWall());
    }

    private boolean goDown() {
        return (!Maze.INSTANCE.getBrick(x + pacSize / 2, y + pacSize).isWall());
    }

    private boolean goLeft() {
        return (!Maze.INSTANCE.getBrick(x - moveDistance, y + pacSize / 2).isWall());
    }

    private boolean goRight() {
        return (!Maze.INSTANCE.getBrick(x + pacSize, y + pacSize / 2).isWall());
    }

    public void draw(Graphics g) {
        // Stops animation if pacman is not moving.
        if (isMoving) {
            animation = !animation;
        }

        g.drawImage(animation ? currentImgBig : currentImgSmall, x, y, pacSize, pacSize, null);
    }

    /**
     * Returns a surrounding rectangle of pacman in order to determine repaint area
     * 
     * @return rectangle surrounding pacman
     */
    public Rectangle getRectangle() {

        // If pacman is just crossing through the shortcut return a wider rectangle
        // covering both exists
        if ((x < pacSize && direction == 'R') || x + pacSize > Maze.INSTANCE.width && direction == 'L') {
            return new Rectangle(0, y - 2, Maze.INSTANCE.width - x, pacSize + 4);
        } else {
            return new Rectangle(x - 2, y - 2, pacSize + 4, pacSize + 4);
        }
    }

}