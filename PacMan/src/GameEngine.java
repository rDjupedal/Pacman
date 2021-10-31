import java.awt.*;
import java.util.ArrayList;

public class GameEngine {

    protected static final GameEngine INSTANCE = new GameEngine();
    private int level = 1, score = 0, lives = 3;
    private Pacman pacman;
    private int pacmanStartX = 400, pacmanStartY = 690;
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private Dimension gameSize, gridSize;
    protected boolean isRunning = false;
    protected boolean isGameOver = false;
    private boolean clearScreen = false;

    private GameEngine() {}

    /*
    Called at every tick
     */
    protected void updateGame() {

        // Check if game is finished (if food is still left)
        if (Maze.INSTANCE.getFoodLeft() < 1) { finishLevel(); }

        // Move the characters & check for collisions
        pacman.doMove();
        for (Ghost ghost : ghosts) {
            ghost.doMove();
            if ( ghost.getCollisionRectangle().intersects(pacman.getCollisionRectangle()) ) {
                collisionDetected();
            }
        }
    }

    private void collisionDetected() {
        if ( pacman.highOnCandyMs < 1) {
            lives--;
            // Reset the position of the characters
            resetCharacterPositions();

            // stops the EDT timer
            isRunning = false;

            if (lives < 1) gameOver();

        } else {
            score += 500;
            // todo: respawn the ghost into its cage!
        }
    }


    private void gameOver(){
        System.out.println("Game over");
        isGameOver = true;

    }


    private void finishLevel() {
        System.out.println("game finished!!");
        GameEngine.INSTANCE.isRunning = false;
        resetCharacterPositions();
        //todo level..
        //level++;
        createMaze();
    }

    /**
     * Called only on program start
     */
    protected void initGame() {
        createPacman();
        createMaze();
        createGhosts();
    }

    /**
     * Called after GameOver
     */
    protected void newGame() {
        // Old Maze handled by garbage collector..
        createMaze();
        lives = 3;
        score = 0;

        // MazePanel checks this and repaints the whole screen
        //clearScreen = true;
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

        // MazePanel checks this and repaints the whole screen
        clearScreen = true;
        System.out.println("screen cleared from StartGame()");

        isRunning = true;
        isGameOver = false;
    }

    protected void ateFood() { score += 10; }
    protected int getScore() { return score; }

    protected void createMaze() { Maze.INSTANCE.startMaze(level, gameSize, gridSize); }

    protected void resetCharacterPositions() {
        pacman.x = pacmanStartX;
        pacman.y = pacmanStartY;
        ghosts.get(0).setPos(330,390);
        ghosts.get(1).setPos(330,450);
        ghosts.get(2).setPos(480,390);
        ghosts.get(3).setPos(480,450);
    }

    protected void createPacman() {
        AbstractFactory pacManFactory = FactoryProducer.getFactory(true);
        //todo: replace harcoded startpos with Maze.INSTANCE.getPacmanX() & Maze.INSTANCE.getPacmanY()
        pacman = pacManFactory.getCharacter("pacman", pacmanStartX, pacmanStartY);
    }

    protected void createGhosts() {
        AbstractFactory ghostFactory = FactoryProducer.getFactory(false);
        //todo: replace harcoded startpos with Maze.INSTANCE.getPacmanX() & Maze.INSTANCE.getPacmanY()
        ghosts.add(ghostFactory.getCharacter("ghost", 330, 390, "red"));
        ghosts.add(ghostFactory.getCharacter("ghost", 330, 450, "blue"));
        ghosts.add(ghostFactory.getCharacter("ghost", 480, 390, "yellow"));
        ghosts.add(ghostFactory.getCharacter("ghost", 480, 450, "pink"));
    }

    protected Pacman getPacman() { return pacman; }

    protected void setSizes (Dimension gameSize,Dimension gridSize) {
        this.gameSize = gameSize;
        this.gridSize = gridSize;
    }

    protected ArrayList<Ghost> getGhosts() { return ghosts; }
    protected int getLives() {return lives; }

    protected void setScatter() {
        ghosts.forEach(ghost -> {
            ghost.setScatter();
        });
    }
}
