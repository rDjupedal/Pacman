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
        case 38:
            lastKey = 'U'; // Up
            currentImg = pacImages.get(2);

            break;
        case 40:
            lastKey = 'D'; // Down
            currentImg = pacImages.get(3);
            break;
        case 37:
            lastKey = 'L'; // Left
            currentImg = pacImages.get(0);
            break;
        case 39:
            lastKey = 'R'; // Right
            currentImg = pacImages.get(1);
            break;
        case 27:
            lastKey = 'E'; // Escape (Stop playing)
            break;
        case 80:
            lastKey = 'P'; // P (Pause)
        }
    }

    public void doMove() {
        System.out.println("last key: " + lastKey);
        switch (lastKey) {
        case 'U':
            y = y - moveDistance;

            break;
        case 'D':
            y = y + moveDistance;
            break;
        case 'L':
            x = x - moveDistance;
            break;
        case 'R':
            x = x + moveDistance;

            break;
        }
        // DEBUG
        System.out.println("pacman moved to " + x + ", " + y);
    }

    // Getters för pacmans position.
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Används inte just nu.
     */
    public void draw(Graphics g) {
        System.out.println("drawing pacman from drawPacman..");
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, pacSize, pacSize);
    }

    /*
     * public void paintComponent(Graphics g) {
     * System.out.println("drawing pacman from its own paintComponent method..");
     * g.setColor(Color.YELLOW); g.fillOval(x, y, pacSize, pacSize); }
     * 
     */

}