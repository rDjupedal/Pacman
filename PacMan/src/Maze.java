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


public final class Maze extends JComponent {
    protected static final Maze INSTANCE = new Maze();
    int level;
    int gridWidth, gridHeight;
    int width, height;
    byte[] readLevel;
    BufferedImage wall, space, food, candy, door;
    ArrayList<MazeBrick> mazeBricks = new ArrayList<MazeBrick>();

    /**
     * Empty private constructor, Singleton
     */
    private Maze() {}

    protected ArrayList<MazeBrick> getNeighbourBricks(Dimension position) {
        ArrayList<MazeBrick> neighbours = new ArrayList<MazeBrick>();
        return neighbours;
    }

    /**
     * Returns brick at point
     * @param x x-coord for point
     * @param y y-coord for point
     * @return brick at position
     */
    protected MazeBrick getBrick(int x, int y) {

        for (MazeBrick brick : mazeBricks) {
            if ( brick.getBrickRectangle().contains(x, y) ) {
                return brick;
            }
        }

        System.out.println("Could not find a brick at position " + x + ", " + y);
        return null;
    }

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

    private void readFromFile() {

        Path path = Paths.get("PacMan/src/resources/level" + level);
        System.out.println("opening file " + path.toString() + "...");

        try {
            readLevel = Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println("error opening file ");
            // e.printStackTrace();
        }

        /*
         * System.out.println(readLevel.length); List<?> test =
         * Stream.of(readLevel).collect(Collectors.toList());
         */

        System.out.println("Read " + readLevel.length + " bytes from Maze file.");

    }

    private void createGraphics() {

        // create walls & spaces
        // todo: move to separate class
        try {
            wall = ImageIO.read(new File("PacMan/src/resources/wall.jpg"));
            space = ImageIO.read(new File("PacMan/src/resources/space.jpg"));
            food = ImageIO.read(new File("PacMan/src/resources/food.jpg"));
            candy = ImageIO.read(new File("PacMan/src/resources/candy.jpg"));
            door = ImageIO.read(new File("PacMan/src/resources/door.jpg"));
        } catch (IOException e) {
            System.out.println("error loading image");
        }
    }

    private void createMazeBricks() {
        int curX = 0;
        int curY = 0;

        for (int i = 0; i < readLevel.length; i++) {

            MazeBrick tempMazeBrick = null;

            if (readLevel[i] != 10) {   //ignore new line characters
                switch(readLevel[i]) {
                    //todo: use a factory instead and pass the byte as argument

                    case 67: // (C)andy
                        tempMazeBrick = new MazeBrick("candy", candy, curX, curY, gridWidth, gridHeight);
                        break;
                    case 68: // (D)oor
                        tempMazeBrick = new MazeBrick("door", door, curX, curY, gridWidth, gridHeight);
                        break;
                    case 70: // (F)ood
                        tempMazeBrick = new MazeBrick("food", food, curX, curY, gridWidth, gridHeight);
                        break;
                    case 87: // (W)all
                        tempMazeBrick = new MazeBrick("wall", wall, curX, curY, gridWidth, gridHeight);
                        break;

                    case 83: // (S)pace
                        tempMazeBrick = new MazeBrick("space", space, curX, curY, gridWidth, gridHeight);
                        break;

                    default:
                        System.out.println("Found unrecognized character at: " + i + ":  " + readLevel[i]);
                        break;
                }

                mazeBricks.add(tempMazeBrick);

                if ( curX + gridWidth >= width ) {
                    curY += gridHeight;
                    curX = 0;
                } else {
                    curX += gridWidth;
                }
            }

        }
    }


    protected void drawMap(Graphics g) {
        for (MazeBrick brick : mazeBricks) { brick.draw(g); }
        drawDebugGrid(g);

    }

    private void drawDebugGrid(Graphics g) {
        //debug grid
        for (int x = 0; x <= width; x += gridWidth) {
            g.drawLine(x,0,x, height);
        }
        for (int y = 0; y <= height; y += gridHeight) {
            g.drawLine(0,y, width, y);
        }
    }

}
