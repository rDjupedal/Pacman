import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
//Image imports
import javax.imageio.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

class Pacman extends JComponent implements Character {
    int x, y;
    final int pacSize = 30;
    final int moveDistance = 2;
    char lastKey;
    char direction = 'R';

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

    /**
     * När en knapp trycks så ändras även bilden så att pac åker åtå rätt håll.
     */
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
            lastKey = 'U'; // Up
            break;
        case KeyEvent.VK_DOWN:
            lastKey = 'D'; // Down
            break;
        case KeyEvent.VK_LEFT:
            lastKey = 'L'; // Left
            break;
        case KeyEvent.VK_RIGHT:
            lastKey = 'R'; // Right
            break;
        case 27:
            lastKey = 'E'; // Escape (Stop playing)
            break;
        case 80:
            lastKey = 'P'; // P (Pause)
        }
    }

    public void doMove() {

        // todo: dont hardcode 2*gridsize! (60x60)
        if (lastKey != direction) {
            // Only change direction to up / down when we are horizontally in center of a
            // grid
            if (lastKey == 'U' || lastKey == 'D') {
                if (x % 60 <= 1) {
                    direction = lastKey;
                    System.out.println("changing direction to " + lastKey + " at pos " + x + ", " + y);
                }
                ;
            }
            // Only change direction to left / right when we are vertically in center of a
            // grid
            if (lastKey == 'L' || lastKey == 'R') {
                if (y % 60 <= 1) {
                    direction = lastKey;
                    System.out.println("changing direction to " + lastKey + " at pos " + x + ", " + y);
                }
                ;
            }
        }

        // switch (lastKey) {
        switch (direction) {
        case 'U':
            y = y - moveDistance;
            currentImgBig = pacImages.get(2);
            currentImgSmall = pacImages.get(6);
            break;
        case 'D':
            currentImgBig = pacImages.get(3);
            currentImgSmall = pacImages.get(7);
            y = y + moveDistance;
            break;
        case 'L':
            currentImgBig = pacImages.get(0);
            currentImgSmall = pacImages.get(4);
            x = x - moveDistance;
            break;
        case 'R':
            currentImgBig = pacImages.get(1);
            currentImgSmall = pacImages.get(5);
            x = x + moveDistance;
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