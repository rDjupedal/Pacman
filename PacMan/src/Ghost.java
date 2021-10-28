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
    Boolean onTheMove = false;
    Boolean pickDirection = true;

    String name;
    final int ghostSize = 30;
    final int moveDistance = 2;
    String direction;
    ArrayList<BufferedImage> ghostImgs = new ArrayList<BufferedImage>();
    BufferedImage currentImg;
    Boolean chase = false;
    String[] state = { "chase", "scatter" };
    String currentState = "wakeup";

    public Ghost(int x, int y, String color) {
        this.x = x;
        this.y = y;
        iFrightenedBehaviour = new FrightenedWandering();
        iWakeUpBehaviour = new WakeUpBehaviour(this);
        this.ghostColor = color.toLowerCase();
        this.name = String.format("%s ghost", ghostColor);

        // Will be changed to reflect the movement of each ghost later when other
        // movements are implemented.
        switch (ghostColor) {
        case "red":
            iChaseBehaviour = new ChaseAggresive();
            iScatterBehaviour = new ScatterBehaviour("BR");
            break;
        case "blue":
            iChaseBehaviour = new ChaseAggresive();
            iScatterBehaviour = new ScatterBehaviour("BL");
            break;

        case "yellow":
            iChaseBehaviour = new ChaseAggresive();
            iScatterBehaviour = new ScatterBehaviour("TR");
            break;
        case "pink":
            iChaseBehaviour = new ChaseAggresive();
            iScatterBehaviour = new ScatterBehaviour("TL");
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

        if (inHorizontalGrid() && inVerticalGrid()) {
            pickDirection = true;
        }
        while (pickDirection) {
            direction = getState();
            pickDirection = false;

        }

        if (!pickDirection) {
            switch (direction) {

            case "up":

                y -= moveDistance;
                currentImg = ghostImgs.get(0);

                break;

            case "down":
                y += moveDistance;
                currentImg = ghostImgs.get(1);
                break;

            case "left":

                if (x - moveDistance < 0) {
                    x = Maze.INSTANCE.width;
                } else {
                    x -= moveDistance;
                    currentImg = ghostImgs.get(2);
                }
                break;

            case "right":
                if (x + ghostSize + moveDistance > Maze.INSTANCE.width) {
                    x = 0;
                } else {

                    x += moveDistance;
                    currentImg = ghostImgs.get(3);
                }

                break;
            }
        }
    }

    private boolean inVerticalGrid() {
        return (x % Maze.INSTANCE.gridWidth == 0);
    }

    private boolean inHorizontalGrid() {
        return (y % Maze.INSTANCE.gridHeight == 0);
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

            setChase();

        }

    }

    public void setChase() {
        currentState = "chase";

        System.out.println("current state is : " + currentState);
    }

    public void setScatter() {

        currentState = "scatter";
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
