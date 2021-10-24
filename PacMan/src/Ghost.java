import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

public abstract class Ghost extends JComponent {
    int x, y;
    int number;

    public Ghost() {

    }

    public void doMove() {
        System.out.println("Does a move from Ghost class");
    }

    public abstract Rectangle getRectangle();

    public abstract void draw(Graphics g);
}
