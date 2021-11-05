import javax.swing.*;
import java.awt.*;

/**
 * Entrypoint for the Pacman game.
 * @author Rasmus Djupedal, Tobias Liljeblad
 */
public class PacmanGame {
    final static int width = 840;
    final static int height = 930;

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            PacmanFrame engine = new PacmanFrame(width, height);
            engine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            engine.setTitle("Pacman 1.0");
            engine.pack();

        });

    }
}
