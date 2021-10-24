import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

public abstract class Ghost extends JComponent {
    int x, y;
    int number;

    public Ghost(int x, int y, int number) {
        this.x = x;
        this.y = y;
        this.number = number;

    }

    public void doMove() {
        System.out.println("Does a move from Ghost class");
    }

    public abstract Rectangle getRectangle();

    public abstract void draw(Graphics g);
}
