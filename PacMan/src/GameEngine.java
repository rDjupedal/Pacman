import java.awt.*;
import java.util.ArrayList;

public class GameEngine {

    protected static final GameEngine INSTANCE = new GameEngine();
    private int level = 1;
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
        if (Maze.INSTANCE.getFoodLeft() < 1) {
            System.out.println("game finished!!");
            GameEngine.INSTANCE.isRunning = false;
        }

        // Move the characters
        pacman.doMove();
        for (Ghost monster : ghosts) {
            monster.doMove();
        }

    }

    protected void initilizeGame() {
        System.out.println("Game initialized");
        createMaze();
        createGhosts();
        createGhosts();
    }

    protected void startGame() {
        System.out.println("Game started .");
        isRunning = true;
    }


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
