
import java.awt.event.KeyEvent;
import java.time.YearMonth;

import javax.swing.*;
import java.awt.*;

public class Monster implements Character {

    int x, y;
    String name;
    final int monsterSize = 30;
    final int moveDistance = 1;
    char lastKey;
    String direction = "up";
    Color color = Color.RED;

    public Monster(int x, int y, int number) {
        this.x = x;
        this.y = y;
        this.name = "Monster " + number;
        System.out.printf("Creating %s at %d, %d", name, x, y);

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

        String debug = String.format("%s is at %d, %d", name, x, y);
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
        System.out.printf("Drawing %s from Monsterclass", name);
        g.setColor(color);
        g.fillOval(x, y, monsterSize, monsterSize);

    }

}
