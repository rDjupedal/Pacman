
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

public class PinkGhost extends Ghost implements LivingCharacter {

    int x, y;
    String name;
    final int ghostSize = 30;
    final int moveDistance = 1;
    String direction;

    ArrayList<BufferedImage> pinkGhostImgs = new ArrayList<BufferedImage>();
    BufferedImage currentImg;

    public PinkGhost(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        IchaseBehaviour = new ChaseRandom();
        this.name = "Pink Ghost";

        // Laddar deras bilder beroende på sekvens. (Ska man göra olika klasser för alla
        // spöken istället? Färgkodat?)
        try {

            pinkGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/pinkGhost/pinkUp.png")));
            pinkGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/pinkGhost/pinkDown.png")));
            pinkGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/pinkGhost/pinkRight.png")));
            pinkGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/pinkGhost/pinkLeft.png")));
        } catch (IOException e) {
            System.out.println("Spökenas bild kunde inte hämtas: " + e.getMessage());
        }
        currentImg = pinkGhostImgs.get(0);

    }

    public BufferedImage getImage() {
        return pinkGhostImgs.get(0);
    }

    @Override
    public void doMove() {

        switch (IchaseBehaviour.chase(x, y)) {

        case "up":
            y -= moveDistance;
            currentImg = pinkGhostImgs.get(0);
            break;

        case "down":
            y += moveDistance;
            currentImg = pinkGhostImgs.get(1);
            break;

        case "left":
            x -= moveDistance;
            currentImg = pinkGhostImgs.get(3);
            break;

        case "right":
            x += moveDistance;
            currentImg = pinkGhostImgs.get(2);
            break;
        }

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
    public Rectangle getRectangle() {
        return new Rectangle(x - 2, y - 2, ghostSize + 4, ghostSize + 4);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Empty because of LivingCharacter
    }

}
