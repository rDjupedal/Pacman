import java.awt.*;
import java.awt.event.KeyEvent;

public interface Character {
    void draw(Graphics g);

    // void paintComponent(Graphics g);
    void doMove();

    void keyPressed(KeyEvent e); // does nothing for monsters

    // Testing getPosition

    int getY();

    int getX();

}
