import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class for the GameOver dialog
 * @author Rasmus Djupedal, Tobias Liljeblad
 */
public class GameOverDialog extends JDialog {
    private int score;
    private int highScore;
    private JTextField yourName;

    /**
     * Constructor, creates the dialog
     */
    protected GameOverDialog() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400,300);
        setResizable(false);

        score = GameEngine.INSTANCE.getScore();
        highScore = HighScore.INSTANCE.getHighScore();
        boolean isHighScore = score > highScore;

        setTitle("Game over!");
        setLayout(new GridLayout(3,1, 3, 0));

        JLabel label1 = new JLabel("",JLabel.CENTER);
        JLabel label2 = new JLabel("" + GameEngine.INSTANCE.getScore(), JLabel.CENTER);

        label1.setFont(new Font("", Font.BOLD, 25));
        label2.setFont(new Font("", Font.BOLD, 35));

        JButton okBtn = new JButton("OK");
        okBtn.addActionListener((ActionEvent e) -> {

            // Save to highscore
            if (isHighScore) {
                String name = yourName.getText();
                HighScore.INSTANCE.writeHighScore(name, score);
            }

            // Close dialog
            dispose();
        });

        yourName = new JTextField();
        yourName.setText("Enter your name..");

        JPanel p = new JPanel();

        p.setBackground(Color.GRAY);

        add(label1);
        add(label2);

        // Show different contents dependent on whether highscore reached or not
        if (isHighScore) {
            p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
            label1.setText("You got a highscore!");
            yourName.setVisible(true);
            p.add(yourName);
            okBtn.setText("Save");
        } else {
            p.setLayout(new FlowLayout());
            label1.setText("Game over !");
            yourName.setVisible(false);
        }

        p.add(okBtn);
        add(p);

        // New game
        GameEngine.INSTANCE.newGame();
    }
}
