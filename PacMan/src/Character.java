import java.awt.*;
import java.awt.event.KeyEvent;

//Image imports
import java.awt.image.*;

public interface Character {
    void draw(Graphics g);

    // void paintComponent(Graphics g);
    void doMove();

    void keyPressed(KeyEvent e); // does nothing for monsters

    // Testing getPosition

    int getY();

    int getX();

    BufferedImage getImage();

}
