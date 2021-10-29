import org.ietf.jgss.GSSManager;

import java.awt.*;
import java.util.ArrayList;

public class GameEngine {

    protected static final GameEngine INSTANCE = new GameEngine();
    private int level = 1, score = 0, lives = 3;
    private Pacman pacman;
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private Dimension gameSize, gridSize;
    protected boolean isRunning = false;

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
                System.out.println("COLLISION DETECTED!");
                lives--;
                isRunning = false;
                if (lives < 0) gameOver();
                else {} // stop EDT timer

            }
        }
    }


    private void gameOver(){}


    private void finishGame() {
        System.out.println("game finished!!");
        GameEngine.INSTANCE.isRunning = false;
    }

    protected void initGame() {
        System.out.println("Game initialized");
        createMaze();
        createGhosts();
        createGhosts();
    }

    protected void startGame() {
        System.out.println("Game started .");
        isRunning = true;
    }

    protected void ateFood() { score += 100; }
    protected int getScore() { return score; }

    protected void createMaze() { Maze.INSTANCE.startMaze(level, gameSize, gridSize); }

    protected void createPacman() {
        AbstractFactory pacManFactory = FactoryProducer.getFactory(true);
        //todo: replace harcoded startpos with Maze.INSTANCE.getPacmanX() & Maze.INSTANCE.getPacmanY()
        pacman = pacManFactory.getCharacter("pacman", 400, 690);
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

    protected void setScatter() {
        ghosts.forEach(ghost -> {
            ghost.setScatter();
        });
    }


}
