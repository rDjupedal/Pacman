
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.*;

//Image imports
import javax.imageio.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PinkGhost extends Ghost implements Character {

    int x, y;
    String name;
    final int monsterSize = 30;
    final int moveDistance = 1;
    // char lastKey;
    String direction = "up";
    Color color = Color.RED;

    ArrayList<BufferedImage> monsterImg = new ArrayList<BufferedImage>();
    BufferedImage currentImg;

    public PinkGhost(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.name = "Pink Ghost";
        System.out.println(name + "created at " + x + ", " + y);

        // Laddar deras bilder beroende på sekvens. (Ska man göra olika klasser för alla
        // spöken istället? Färgkodat?)
        try {

            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/pinkGhost/pinkUp.png")));
            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/pinkGhost/pinkDown.png")));
            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/pinkGhost/pinkRight.png")));
            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/pinkGhost/pinkLeft.png")));
        } catch (IOException e) {
            System.out.println("Spökenas bild kunde inte hämtas: " + e.getMessage());
        }
        currentImg = monsterImg.get(0);

    }

    public BufferedImage getImage() {
        return monsterImg.get(0);
    }

    /**
     * Enkel liten loop som späkena gör. Vänden i olika hörn och snurrar ett helt
     * varv.
     * 
     * @return
     */
    private String getDirection() {
        if (x == 0 && y > 0) {
            direction = "up";

        } else if (x == 800 && y == 0) {
            direction = "down";

        } else if (y == 0 && x > 0) {
            direction = "right";

        } else if (y == 600 && x == 800) {
            direction = "left";
        } else if (x == 0 && y == 0) {
            direction = "right";
        }

        return direction;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Empty, as monsters do not listen to keys.
    }

    @Override
    public void doMove() {

        switch (getDirection()) {
        case "left":
            currentImg = monsterImg.get(3);
            x = x - moveDistance;
            break;
        case "right":
            currentImg = monsterImg.get(2);
            x = x + moveDistance;
            break;
        case "up":
            currentImg = monsterImg.get(0);
            y = y - moveDistance;
            break;
        case "down":
            currentImg = monsterImg.get(1);
            y = y + moveDistance;
        default:
            break;
        }

        // DEbug test.
        String debug = String.format("%s is at %d, %d", name, x, y);
        // System.out.println(debug);
    }

    /**
     * Getters för positionen.
     */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void draw(Graphics g) {
        // System.out.printf("Drawing %s from Monsterclass %n", name);
        g.drawImage(currentImg, x, y, monsterSize, monsterSize, null);
    }

    /**
     * Returns a rectangle around pacman in order to only redraw this part
     * 
     * @return rectangle surronding object
     */
    public Rectangle getRectangle() {
        return new Rectangle(x - 2, y - 2, monsterSize + 4, monsterSize + 4);
    }

}
