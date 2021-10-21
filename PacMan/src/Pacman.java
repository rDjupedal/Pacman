import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class Pacman extends JComponent implements Character {
    int x, y;
    final int pacSize = 30;
    final int moveDistance = 2;
    char lastKey;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("Creating a Pac at " + x + ", " + y);
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode());
        switch (e.getKeyCode()) {
        case 38:
            lastKey = 'U'; // Up
            break;
        case 40:
            lastKey = 'D'; // Down
            break;
        case 37:
            lastKey = 'L'; // Left
            break;
        case 39:
            lastKey = 'R'; // Right
            break;
        case 27:
            lastKey = 'E'; // Escape (Stop playing)
            break;
        case 80:
            lastKey = 'P'; // P (Pause)
        }
    }

    public void doMove() {
        System.out.println("last key: " + lastKey);
        switch (lastKey) {
        case 'U':
            y = y - moveDistance;
            break;
        case 'D':
            y = y + moveDistance;
            break;
        case 'L':
            x = x - moveDistance;
            break;
        case 'R':
            x = x + moveDistance;

            break;
        }
        // DEBUG
        System.out.println("pacman moved to " + x + ", " + y);
    }


    public void draw(Graphics g) {
        System.out.println("drawing pacman from drawPacman..");
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, pacSize, pacSize);
    }


    /*
    public void paintComponent(Graphics g) {
        System.out.println("drawing pacman from its own paintComponent method..");
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, pacSize, pacSize);
    }

     */

}