import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
//Image imports
import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

class Pacman extends JComponent implements LivingCharacter {
    int x, y;
    final int pacSize = 30;
    final int moveDistance = 2;
    char direction = ' ';
    boolean isMoving = false;
    Deque<Character> keyBuffer = new LinkedList<>();

    ArrayList<BufferedImage> pacImages = new ArrayList<BufferedImage>();
    BufferedImage currentImgBig;
    BufferedImage currentImgSmall;
    Boolean animation = true;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
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

        if (!isMoving) {
            keyBuffer.clear();
        }

        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
            keyBuffer.addLast('U');
            break;
        case KeyEvent.VK_DOWN:
            keyBuffer.addLast('D');
            break;
        case KeyEvent.VK_LEFT:
            keyBuffer.addLast('L');
            break;
        case KeyEvent.VK_RIGHT:
            keyBuffer.addLast('R');
            break;
        case 27:
            // key = 'E'; // todo Escape (Stop playing)
            break;
        case 80:
            // key = 'P'; // todo P (Pause)
        }

        if (!isMoving) {
            isMoving = true;
            direction = keyBuffer.pollFirst();
        }

        System.out.println("size of keyBuffer: " + keyBuffer.size());
        for (char key : keyBuffer) {
            System.out.print(key + ", ");
        }
    }

    public void doMove() {

        for (int i = 0; i < keyBuffer.size(); i++) {
            if (keyBuffer.peekFirst() == direction) {
                keyBuffer.removeFirst();
            }
        }

        if (!keyBuffer.isEmpty()) {
            char lastKey = keyBuffer.peekFirst();

            if (lastKey != direction) {

                // Only evaluate for free path when Pacman crosses the center of a grid

                // Pacman is inside a vertical grid
                if (inVerticalGrid()) {
                    if ((lastKey == 'U' && goUp()) || (lastKey == 'D' && goDown())) {
                        direction = keyBuffer.pollFirst();
                        System.out.println("direction changed to " + direction + " at pos " + x + ", " + y);
                    }
                }

                // Pacman is inside a horizontal grid
                if (inHorizontalGrid()) {
                    if ( (lastKey == 'L' && goLeft()) || lastKey =='R' && goRight() ) {
                        direction = keyBuffer.pollFirst();
                        System.out.println("direction changed to " + direction + " at pos " + x + ", " + y);
                    }
                }
            }
        }

        /**
         * Rita pacman med munnen åt rätt håll samt förflytta honom
         * Kolla innan varje förflyttning at närliggande ruta ej är en vägg
         */

        switch (direction) {

        case 'U':
            //if (!Maze.INSTANCE.getBrick(x, y - moveDistance).isWall()) {
            if (goUp() && inVerticalGrid()) {
                y -= moveDistance;
                currentImgBig = pacImages.get(2);
                currentImgSmall = pacImages.get(6);
            } else
                isMoving = false;
            break;

        case 'D':
            //if (!Maze.INSTANCE.getBrick(x, y + pacSize).isWall()) {
            if (goDown() && inVerticalGrid()) {
                y += moveDistance;
                currentImgBig = pacImages.get(3);
                currentImgSmall = pacImages.get(7);
            } else
                isMoving = false;
            break;

        case 'L':
            // if pacman went thru tunnel
            if (x - moveDistance < 0)
                x = Maze.INSTANCE.width;

            //if (!Maze.INSTANCE.getBrick(x - moveDistance, y).isWall()) {
            if (goLeft() && inHorizontalGrid()) {
                x -= moveDistance;
                currentImgBig = pacImages.get(0);
                currentImgSmall = pacImages.get(4);
            } else
                isMoving = false;
            break;

        case 'R':
            // if pacman went thru tunnel
            if (x + pacSize + moveDistance > Maze.INSTANCE.width)
                x = -pacSize;

            //if (!Maze.INSTANCE.getBrick(x + pacSize, y).isWall()) {
            if (goRight() && inHorizontalGrid()) {
                x += moveDistance;
                currentImgBig = pacImages.get(1);
                currentImgSmall = pacImages.get(5);
            } else
                isMoving = false;
            break;
        }

    }

    private boolean inVerticalGrid() {
        return (x % Maze.INSTANCE.gridWidth <= 1);
    }

    private boolean inHorizontalGrid() {
        return (y % Maze.INSTANCE.gridHeight <= 1);
    }

    private boolean goUp(){
        return (!Maze.INSTANCE.getBrick(x, y - moveDistance).isWall());
    }

    private boolean goDown() {
        return (!Maze.INSTANCE.getBrick(x + pacSize / 2, y + pacSize).isWall());
    }

    private boolean goLeft() {
        return (!Maze.INSTANCE.getBrick(x - moveDistance, y).isWall());
    }

    private boolean goRight() {
        return (!Maze.INSTANCE.getBrick(x + pacSize, y).isWall());
    }

    public void draw(Graphics g) {
        animation = !animation;
        g.drawImage(animation ? currentImgBig : currentImgSmall, x, y, pacSize, pacSize, null);
    }

    /**
     * Returns a surrounding rectangle of pacman in order to determine repaint area
     * @return rectangle surrounding pacman
     */
    public Rectangle getRectangle() {

        // If pacman is just crossing through the shortcut return a wider rectangle covering both exists
        if ((x < pacSize && direction == 'R') ||
                x + pacSize > Maze.INSTANCE.width && direction == 'L' ) {
            return new Rectangle(0, y - 2, Maze.INSTANCE.width - x, pacSize + 4);
        }   else {
                return new Rectangle(x - 2, y - 2, pacSize + 4, pacSize + 4);
            }
    }

}