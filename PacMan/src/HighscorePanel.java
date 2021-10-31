import javax.swing.*;

public class HighscorePanel extends JPanel {
    protected HighscorePanel() {
        //setLayout(BoxLayout());
        add(new JLabel("Game over, score: " + GameEngine.INSTANCE.getScore()));
    }
}
