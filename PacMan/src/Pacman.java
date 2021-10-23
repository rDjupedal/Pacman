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
    BufferedImage currentImg;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("Creating a Pac at " + x + ", " + y);

        // Load images.
        try {

            pacImages.add(ImageIO.read(this.getClass().getResource("resources/pac/pacLeft.png")));
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/pac/pacRight.png")));
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/pac/pacUp.png")));
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/pac/pacDown.png")));

            // Initial pic.
            currentImg = pacImages.get(0);

        } catch (IOException e) {
            System.out.println("Pacmans bild kunde inte hämtas: " + e.getMessage());
        }
    }

    public BufferedImage getImage() {
        return currentImg;
    }

    /**
     * När en knapp trycks så ändras även bilden så att pac åker åtå rätt håll.
     */
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode());
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
        //System.out.println("last key: " + lastKey);

        // todo: dont hardcode gridsize! (30x30)
        if (lastKey != direction) {
            // Only change direction to up / down when we are horizontally in center of a grid
            if (lastKey == 'U' || lastKey == 'D') {
                if ( x % 30 <= 1 ) {
                    direction = lastKey;
                    System.out.println("changeing direction to " + lastKey + " at pos " + x + ", " + y);
                };
            }
            // Only change direction to left / right when we are vertically in center of a grid
            if (lastKey == 'L' || lastKey == 'R') {
                if ( y % 30 <= 1 ) {
                    direction = lastKey;
                    System.out.println("changeing direction to " + lastKey + " at pos " + x + ", " + y);
                };
            }
        }

        //switch (lastKey) {
        switch (direction) {
        case 'U':
            y = y - moveDistance;
            currentImg = pacImages.get(2);
            break;
        case 'D':
            currentImg = pacImages.get(3);
            y = y + moveDistance;
            break;
        case 'L':
            currentImg = pacImages.get(0);
            x = x - moveDistance;
            break;
        case 'R':
            currentImg = pacImages.get(1);
            x = x + moveDistance;
            break;
        }
        // DEBUG
        //System.out.println("pacman moved to " + x + ", " + y);
    }

    public void draw(Graphics g) {
        g.drawImage(currentImg, x, y, pacSize, pacSize, null);
    }

    public Rectangle getRectangle() {
        return new Rectangle(x-2, y-2, pacSize+4, pacSize+4);
    }

}