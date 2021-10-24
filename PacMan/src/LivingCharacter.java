import java.awt.*;
import java.awt.event.KeyEvent;

//Image imports
import java.awt.image.*;

public interface LivingCharacter {
    void draw(Graphics g);

    // void paintComponent(Graphics g);
    void doMove();

    void keyPressed(KeyEvent e); // does nothing for monsters

    // Getters för position

    int getY();

    int getX();

    // GEtter for img.
    BufferedImage getImage();

}
