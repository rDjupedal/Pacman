
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

public class BlueGhost extends Ghost implements LivingCharacter {

    int x, y;
    String name;
    final int ghostSize = 30;
    final int moveDistance = 1;
    // char lastKey;
    String direction = "up";
    Color color = Color.RED;

    ArrayList<BufferedImage> monsterImg = new ArrayList<BufferedImage>();
    BufferedImage currentImg;

    public BlueGhost(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        IchaseBehaviour = new ChaseRandom();

        this.name = "Blue Ghost";
        System.out.println(name + "created at " + x + ", " + y);

        // Laddar deras bilder beroende på sekvens. (Ska man göra olika klasser för alla
        // spöken istället? Färgkodat?)
        try {

            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/blueGhost/blueUp.png")));
            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/blueGhost/blueDown.png")));
            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/blueGhost/blueRight.png")));
            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/blueGhost/blueLeft.png")));
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

        switch (IchaseBehaviour.chase(x, y)) {

        case "up":
            y -= moveDistance;
            currentImg = monsterImg.get(0);
            break;

        case "down":
            y += moveDistance;
            currentImg = monsterImg.get(1);
            break;

        case "left":
            x -= moveDistance;
            currentImg = monsterImg.get(3);
            break;

        case "right":
            x += moveDistance;
            currentImg = monsterImg.get(2);
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
        g.drawImage(currentImg, x, y, ghostSize, ghostSize, null);
    }

    /**
     * Returns a rectangle around pacman in order to only redraw this part
     * 
     * @return rectangle surronding object
     */
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x - 2, y - 2, ghostSize + 4, ghostSize + 4);
    }

}
