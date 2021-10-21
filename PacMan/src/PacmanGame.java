import javax.swing.*;
import java.awt.*;

public class PacmanGame {
    final static int width = 600;
    final static int height = 600;

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            JFrame pFrame = new JFrame();
            pFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pFrame.setSize(width, height);
            pFrame.setTitle("Pacman 1.0");

            PacPanel pacPanel = new PacPanel(width, height);
            pFrame.add(pacPanel);
            pFrame.setVisible(true);
        });
    }
}
