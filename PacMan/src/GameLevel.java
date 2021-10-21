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

public class GameLevel extends JComponent {
    int level;
    PacPanel pacpanel;
    //byte[] levelBytes;
    //List<String> levelMap;
    //List<Byte> levelBytes;
    int gridWidth;
    int gridHeight;
    byte[] readLevel;
    BufferedImage wall, space;

    public GameLevel(PacPanel pacpanel) {
        this.level = pacpanel.level;
        this.pacpanel = pacpanel;

        // Calculate grid size
        gridWidth = pacpanel.width / 30;
        gridHeight = pacpanel.height / 30;

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
        List<> test =
                Stream.of(readLevel).collect(Collectors.toList());

         */

        // todo: filter out all new lines


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

/*
    protected void paintComponent(Graphics g) {

        // Calculate grid size
        double gridWidth = pacpanel.width / 30;
        double gridHeight = pacpanel.height / 30;

        // Iterate over map level array
        System.out.println("GameLevel::paintComponent called");
        g.drawImage(wall,200,200,this);
        g.fillRect(300,300,100,100);
    }
 */

    protected void drawMap(Graphics g) {

        // Iterate over map level array
        for (int i = 0; i < readLevel.length; i++) {
            int spriteX = (i % 30) * (int) gridWidth;
            int spriteY = (i / 30) * (int) gridHeight;
            System.out.print(readLevel[i] + " ");


            //if (readLevel[i] == 87) g.drawImage(wall, spriteX, spriteY, this);
            if (readLevel[i] == 87) g.drawImage(wall.getScaledInstance(gridWidth, gridHeight, 2), spriteX, spriteY, this);

            //if (readLevel[i] == 83) g.drawImage(space, spriteX, spriteY, this);
            if (readLevel[i] == 83) g.drawImage(space.getScaledInstance(gridWidth, gridHeight, 2), spriteX, spriteY, this);
        }



        //System.out.println("GameLevel::paintComponent called");
        //g.drawImage(wall,200,200,this);
        //g.fillRect(300,300,100,100);
    }


}
