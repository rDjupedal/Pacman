import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameLevel extends JComponent {
    int level;
    PacPanel pacpanel;
    //byte[] levelBytes;
    //List<String> levelMap;
    //List<Byte> levelBytes;
    byte[] readLevel;
    BufferedImage wall, space;

    public GameLevel(PacPanel pacpanel) {
        this.level = pacpanel.level;
        this.pacpanel = pacpanel;
        readFromFile();
        createGraphics();
    }

    private void readFromFile() {

        Path path = Paths.get("PacMan/src/resources/level" + level);
        System.out.println("opening file " + path.toString() + "...");

        try {
            //levelMap = Files.readAllLines(path);
            readLevel = Files.readAllBytes(path);

        } catch (IOException e) {
            System.out.println("error opening file ");
            //e.printStackTrace();
        }

        /*
        System.out.println(readLevel.length);
        List<?> test =
                Stream.of(readLevel).collect(Collectors.toList());
         */

    }

    private void createGraphics() {

        // create walls & space
        try {
            wall = ImageIO.read(new File("PacMan/src/resources/wall.jpg"));
            space = ImageIO.read(new File("PacMan/src/resources/space.jpg"));
        } catch (IOException e) {
            System.out.println("error loading image");
        }
    }


    protected void paintComponent(Graphics g) {

        // Calculate grid size
        double gridWidth = pacpanel.width / 30;
        double gridHeight = pacpanel.height / 30;

        // Iterate over map level array
        System.out.println("GameLevel::paintComponent called");
    }


    /*
    protected void drawMap(Graphics g) {

        // Calculate grid size
        double gridWidth = pacpanel.width / 30;
        double gridHeight = pacpanel.height / 30;

        // Iterate over map level array
        System.out.println("GameLevel::paintComponent called");
    }

     */


}
