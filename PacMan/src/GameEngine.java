import java.awt.*;
import java.util.ArrayList;

public class GameEngine {

    protected static final GameEngine INSTANCE = new GameEngine();
    private int level = 1, score = 14, lives = 3;
    private Pacman pacman;
    private int pacmanStartX = 400, pacmanStartY = 690;
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private Dimension gameSize, gridSize;
    protected boolean isRunning = false;
    protected boolean isGameOver = false;

    private GameEngine() {}

    /*
    Called at every tick
     */
    protected void updateGame() {

        // Check if game is finished (if food is still left)
        if (Maze.INSTANCE.getFoodLeft() < 1) { finishGame(); }

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

            // MazePanel checks this and repaints the whole screen
            pacman.died = true;

            // Reset the position of the characters
            resetCharacterPositions();

            // stops the EDT timer
            isRunning = false;

            if (lives < 1) gameOver();

        } else {
            System.out.println("Ghost dies");
            score += 500;
        }
    }


    private void gameOver(){
        System.out.println("Game over");
        isGameOver = true;
    }


    private void finishGame() {
        System.out.println("game finished!!");
        GameEngine.INSTANCE.isRunning = false;
    }

    protected void initGame() {
        System.out.println("Game initialized");
        createPacman();
        createMaze();
        createGhosts();
    }

    protected void startGame() {
        System.out.println("Game started");
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
