
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.*;

public class Monster implements Character {

    int x, y;
    final int monsterSize = 30;
    final int moveDistance = 1;
    char lastKey;
    boolean firsttime = true;

    public Monster(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("Creating Monster at" + x + ", " + y);

    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Empty, as monsters do not listen to keys.
    }

    @Override
    public void doMove() {
        int start = (int) Math.random();

        System.out.println(start);

        System.out.println("Monster is at " + x + ", " + y);

    }

    @Override
    public void draw(Graphics g) {
        System.out.println("Drawing monster from drawMonster");
        g.setColor(Color.RED);
        g.fillOval(x, y, monsterSize, monsterSize);

    }

}
