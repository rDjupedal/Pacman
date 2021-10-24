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
import java.util.stream.Stream;

public class Maze extends JComponent {
    int level;
    PacPanel pacpanel;
    final int gridWidth, gridHeight;
    final int width, height;
    byte[] readLevel;
    BufferedImage wall, space;
    ArrayList<MazeBrick> mazeBricks = new ArrayList<MazeBrick>();

    public Maze(PacPanel pacpanel) {
        this.level = pacpanel.level;
        // this.pacpanel = pacpanel;

        width = pacpanel.width;
        height = pacpanel.height;
        gridWidth = pacpanel.gridWidth;
        gridHeight = pacpanel.gridWidth;

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
            // System.out.println(i);
            MazeBrick tempMazeBrick = null;

            if (readLevel[i] != 10) { // not new line
                switch (readLevel[i]) {
                // todo: use a factory instead and pass the byte as argument
                case 87: // wall
                    tempMazeBrick = new MazeBrick(wall, curX, curY, gridWidth, gridHeight);
                    break;

                case 83: // space
                    tempMazeBrick = new MazeBrick(space, curX, curY, gridWidth, gridHeight);
                    break;

                default:
                    // System.out.println(readLevel[i]);
                    break;
                }

                mazeBricks.add(tempMazeBrick);

                if (curX + gridWidth >= width) {
                    curY += gridHeight;
                    curX = 0;
                } else {
                    curX += gridWidth;
                }
            }

        }
    }

    protected void drawMap(Graphics g) {
        for (MazeBrick brick : mazeBricks) {
            brick.draw(g);
        }

        /*
         * // Iterate over map level array for (int i = 0; i < readLevel.length; i++) {
         * int spriteX = (i % 30) * (int) gridWidth; int spriteY = (i / 30) * (int)
         * gridHeight; System.out.print(readLevel[i] + " ");
         * 
         * // if (readLevel[i] == 87) g.drawImage(wall, spriteX, spriteY, this); if
         * (readLevel[i] == 87) g.drawImage(wall.getScaledInstance(gridWidth,
         * gridHeight, 2), spriteX, spriteY, this);
         * 
         * // if (readLevel[i] == 83) g.drawImage(space, spriteX, spriteY, this); if
         * (readLevel[i] == 83) g.drawImage(space.getScaledInstance(gridWidth,
         * gridHeight, 2), spriteX, spriteY, this); }
         * 
         */

    }

}
