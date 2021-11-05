import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Singleton class for handling the game
 * @author Rasmsu Djupedal, Tobias Liljeblad
 */
public class GameEngine {

    protected static final GameEngine INSTANCE = new GameEngine();
    private int level = 1, score = 0, lives = 3;
    private int chaseCounter = 1300;
    private int wakeupCounter = 200;
    private Pacman pacman;
    private boolean highOnCandy = false;
    private int highOnCandyMs = 800;
    private int pacmanStartX = 400, pacmanStartY = 690;
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private Dimension gameSize, gridSize;
    protected boolean isRunning = false;
    protected boolean isGameOver = false;
    private boolean clearScreen = false;
    private ExecutorService executorService = Executors.newFixedThreadPool(6);
    private Sound sound = new Sound();
    private StateSetter stateSetter = new StateSetter();

    /**
     * Private empty constructor as it is a singleton
     */
    private GameEngine() {
    }

    /**
     * Called at every tick, updates the game
     */
    protected void updateGame() {

        if (stateSetter.isWakeUp()) {
            wakeupCounter--;
            if (wakeupCounter == 0) {
                stateSetter.setChase();
                wakeupCounter = 150;
                Maze.INSTANCE.closeDoor();
            }
        } else {

            if (!stateSetter.isFright()) {
                switch (chaseCounter) {
                case 1300:
                    stateSetter.setChase();
                    break;
                case 200:
                    stateSetter.setScatter();
                    break;

                case 0:
                    chaseCounter = 1301;
                    break;
                default:
                    break;
                }

                chaseCounter -= 1;
            }

            if (highOnCandy) {
                highOnCandyMs -= 1;
                if (highOnCandyMs < 1) {
                    endHighOnCandy();
                }
            }
        }

        // Check if game is finished (if food is still left)
        if (Maze.INSTANCE.getFoodLeft() < 1) {
            finishLevel();
        }

        // Move the characters & check for collisions
        executorService.execute(pacman);
        for (Ghost ghost : ghosts) {
            executorService.execute(ghost);

            // Check if a ghost collide with Pacman
            if (ghost.getCollisionRectangle().intersects(pacman.getCollisionRectangle())) {
                collisionDetected(ghost);
            }
        }
    }

    /**
     * Called when a ghost collide with Pacman
     * @param ghost the ghost that collides with Pacman
     */
    private void collisionDetected(Ghost ghost) {

        if (!highOnCandy) {
            // Ghost kills Pacman :(
            lives--;
            sound.play("die");

            // Reset the position of the characters
            resetCharacterPositions();

            // stops the EDT timer
            isRunning = false;

            if (lives < 1)
                gameOver();

        } else {
            // Pacman kills the ghost :)
            score += 500;
            sound.play("kill");
            ghost.died();

        }
    }

    /**
     * Called when GameOver
     */
    private void gameOver() {
        sound.play("gameover");
        isGameOver = true;
    }

    /**
     * Called when maze is finished (all food eaten)
     * Resets the maze and characters positions.
     */
    private void finishLevel() {
        GameEngine.INSTANCE.isRunning = false;
        resetCharacterPositions();
        endHighOnCandy();
        sound.play("mazefinished");
        createMaze();
    }

    /**
     * Initializes the game.
     * Called only on program start
     */
    protected void initGame() {
        createPacman();
        createMaze();
        createGhosts();
        stateSetter.setWakeUp();
    }

    /**
     * Creates a new maze, resets score
     * Called after GameOver
     */
    protected void newGame() {

        createMaze();

        lives = 3;
        score = 0;

        // MazePanel checks this and repaints the whole screen
        clearScreen = true;
    }

    /**
     * Used by MazePanel in order to know if the whole screen should be repainted
     * @return whether the screen should be repainted
     */
    protected boolean getClearScreen() {
        if (clearScreen) {
            clearScreen = false;
            return true;
        }
        return false;
    }

    /**
     * Called after Pacman died / new game
     */
    protected void startGame() {
        isRunning = true;
        isGameOver = false;
    }

    /**
     * Called every time Pacman eats food
     */
    protected void ateFood() {
        score += 10;
        sound.play("eat");
    }

    /**
     * Returns the current score
     * @return the score
     */
    protected int getScore() {
        return score;
    }

    /**
     * Orders Maze instance to create a new maze
     */
    protected void createMaze() {
        Maze.INSTANCE.startMaze(level, gameSize, gridSize);
    }

    /**
     * Resets the position for Pacman and the ghosts
     */
    protected void resetCharacterPositions() {
        pacman.x = pacmanStartX;
        pacman.y = pacmanStartY;
        ghosts.get(0).setPos(330, 390);
        ghosts.get(1).setPos(330, 450);
        ghosts.get(2).setPos(480, 390);
        ghosts.get(3).setPos(480, 450);

        pacman.setInitImage();
        clearScreen = true;
        stateSetter.setWakeUp();
    }

    /**
     * Creates Pacman and sets his starting position with an abstract factory
     */
    protected void createPacman() {
        AbstractFactory pacManFactory = FactoryProducer.getFactory(true);
        pacman = pacManFactory.getCharacter("pacman", pacmanStartX, pacmanStartY);
    }

    /**
     * Creates the ghosts and set their starting positions with an abstract factory
     */
    protected void createGhosts() {
        AbstractFactory ghostFactory = FactoryProducer.getFactory(false);

        ghosts.add(ghostFactory.getCharacter("ghost", 330, 390, "red"));
        ghosts.add(ghostFactory.getCharacter("ghost", 330, 450, "blue"));
        ghosts.add(ghostFactory.getCharacter("ghost", 480, 390, "yellow"));
        ghosts.add(ghostFactory.getCharacter("ghost", 480, 450, "pink"));
        ghosts.forEach(ghost -> {
            stateSetter.addObserver(ghost);
        });
    }

    /**
     * Returns the Pacman instance
     * @return the Pacman instance
     */
    protected Pacman getPacman() {
        return pacman;
    }

    /**
     * Tells GameEngine about the size of the playable area and the gridsize.
     * Called from PacmanFrame
     * @param gameSize dimension of the playable area
     * @param gridSize dimension of the grid
     */
    protected void setSizes(Dimension gameSize, Dimension gridSize) {
        this.gameSize = gameSize;
        this.gridSize = gridSize;
    }

    /**
     * Returns all ghost instances
     * @return the ghosts
     */
    protected ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    /**
     * Returns the number of lives left of Pacman
     * @return number of lives
     */
    protected int getLives() {
        return lives;
    }

    /**
     * Called when Pacman eats a candy. Sets a counter and makes the ghosts scared!
     */
    protected void setHighOnCandy() {
        sound.play("candy");
        if (highOnCandy) {
            highOnCandyMs = 800;
        } else {

            highOnCandy = true;
            stateSetter.setFright();
        }
    }

    /**
     * Called when Pacman shouldn't be high on candy any longer
     */
    private void endHighOnCandy() {
        highOnCandy = false;
        highOnCandyMs = 800;
        stateSetter.setWakeUp();
    }
}
