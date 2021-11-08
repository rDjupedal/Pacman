import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class for handling the maze
 * @author Rasmus Djupedal, Tobias Liljeblad
 */
public final class Maze extends JComponent {
    protected static final Maze INSTANCE = new Maze();
    protected int level;
    protected int gridWidth, gridHeight;
    protected int width, height;
    private int foodLeft;
    private byte[] readLevel;
    protected BufferedImage wall, space, food, candy, door;
    private ArrayList<MazeBrick> mazeBricks = new ArrayList<>();
    private ArrayList<MazeBrick> doorBricks;

    /**
     * Private constructor as it is a Singleton
     */
    private Maze() {
    }

    /**
     * Returns brick at point
     *
     * @param x x-coord for point
     * @param y y-coord for point
     * @return brick at position
     */
    protected MazeBrick getBrick(int x, int y) {

        for (MazeBrick brick : mazeBricks) {
            if (brick.getBrickRectangle().contains(x, y)) {
                return brick;
            }
        }

        //System.out.println("Could not find a brick at position " + x + ", " + y);
        // Return whatever dummy brick that is not wall

        for (MazeBrick brick : mazeBricks) {
            if (!brick.isWall())
                return brick;
        }
        return null;
    }

    /**
     * Returns number of MazeBricks that are food
     * @return food left
     */
    protected int getFoodLeft() {
        return foodLeft;
    }

    /**
     * Updates a food counter when food is eaten
     */
    protected void ateFood() {
        foodLeft--;
    }

    /**
     * Sets up a new / resets maze
     * @param level current level
     * @param paneSize size of the maze
     * @param gridSize size of each grid
     */
    protected void startMaze(int level, Dimension paneSize, Dimension gridSize) {

        this.level = level;
        this.width = paneSize.width;
        this.height = paneSize.height;
        this.gridWidth = gridSize.width;
        this.gridHeight = gridSize.height;

        readFromFile();
        createGraphics();
        createMazeBricks();
    }

    /**
     * Reads maze from file
     */
    private void readFromFile() {

        Path path = Paths.get("PacMan/src/resources/maze/level" + level);

        try {
            readLevel = Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println("Error opening Maze file. ");
        }
    }

    /**
     * Reads maze iamges
     */
    private void createGraphics() {

        try {
            wall = ImageIO.read(new File("PacMan/src/resources/maze/wall.jpg"));
            space = ImageIO.read(new File("PacMan/src/resources/maze/space.jpg"));
            food = ImageIO.read(new File("PacMan/src/resources/maze/food.jpg"));
            candy = ImageIO.read(new File("PacMan/src/resources/maze/candy.jpg"));
            door = ImageIO.read(new File("PacMan/src/resources/maze/door.jpg"));
        } catch (IOException e) {
            System.out.println("Error loading image files..");
        }
    }

    /**
     * Sets up an array of MazeBricks in accordance with the Maze-file
     */
    private void createMazeBricks() {
        int curX = 0;
        int curY = 0;
        foodLeft = 0;

        // Return MazeBricks to pool for reuse
        mazeBricks.forEach((brick) -> MazeBrickPool.INSTANCE.returnBrickObject(brick));
        mazeBricks.clear();

        // Iterate over the data from the Maze file
        for (int i = 0; i < readLevel.length; i++) {

            if (readLevel[i] != 10) { // ignore new line characters

                // Get a MazeBrick from the Maze-pool
                MazeBrick tempMazeBrick = MazeBrickPool.INSTANCE.getBrickObject();
                boolean skip = false;

                // Set the properties of the MazeBrick
                switch (readLevel[i]) {

                case 67: // (C)andy
                    tempMazeBrick.setupBrick("candy", candy, curX, curY, gridWidth, gridHeight);
                    break;

                case 68: // (D)oor
                    tempMazeBrick.setupBrick("door", door, curX, curY, gridWidth, gridHeight);
                    break;

                case 70: // (F)ood
                    tempMazeBrick.setupBrick("food", food, curX, curY, gridWidth, gridHeight);
                    foodLeft++;
                    break;

                case 83: // (S)pace
                    tempMazeBrick.setupBrick("space", space, curX, curY, gridWidth, gridHeight);
                    break;

                case 87: // (W)all
                    tempMazeBrick.setupBrick("wall", wall, curX, curY, gridWidth, gridHeight);
                    break;

                default:
                    System.out.println("Found unrecognized character at: " + i + ":  " + readLevel[i]);
                    skip = true;
                    break;
                }

                // Add the MazeBrick to the array
                if (!skip) mazeBricks.add(tempMazeBrick);

                // Check if the horizontal line is finished
                if (curX + gridWidth >= width) {
                    curY += gridHeight;
                    curX = 0;
                } else {
                    curX += gridWidth;
                }
            }

        }

        // Create door arrayList
        doorBricks = mazeBricks.stream().filter(brick -> brick.getType().equals("door"))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Draws all the bricks on the screen
     * @param g
     */
    protected void drawMap(Graphics g) {
        for (MazeBrick brick : mazeBricks) {
            brick.draw(g);
        }
        // Uncomment in order to draw a grid
        //drawDebugGrid(g);
    }

    /**
     * Draw lines along the grids of the maze
     * @param g
     */
    private void drawDebugGrid(Graphics g) {
        for (int x = 0; x <= width; x += gridWidth) {
            g.drawLine(x, 0, x, height);
        }
        for (int y = 0; y <= height; y += gridHeight) {
            g.drawLine(0, y, width, y);
        }
    }

    /**
    Stops ghosts from running in / out of the cage
     */
    public void closeDoor() {
        doorBricks.forEach(z -> {
            z.changeBrick("wall");
        });
    }

    /**
     * Opens the door to the ghosts cage
     */
    public void openDoor() {
        doorBricks.forEach(brick -> {
            brick.changeBrick("door");
        });
    }
}
