import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

public abstract class Ghost extends JComponent {
    int x, y;
    int number;
    IChaseBehaviour IchaseBehaviour;

    public Ghost() {

    }

    /**
     * Can we get this to work for all ghosts?
     */
    public void doMove() {

    }

    public abstract Rectangle getRectangle();

    public abstract void draw(Graphics g);
}
