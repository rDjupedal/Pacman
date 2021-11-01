import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;
import java.sql.Date;

public class Ghost extends LivingCharacter implements StateObserver, Runnable {
    private IChaseBehaviour iChaseBehaviour;
    private IScatterBehaviour iScatterBehaviour;
    private IFrightenedBehaviour iFrightenedBehaviour;
    private IWakeUpBehaviour iWakeUpBehaviour;

    private String ghostColor;
    private String imgPath;
    private Boolean pickDirection = true;
    private boolean frigthened = false;
    private boolean animation = false;
    private boolean isLogging = false;
    private String previousMove = "";

    private final int cSize = 30;
    private final int moveDistance = 2;
    private String direction;
    private ArrayList<BufferedImage> ghostImgs = new ArrayList<BufferedImage>();
    private ArrayList<BufferedImage> frigthenedGhostImgs = new ArrayList<BufferedImage>();
    private BufferedImage currentImg;
    private String currentState = "wakeup";

    public Ghost(int x, int y, String color) {
        this.x = x;
        this.y = y;
        iFrightenedBehaviour = new FrightenedWandering();
        iWakeUpBehaviour = new WakeUpBehaviour(this);

        this.ghostColor = color.toLowerCase();
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
        isLogging = true;
    }

    protected void setDirection() {

        if (inHorizontalGrid() && inVerticalGrid()) {
            pickDirection = true;
        }
        while (pickDirection) {
            direction = getDirection();
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

            previousMove = direction;
        }
    }

    private String getDirection() {
        GhostLogger.getLogger().info(
                "Thread : " + Thread.currentThread().getName() + " is working on this call for ghost: " + ghostColor);

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
            return iWakeUpBehaviour.awokenBehaviour(x, y, getPossibleMoves());

        } else if (currentState.equalsIgnoreCase("chase")) {
            return iChaseBehaviour.chase(x, y, getPossibleMoves());

        } else if (currentState.equalsIgnoreCase("scatter")) {
            return iScatterBehaviour.scatter(x, y, getPossibleMoves());

        } else if (currentState.equalsIgnoreCase("fright")) {
            frigthened = true;
            return iFrightenedBehaviour.FrightenedBehaviour(x, y, getPossibleMoves());
        } else {
            return "";
        }
    }

    private ArrayList<String> getPossibleMoves() {
        ArrayList<String> possibleMovesArray = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
        if (previousMove.equalsIgnoreCase("left") || Maze.INSTANCE.getBrick(x + Maze.INSTANCE.gridWidth, y).isWall()) {
            possibleMovesArray.remove("right");

        }
        if (previousMove.equalsIgnoreCase("right") || Maze.INSTANCE.getBrick(x - Maze.INSTANCE.gridWidth, y).isWall()) {
            possibleMovesArray.remove("left");

        }
        if (Maze.INSTANCE.getBrick(x, y + Maze.INSTANCE.gridHeight).isWall() | previousMove.equalsIgnoreCase("up")) {
            possibleMovesArray.remove("down");

        }
        if (Maze.INSTANCE.getBrick(x, y - Maze.INSTANCE.gridHeight).isWall() | previousMove.equalsIgnoreCase("down")) {
            possibleMovesArray.remove("up");

        }

        return possibleMovesArray;
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

    @Override
    public void run() {

        setDirection();
        moveCharacter();

        // while (isLogging) {

        // try {
        // String logMessage = String.format("%s ghost is at %d, %d. Written by Thread:
        // %s at %s", ghostColor, x,
        // y, Thread.currentThread().getName(), java.time.LocalTime.now());

        // GhostLogger.getLogger().info(logMessage);

        // Thread.sleep(1000);

        // } catch (InterruptedException e) {
        // System.out.println("Thread was interuppted: " + e.getMessage());
        // }

        // }

    }

}
