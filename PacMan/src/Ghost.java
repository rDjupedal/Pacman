import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;
import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;

public class Ghost extends JComponent implements LivingCharacter {
    int x, y;

    IChaseBehaviour iChaseBehaviour;
    IScatterBehaviour iScatterBehaviour;
    IFrightenedBehaviour iFrightenedBehaviour;
    IWakeUpBehaviour iWakeUpBehaviour;
    String ghostColor;
    String imgPath;

    String name;
    final int ghostSize = 30;
    final int moveDistance = 1;
    String direction;
    ArrayList<BufferedImage> ghostImgs = new ArrayList<BufferedImage>();
    BufferedImage currentImg;

    public Ghost(int x, int y, String color) {
        this.x = x;
        this.y = y;
        iFrightenedBehaviour = new FrightenedWandering();
        iWakeUpBehaviour = new WakeUpBehaviour();
        this.ghostColor = color.toLowerCase();
        this.name = String.format("%s ghost", ghostColor);

        // Will be changed to reflect the movement of each ghost later when other
        // movements are implemented.
        switch (ghostColor) {
        case "red":
            iChaseBehaviour = new ChaseRandom();
            iScatterBehaviour = new ScatterBottomLeftBehaviour();
            break;
        case "blue":
            iChaseBehaviour = new ChaseRandom();
            iScatterBehaviour = new ScatterBottomRightBehaviour();
            break;

        case "yellow":
            iChaseBehaviour = new ChaseRandom();
            iScatterBehaviour = new ScatterTopRightBehaviour();
            break;
        case "pink":
            iChaseBehaviour = new ChaseRandom();
            iScatterBehaviour = new ScatterTopLeftBehaviour();
            break;

        default:
            break;
        }

        try {

            // Looping pictures

            for (int i = 0; i < 4; i++) {
                this.imgPath = String.format("resources/%sGhost/%d.png", ghostColor, i);
                ghostImgs.add(ImageIO.read(this.getClass().getResource(imgPath)));
            }

        } catch (IOException e) {
            System.out.println("Ghost pic could not be loaded: " + e.getMessage());
        }
        currentImg = ghostImgs.get(0);
    }

    /**
     * Can we get this to work for all ghosts?
     */
    @Override
    public void doMove() {

        switch (iChaseBehaviour.chase(x, y)) {

        case "up":
            y -= moveDistance;
            currentImg = ghostImgs.get(0);
            break;

        case "down":
            y += moveDistance;
            currentImg = ghostImgs.get(1);
            break;

        case "left":
            x -= moveDistance;
            currentImg = ghostImgs.get(2);
            break;

        case "right":
            x += moveDistance;
            currentImg = ghostImgs.get(3);
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
        // TODO Auto-generated method stub

    }

    public BufferedImage getImage() {
        // TODO Auto-generated method stub
        return null;
    }
}
