import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Maze2 extends JComponent {
    protected static final Maze2 INSTANCE = new Maze2();
    int level;
    int gridWidth, gridHeight;
    int width, height;
    byte[] readLevel;
    BufferedImage wall, space;
    ArrayList<MazeBrick> mazeBricks = new ArrayList<MazeBrick>();

    /**
     * Empty private constructor, Singleton
     */
    private Maze2() {}

    protected ArrayList<MazeBrick> getNeighbourBricks(Dimension position) {
        ArrayList<MazeBrick> neighbours = new ArrayList<MazeBrick>();
        return neighbours;
    }

    protected MazeBrick getBrick(int x, int y) {
        // Find which brick is at current position

        for (MazeBrick brick : mazeBricks) {
            if ( brick.getBrickRectangle().contains(x, y) ) {
                //System.out.println("Found a brick a position " + brick.x + ", " + brick.y);
                return brick;
            }
        }

        System.out.println("Couldnt find a brick at position " + x + ", " + y);
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
        } catch (IOException e) {
            System.out.println("error loading image");
        }
    }

    private void createMazeBricks() {
        int curX = 0;
        int curY = 0;

        for (int i = 0; i < readLevel.length; i++) {
            System.out.println(i);
            MazeBrick tempMazeBrick = null;

            if (readLevel[i] != 10) {   //ignore new line characters
                switch(readLevel[i]) {
                    //todo: use a factory instead and pass the byte as argument
                    case 87: //wall
                        tempMazeBrick = new MazeBrick("wall", wall, curX, curY, gridWidth, gridHeight);
                        break;

                    case 83: // space
                        tempMazeBrick = new MazeBrick("space", space, curX, curY, gridWidth, gridHeight);
                        break;

                    default:
                        System.out.println(readLevel[i]);
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
