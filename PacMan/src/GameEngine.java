import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    StateSetter stateSetter = new StateSetter();

    private GameEngine() {
    }

    /*
     * Called at every tick
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
            if (ghost.getCollisionRectangle().intersects(pacman.getCollisionRectangle())) {
                // Remove ghost from Array. Work on checking the details and spawning another
                // one.
                collisionDetected(ghost);
            }
        }
    }

    private void collisionDetected(Ghost ghost) {

        if (!highOnCandy) {
            lives--;
            // Reset the position of the characters
            resetCharacterPositions();

            // stops the EDT timer
            isRunning = false;

            if (lives < 1)
                gameOver();

        } else {
            score += 500;
            // Kill the ghost
            ghost.died();

        }
    }

    private void gameOver() {
        isGameOver = true;
    }

    private void finishLevel() {
        GameEngine.INSTANCE.isRunning = false;
        resetCharacterPositions();
        // todo level..
        // level++;
        createMaze();
    }

    /**
     * Called only on program start
     */
    protected void initGame() {
        createPacman();
        createMaze();
        createGhosts();
        stateSetter.setWakeUp();
    }

    /**
     * Called after GameOver
     */
    protected void newGame() {

        createMaze();

        lives = 3;
        score = 0;

        // MazePanel checks this and repaints the whole screen
        clearScreen = true;
    }

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

    protected void ateFood() {
        score += 10;
    }

    protected int getScore() {
        return score;
    }

    protected void createMaze() {
        Maze.INSTANCE.startMaze(level, gameSize, gridSize);
    }

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

    protected void createPacman() {
        AbstractFactory pacManFactory = FactoryProducer.getFactory(true);
        pacman = pacManFactory.getCharacter("pacman", pacmanStartX, pacmanStartY);
    }

    protected void createGhosts() {
        AbstractFactory ghostFactory = FactoryProducer.getFactory(false);
        // todo: replace harcoded startpos with Maze.INSTANCE.getPacmanX() &
        // Maze.INSTANCE.getPacmanY()
        ghosts.add(ghostFactory.getCharacter("ghost", 330, 390, "red"));
        ghosts.add(ghostFactory.getCharacter("ghost", 330, 450, "blue"));
        ghosts.add(ghostFactory.getCharacter("ghost", 480, 390, "yellow"));
        ghosts.add(ghostFactory.getCharacter("ghost", 480, 450, "pink"));
        ghosts.forEach(ghost -> {
            stateSetter.addObserver(ghost);

        });
    }

    protected Pacman getPacman() {
        return pacman;
    }

    protected void setSizes(Dimension gameSize, Dimension gridSize) {
        this.gameSize = gameSize;
        this.gridSize = gridSize;
    }

    protected ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    protected int getLives() {
        return lives;
    }

    protected void setHighOnCandy() {
        if (highOnCandy) {
            highOnCandyMs = 800;
        } else {

            highOnCandy = true;
            stateSetter.setFright();
        }
    }

    private void endHighOnCandy() {
        highOnCandy = false;
        stateSetter.setPreviousState();
        highOnCandyMs = 800;
    }
}
