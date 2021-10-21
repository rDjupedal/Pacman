import java.awt.*;
import java.awt.event.KeyEvent;

public interface AliveComponent {
    void draw(Graphics g);
    void doMove();
    void keyPressed(KeyEvent e); // return null for monsters
}
