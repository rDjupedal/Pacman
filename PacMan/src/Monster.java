
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.*;

//Image imports
import javax.imageio.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Monster implements Character {

    int x, y;
    String name;
    final int monsterSize = 30;
    final int moveDistance = 1;
    char lastKey;
    String direction = "up";
    Color color = Color.RED;

    BufferedImage monsterImg;

    public Monster(int x, int y, int number) {
        this.x = x;
        this.y = y;
        this.name = "Monster " + number;
        System.out.printf("Creating %s at %d, %d", name, x, y);

        // Laddar deras bilder beroende på sekvens. (Ska man göra olika klasser för alla
        // spöken istället? Färgkodat?)
        try {

            String imgPath = String.format("resources/ghost/%d.png", number);
            monsterImg = ImageIO.read(this.getClass().getResource(imgPath));
        } catch (IOException e) {
            System.out.println("Spökenas bild kunde inte hämtas: " + e.getMessage());
        }

    }

    public BufferedImage getImage() {
        return monsterImg;
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
            x = x - moveDistance;
            break;
        case "right":
            x = x + moveDistance;
            break;
        case "up":
            y = y - moveDistance;
            break;
        case "down":
            y = y + moveDistance;
        default:
            break;
        }

        // DEbug test.
        String debug = String.format("%s is at %d, %d", name, x, y);
        System.out.println(debug);
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

    /**
     * Används inte just nu.
     */
    @Override
    public void draw(Graphics g) {
        System.out.printf("Drawing %s from Monsterclass %n", name);
        g.setColor(color);
        g.fillOval(x, y, monsterSize, monsterSize);

    }

}
