import java.awt.Graphics;
import java.util.ArrayList;
import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;

public class Ghost extends LivingCharacter implements StateObserver {
    IChaseBehaviour iChaseBehaviour;
    IScatterBehaviour iScatterBehaviour;
    IFrightenedBehaviour iFrightenedBehaviour;
    IWakeUpBehaviour iWakeUpBehaviour;

    String ghostColor;
    String imgPath;
    Boolean onTheMove = false;
    Boolean pickDirection = true;
    boolean frigthened = false;
    boolean animation = false;

    String name;
    final int cSize = 30;
    final int moveDistance = 2;
    String direction;
    ArrayList<BufferedImage> ghostImgs = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> frigthenedGhostImgs = new ArrayList<BufferedImage>();
    BufferedImage currentImg;
    String currentState = "wakeup";

    public Ghost(int x, int y, String color) {
        this.x = x;
        this.y = y;
        iFrightenedBehaviour = new FrightenedWandering();
        iWakeUpBehaviour = new WakeUpBehaviour(this);

        this.ghostColor = color.toLowerCase();
        this.name = String.format("%s ghost", ghostColor);
        animation = false;

        // Will be changed to reflect the movement of each ghost later when other
        // movements are implemented.
        switch (ghostColor) {
        case "red":
            iChaseBehaviour = new ChaseAggresive();
            iScatterBehaviour = new ScatterBehaviour("TR");
            break;
        case "blue":
            iChaseBehaviour = new ChaseAmbush();
            iScatterBehaviour = new ScatterBehaviour("BR");
            break;

        case "yellow":
            iChaseBehaviour = new ChasePatrol();
            iScatterBehaviour = new ScatterBehaviour("BL");
            break;
        case "pink":
            iChaseBehaviour = new ChaseRandom();
            iScatterBehaviour = new ScatterBehaviour("TL");
            break;

        default:
            break;
        }

        try {

            // Looping pictures
            frigthenedGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/ghost/scaredGhost0.png")));
            frigthenedGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/ghost/scaredGhost1.png")));

            for (int i = 0; i < 4; i++) {
                this.imgPath = String.format("resources/%sGhost/%d.png", ghostColor, i);
                ghostImgs.add(ImageIO.read(this.getClass().getResource(imgPath)));
            }

        } catch (IOException e) {
            System.out.println("Ghost pic could not be loaded: " + e.getMessage());
        }
        currentImg = ghostImgs.get(0);
    }

    protected void setDirection() {

        if (inHorizontalGrid() && inVerticalGrid()) {
            pickDirection = true;
        }
        while (pickDirection) {
            direction = getState();
            pickDirection = false;
        }
    }

    protected void moveCharacter() {

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
                if (x - Maze.INSTANCE.gridWidth < 0) {
                    x = (Maze.INSTANCE.width);

                }

                x -= moveDistance;
                currentImg = ghostImgs.get(2);

                break;

            case "right":
                if (x + Maze.INSTANCE.gridWidth >= Maze.INSTANCE.width) {
                    x = 0;

                }

                x += moveDistance;
                currentImg = ghostImgs.get(3);

                break;
            }
        }
    }

    private String getState() {

        if (direction != null) {
            if (direction.equalsIgnoreCase("left")) {
                if (x - moveDistance < 0)
                    x = Maze.INSTANCE.width;
            }

            if (direction.equalsIgnoreCase("right")) {
                if (x + Maze.INSTANCE.gridWidth + moveDistance > Maze.INSTANCE.width)
                    x = 0;
            }
        }

        if (currentState.equalsIgnoreCase("wakeup")) {
            return iWakeUpBehaviour.awokenBehaviour(x, y);

        } else if (currentState.equalsIgnoreCase("chase")) {
            return iChaseBehaviour.chase(x, y);

        } else if (currentState.equalsIgnoreCase("scatter")) {
            return iScatterBehaviour.scatter(x, y);

        } else if (currentState.equalsIgnoreCase("fright")) {
            frigthened = true;
            return iFrightenedBehaviour.FrightenedBehaviour(x, y);
        } else {
            return "";
        }
    }

    public void draw(Graphics g) {

        if (!frigthened) {

            g.drawImage(currentImg, x, y, cSize, cSize, null);
        } else {
            animation = !animation;
            g.drawImage(animation ? frigthenedGhostImgs.get(0) : frigthenedGhostImgs.get(1), x, y, cSize, cSize, null);

        }
    }

    @Override
    public void updateState(String newState) {
        if (frigthened) {
            frigthened = false;
        }
        currentState = newState;
        System.out.println("current state is : " + currentState);

    }

}
