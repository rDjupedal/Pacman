import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The panel which contains the actual game
 * @author Rasmus Djupedal, Tobias Liljeblad
 */
class MazePanel extends JPanel {
    private Pacman pacman;
    private ArrayList<Ghost> ghosts;

    /**
     * Constructor, called with pacman- and ghosts- instances.
     * @param pacman Pacman instance
     * @param ghosts Ghost instances
     */
    public MazePanel(Pacman pacman, ArrayList<Ghost> ghosts) {
        setOpaque(true);
        setBackground(Color.BLACK);

        this.ghosts = ghosts;
        this.pacman = pacman;
    }

    /**
     * Checks what part of the screen / panel to repaint
     */
    protected void drawCharacters () {
        // Check if the whole panel should be repainted
        if (GameEngine.INSTANCE.getClearScreen()) {
            // Repaint the whole panel
            repaint();

        } else {
            // Repaint only the parts of the panel where pacman and the ghosts are
            repaint(pacman.getRectangle());
            for (Ghost monster : ghosts) {
                repaint(monster.getRectangle());
            }
        }
    }

    /**
     * Repaints the panel
     * @param g Graphics object
     */
    @Override
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
