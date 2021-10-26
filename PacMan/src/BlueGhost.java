
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
    String direction;
    ArrayList<BufferedImage> blueGhostimgs = new ArrayList<BufferedImage>();
    BufferedImage currentImg;

    public BlueGhost(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        iChaseBehaviour = new ChaseRandom();
        iScatterBehaviour = new ScatterTopLeftBehaviour();
        iFrightenedBehaviour = new FrightenedWandering();
        iWakeUpBehaviour = new WakeUpBehaviour();

        try {

            blueGhostimgs.add(ImageIO.read(this.getClass().getResource("resources/blueGhost/blueUp.png")));
            blueGhostimgs.add(ImageIO.read(this.getClass().getResource("resources/blueGhost/blueDown.png")));
            blueGhostimgs.add(ImageIO.read(this.getClass().getResource("resources/blueGhost/blueRight.png")));
            blueGhostimgs.add(ImageIO.read(this.getClass().getResource("resources/blueGhost/blueLeft.png")));
        } catch (IOException e) {
            System.out.println("Spökenas bild kunde inte hämtas: " + e.getMessage());
        }
        currentImg = blueGhostimgs.get(0);

    }

    @Override
    public void doMove() {

        switch (iChaseBehaviour.chase(x, y)) {

        case "up":
            y -= moveDistance;
            currentImg = blueGhostimgs.get(0);
            break;

        case "down":
            y += moveDistance;
            currentImg = blueGhostimgs.get(1);
            break;

        case "left":
            x -= moveDistance;
            currentImg = blueGhostimgs.get(3);
            break;

        case "right":
            x += moveDistance;
            currentImg = blueGhostimgs.get(2);
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
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x - 2, y - 2, ghostSize + 4, ghostSize + 4);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public BufferedImage getImage() {
        // TODO Auto-generated method stub
        return null;
    }

}
