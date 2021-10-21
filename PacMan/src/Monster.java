
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.*;

public class Monster implements Character {

    int x, y;
    final int monsterSize = 30;
    final int moveDistance = 1;
    char lastKey;
    String direction = "up";
    Color color = Color.RED;

    public Monster(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("Creating Monster at" + x + ", " + y);

    }

    private String getDirection() {
        if (x == 0 && y > 0) {
            direction = "up";

        } else if (x == 800 && y == 0) {
            direction = "down";

        } else if (y == 0 && x > 0) {
            direction = "right";

        } else if (y == 600 && x == 800) {
            direction = "left";
        } else if (x == 0 && y == 0) {
            direction = "right";
        }

        return direction;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Empty, as monsters do not listen to keys.
    }

    @Override
    public void doMove() {

        switch (getDirection()) {
        case "left":
            x = x - moveDistance;
            break;
        case "right":
            x = x + moveDistance;
            break;
        case "up":
            y = y - moveDistance;
            break;
        case "down":
            y = y + moveDistance;
        default:
            break;
        }

        String debug = String.format("monster is at %d, %d", x, y);
        System.out.println(debug);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void draw(Graphics g) {
        System.out.println("Drawing monster from drawMonster");
        g.setColor(color);
        g.fillOval(x, y, monsterSize, monsterSize);

    }

}
