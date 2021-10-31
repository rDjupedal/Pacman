import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel {

    boolean highScore = true;

    protected GameOverPanel() {
        setLayout(new GridLayout(3,1, 3, 0));

        JLabel label1 = new JLabel("",JLabel.CENTER);
        JLabel label2 = new JLabel("" + GameEngine.INSTANCE.getScore(), JLabel.CENTER);

        label1.setFont(new Font("", Font.BOLD, 25));
        label2.setFont(new Font("", Font.BOLD, 35));

        JButton okBtn = new JButton("OK");
        okBtn.addActionListener((ActionEvent e) -> {
            // Save to highscore
            System.out.println("Save to highscore..");
            //this.getParent().di
            // Clean board for new game
            System.out.println(e.toString());
        });
        //okBtn.setHorizontalAlignment(JButton.CENTER);
        JTextField yourName = new JTextField();
        yourName.setText("Enter your name..");
        //yourName.setPreferredSize(new Dimension(150,20));

        JPanel p = new JPanel();

        //p.setLayout(new GridLayout());
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setBackground(Color.GRAY);

        add(label1);
        add(label2);

        if (highScore) {
            label1.setText("You got a highscore!");
            yourName.setVisible(true);
            p.add(yourName);
        } else {
            label1.setText("Game over !");
            yourName.setVisible(false);
        }

        p.add(okBtn);
        add(p);




    }

}
