
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

public class YellowGhost extends Ghost implements LivingCharacter {

    int x, y;
    String name;
    final int ghostSize = 30;
    final int moveDistance = 1;
    String direction;
    ArrayList<BufferedImage> yellowGhostImgs = new ArrayList<BufferedImage>();
    BufferedImage currentImg;

    public YellowGhost(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        iChaseBehaviour = new ChaseRandom();
        iScatterBehaviour = new ScatterBottomRightBehaviour();
        iFrightenedBehaviour = new FrightenedWandering();
        iWakeUpBehaviour = new WakeUpBehaviour();

        this.name = "Yellow Ghost";

        try {

            yellowGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/yellowGhost/yellowUp.png")));
            yellowGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/yellowGhost/yellowDown.png")));
            yellowGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/yellowGhost/yellowRight.png")));
            yellowGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/yellowGhost/yellowLeft.png")));
        } catch (IOException e) {
            System.out.println("Spökenas bild kunde inte hämtas: " + e.getMessage());
        }
        currentImg = yellowGhostImgs.get(0);

    }

    @Override
    public void doMove() {

        switch (iChaseBehaviour.chase(x, y)) {

        case "up":
            y -= moveDistance;
            currentImg = yellowGhostImgs.get(0);
            break;

        case "down":
            y += moveDistance;
            currentImg = yellowGhostImgs.get(1);
            break;

        case "left":
            x -= moveDistance;
            currentImg = yellowGhostImgs.get(3);
            break;

        case "right":
            x += moveDistance;
            currentImg = yellowGhostImgs.get(2);
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
    public BufferedImage getImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
