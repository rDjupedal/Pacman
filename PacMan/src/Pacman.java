import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
//Image imports
import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;

class Pacman extends LivingCharacter {

    // . means no lastKey waiting
    char lastKey = '.';
    int highOnCandyMs = 0;
    boolean died = false;

    ArrayList<BufferedImage> pacImages = new ArrayList<BufferedImage>();
    BufferedImage currentImgBig;
    BufferedImage currentImgSmall;

    protected boolean died() {
        if (died) {
            died = false;
            return true;
        }
        return false;
    }

    protected Pacman(int x, int y) {
        this.x = x;
        this.y = y;
        Maze.INSTANCE.setPacManPos(x, y);
        animation = true;

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
            currentImgBig = pacImages.get(1);
            currentImgSmall = pacImages.get(4);

        } catch (IOException e) {
            System.out.println("Pacmans bild kunde inte hämtas: " + e.getMessage());
        }
    }

    protected void keyPressed(KeyEvent e) {

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

    protected void setDirection() {
        if (highOnCandyMs > 0) highOnCandyMs -= 1;

        // Check if a key has been pressed...
        if (lastKey != '.') {

            // Only evaluate for free path when Pacman is at the center of a grid

            // Pacman is inside a vertical grid
            if (inVerticalGrid() && withinBoundary()) {
                if ((lastKey == 'U' && goUp()) || (lastKey == 'D' && goDown())) {
                    direction = lastKey;
                    lastKey = '.';
                }
            }

            // Pacman is inside a horizontal grid
            if (inHorizontalGrid() && withinBoundary()) {
                if ((lastKey == 'L' && goLeft()) || lastKey == 'R' && goRight()) {
                    direction = lastKey;
                    lastKey = '.';
                }
            }
        }
    }

    protected void moveCharacter() {
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
            if (x + cSize + moveDistance > Maze.INSTANCE.width)
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

    public void draw(Graphics g) {
        // Stops animation if pacman is not moving.
        if (isMoving) {
            animation = !animation;
        }
        //pacman:
        g.drawImage(animation ? currentImgBig : currentImgSmall, x, y, cSize, cSize, null);
    }
}