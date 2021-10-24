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
    char lastKey;
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
            if (keyBuffer.peekFirst() != direction) {

                // Only change direction to up / down when pacman is inside a vertical grid
                if (keyBuffer.peekFirst() == 'U' || keyBuffer.peekFirst() == 'D') {
                    if (x % Maze.INSTANCE.gridWidth <= 1) {
                        direction = keyBuffer.pollFirst();
                        System.out.println("changing direction to " + direction + " at pos " + x + ", " + y);
                    }
                }

                // Only change direction to left / right when pacman is inside a horizontal grid
                else if (keyBuffer.peekFirst() == 'L' || keyBuffer.peekFirst() == 'R') {
                    if (y % Maze.INSTANCE.gridHeight <= 1) {
                        direction = keyBuffer.pollFirst();
                        System.out.println("changing direction to " + direction + " at pos " + x + ", " + y);
                    }
                }
            }
        }

        /**
         * Rita pacman med munnen åt rätt håll samt förflytta honom
         */

        switch (direction) {

        case 'U':
            if (!Maze.INSTANCE.getBrick(x, y - moveDistance).isWall()) {
                y -= moveDistance;
                currentImgBig = pacImages.get(2);
                currentImgSmall = pacImages.get(6);
            } else
                isMoving = false;

            break;

        case 'D':
            if (!Maze.INSTANCE.getBrick(x, y + pacSize).isWall()) {
                y += moveDistance;
                currentImgBig = pacImages.get(3);
                currentImgSmall = pacImages.get(7);
            } else
                isMoving = false;

            break;

        case 'L':
            if (x - moveDistance < 0)
                x = Maze.INSTANCE.width - Maze.INSTANCE.gridWidth;
            if (!Maze.INSTANCE.getBrick(x - moveDistance, y).isWall()) {
                x -= moveDistance;
                currentImgBig = pacImages.get(0);
                currentImgSmall = pacImages.get(4);
            } else
                isMoving = false;

            break;

        case 'R':
            if (x + pacSize + moveDistance >= Maze.INSTANCE.width)
                x = 0;
            if (!Maze.INSTANCE.getBrick(x + pacSize, y).isWall()) {
                x += moveDistance;
                currentImgBig = pacImages.get(1);
                currentImgSmall = pacImages.get(5);
            } else
                isMoving = false;

            break;
        }

        // DEBUG
        // System.out.println("pacman moved to " + x + ", " + y);
    }

    public void draw(Graphics g) {
        animation = !animation;
        g.drawImage(animation ? currentImgBig : currentImgSmall, x, y, pacSize, pacSize, null);
    }

    public Rectangle getRectangle() {
        return new Rectangle(x - 2, y - 2, pacSize + 4, pacSize + 4);
    }

}