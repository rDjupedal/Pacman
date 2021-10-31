import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HighScore {
    String readScore;
    String name = "";
    int highScore = 0;
    private Path path;

    protected static final HighScore INSTANCE = new HighScore();

    protected String getHighScoreName() { return name; }
    protected int getHighScore() { return highScore; }

    /**
     * Read highscore from file
     */
    protected HighScore() {
        path = Paths.get("PacMan/src/resources/highscore");
        try {
            readScore = Files.readString(path);
        } catch (IOException e) {
            System.out.println("Error opening file " + path.toString());
             writeHighScore("" ,0);
        }

        name = readScore.split("\n")[0];
        highScore = Integer.parseInt(readScore.split("\n")[1]);

        System.out.println("parsed " + name + " " + highScore);
    }

    /**
     * Writes new highscore to file
     * @param name the name
     * @param highScore the highscore
     */
    protected void writeHighScore(String name, int highScore) {
        try {
            this.name = name;
            this.highScore = highScore;
            String s = name + "\n" + highScore;
            byte[] strToBytes = s.getBytes(StandardCharsets.UTF_8);
            Files.write(path, strToBytes);
        } catch (IOException e) {
            System.out.println("Error writing to file " + path);
        }

    }

}
