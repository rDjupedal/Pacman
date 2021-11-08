import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;

/**
 * The Concreten Ghost class. Handles all ghost painting and movement.
 * Implements StateObeserver to observe state changes and Runnable to enable
 * multithreading.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */
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
    private String previousMove = "";

    private String direction;

    private ArrayList<BufferedImage> frigthenedGhostImgs = new ArrayList<BufferedImage>();
    private BufferedImage currentImg;
    private String currentState = "wakeup";

    /**
     * Constructor setting the start positions for the ghost and also determininig
     * which ghost it is. GhostColor will determine, both which scatterCorner to use
     * and sprite to load.
     * 
     * @param x     Starting position on the X axis
     * @param y     Starting position on the Y axis.
     * @param color Color the ghost is associated with.
     */
    public Ghost(int x, int y, String color) {
        // Sets starting position
        this.x = x;
        this.y = y;
        // Sets ghostColor
        this.ghostColor = color.toLowerCase();
        // creates new Frightened behaviour
        iFrightenedBehaviour = new FrightenedWandering();
        // Creates new WakeUp Behaviour.
        iWakeUpBehaviour = new WakeUpBehaviour();

        // Depending on which color the ghost has, create a new Scattere Behaviour and a
        // specific Chase Behaviour.
        switch (ghostColor) {
        case "red":
            iChaseBehaviour = new ChaseAggressive();
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

        // Loading the pictures needed for the ghost.
        try {

            // Fritghtened pictures
            frigthenedGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/ghost/scaredGhost0.png")));
            frigthenedGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/ghost/scaredGhost1.png")));

            // Regular Ghost pictures.
            for (int i = 0; i < 4; i++) {
                this.imgPath = String.format("resources/%sGhost/%d.png", ghostColor, i);
                charImages.add(ImageIO.read(this.getClass().getResource(imgPath)));
            }

        } catch (IOException e) {
            System.out.println("Ghost pic could not be loaded: " + e.getMessage());
        }
        // Set the starting picture.
        currentImg = charImages.get(0);
    }

    /**
     * When a ghost is eaten by Pacman, Return the ghost to the Ghost livingroom.
     */
    protected void died() {
        // Send ghost back to cage when he dies
        setPos(15 * Maze.INSTANCE.gridWidth, 15 * Maze.INSTANCE.gridHeight);
    }

    /**
     * Sets the direction of the next move. If the ghost is inside a grid, proceed
     * to get the next direction.
     */
    protected void setDirection() {

        if (withinBoundary() && inHorizontalGrid() && inVerticalGrid()) {
            pickDirection = true;
        }
        while (pickDirection) {
            direction = getDirection();
            pickDirection = false;
        }
    }

    /**
     * Getter of the next move to be done. Depending on which state the ghost is in,
     * sends its own position and an array of possible moves to the correct movement
     * Behaviour.
     * 
     * @return the next direction.
     */
    private synchronized String getDirection() {

        // Checks current state and returns the direction Ghost should go based on which
        // state is active.
        switch (currentState) {
        case "wakeup":
            return iWakeUpBehaviour.awokenBehaviour(x, y, getPossibleMoves());
        case "chase":
            return iChaseBehaviour.chase(x, y, getPossibleMoves());
        case "scatter":
            return iScatterBehaviour.scatter(x, y, getPossibleMoves());
        case "fright":
            frigthened = true;
            return iFrightenedBehaviour.FrightenedBehaviour(x, y, getPossibleMoves());

        default:
            return "";
        }

    }

    /**
     * Checks which moves are possible based on the coordinate of the ghost.
     * 
     * @return array of possible moves.
     */
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

    /**
     * Moves the Ghost by adding or removing a "movedistance" unit from the x or y,
     * 
     */
    protected synchronized void moveCharacter() {

        if (!pickDirection) {
            switch (direction) {

            case "up":

                y -= moveDistance;
                currentImg = charImages.get(0);

                break;

            case "down":
                y += moveDistance;
                currentImg = charImages.get(1);
                break;

            case "left":
                if (x - moveDistance < 0) {
                    x = (Maze.INSTANCE.width - moveDistance);
                }

                x -= moveDistance;
                currentImg = charImages.get(2);

                break;

            case "right":
                if (x + cSize + moveDistance >= Maze.INSTANCE.width) {
                    x = 0;
                }

                x += moveDistance;
                currentImg = charImages.get(3);

                break;
            }

            // Checks if the ghost is inside the tunnel. Changes the x value base on which
            // side of the tunnel it is going through.
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

            previousMove = direction;
        }
    }

    /**
     * Draws the ghost on the panel. If the ghost are frigthened, adds an animation
     * to toggle between two colours.
     * 
     * @param g graphics object
     */
    public void draw(Graphics g) {

        if (!frigthened) {

            g.drawImage(currentImg, x, y, cSize, cSize, null);
        } else {
            animation = !animation;
            g.drawImage(animation ? frigthenedGhostImgs.get(0) : frigthenedGhostImgs.get(1), x, y, cSize, cSize, null);

        }
    }

    /**
     * The overriden updateState which is run whenever the gameEngine tells the
     * ghosts to change state.
     */
    @Override
    public void updateState(String newState) {
        if (frigthened) {
            frigthened = false;
        }
        currentState = newState;

    }

    /**
     * A simple part of the Producer Consumer implementation where the ghosts picks
     * a random message and returns it.
     * 
     * @return a random String message.
     */
    private String getRandomMsg() {

        String[] messages = { "I will catch you, PacMan!", "You will never see the light of day, Pacman!! ",
                "Slow down, you pesky, food eating bastard!", "I will hunt you down Pacman! ",
                "STOP! LET ME CATCH YOU!! ", "PACMAN! GET BACK HERE! ",
                " Please stop eating our food, my children will starve!" };

        int randIdx = (int) (Math.random() * messages.length);

        return messages[randIdx];
    }

    /**
     * The overridden run method which is called by GameEngine whenever the game
     * does a tick. Calls the template method of LivingCharacter (to make a move)
     * and also sends a message to PacMan (Based on some random intervall) and
     * recieves a message from pacman if there is one avaliable.
     */
    @Override
    public void run() {

        // Calls LivingCharacter ::doMove()
        doMove();

        // Part of the producer/Consumer problem. Sends a message to pacman.
        int sendMsgChance = (int) (Math.random() * 3000);

        if (sendMsgChance == 3) {
            String ghostMsg = String.format("%s ghost says: %s", ghostColor, getRandomMsg());
            PostMaster.getPostMaster().sendGhostMessage(ghostMsg);
        }

        // If there is a message from PacMan, retrieves it.
        if (PostMaster.getPostMaster().ghostHasMail()) {
            String pacManMsg = PostMaster.getPostMaster().recievePacManMsg();
            System.out.println(pacManMsg);
        }

    }

}
