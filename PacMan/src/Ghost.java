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
    private int x, y;

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
    Boolean chase = false;
    String[] state = { "chase", "scatter" };
    String currentState = "frightened";

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
            iChaseBehaviour = new ChaseAggresive();
            iScatterBehaviour = new ScatterBottomLeftBehaviour();
            break;
        case "blue":
            iChaseBehaviour = new ChaseAggresive();
            iScatterBehaviour = new ScatterBottomRightBehaviour();
            break;

        case "yellow":
            iChaseBehaviour = new ChaseAggresive();
            iScatterBehaviour = new ScatterTopRightBehaviour();
            break;
        case "pink":
            iChaseBehaviour = new ChaseAggresive();
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

    @Override
    public void doMove() {

        // Needs to be changed depending on the ghosts state which are wakeUp, Chase,
        // Scatter and frightened.
        switch (getState()) {

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

        if (e.getKeyCode() == 83) {

            setState();

        }

    }

    private void setState() {

        if (chase) {
            currentState = "chase";
        } else {
            currentState = "scatter";
        }

        chase = !chase;

        System.out.println("current state is : " + currentState);
    }

    private String getState() {
        if (currentState.equalsIgnoreCase("wakeup")) {
            return iWakeUpBehaviour.awokenBehaviour(x, y);

        } else if (currentState.equalsIgnoreCase("chase")) {
            return iChaseBehaviour.chase(x, y);

        } else if (currentState.equalsIgnoreCase("scatter")) {
            return iScatterBehaviour.scatter(x, y);

        } else {
            return iFrightenedBehaviour.FrightenedBehaviour(x, y);
        }
    }

    public BufferedImage getImage() {
        // TODO Auto-generated method stub
        return null;
    }
}
