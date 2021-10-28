import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class PacPanel extends JPanel {
    private Pacman pacman;
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

    public PacPanel(Pacman pacman, ArrayList<Ghost> ghosts) {
        setOpaque(true);
        setBackground(Color.BLACK);

        this.ghosts = ghosts;
        this.pacman = pacman;
    }

    protected void drawCharacters () {
        repaint(pacman.getRectangle());
        for (Ghost monster : ghosts) {
            repaint(monster.getRectangle());
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Important! The order is important! Draw the maze FIRST!
        Maze.INSTANCE.drawMap(g);

        if (pacman != null) pacman.draw(g);
        for (Ghost monster : ghosts) {
            if (monster != null) monster.draw(g);
        }

    }
}
