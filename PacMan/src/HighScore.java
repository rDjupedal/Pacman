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
    }

    /**
     * Writes new highscore to file
     * @param name the name
     * @param score the score
     */
    protected void writeHighScore(String name, int score) {
        try {
            String s = name + "\n" + score;
            byte[] strToBytes = s.getBytes(StandardCharsets.UTF_8);
            Files.write(path, strToBytes);
        } catch (IOException e) {
            System.out.println("Error writing to file " + path);
        }

    }

}
