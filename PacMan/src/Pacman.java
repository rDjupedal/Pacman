import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Pacman {

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            PacFrame pFrame = new PacFrame();
            pFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}

class PacFrame extends JFrame  {
    final int width = 600;
    final int height = 600;

    public PacFrame() {
        setSize(width, height);
        setTitle("Pacman 1.0");
        PacPanel pacPanel = new PacPanel(width, height);
        add(pacPanel);

        setVisible(true);
    }
}

class PacPanel extends JPanel {
    Pac pac;
    final int width;
    final int height;

    public PacPanel(int width, int height) {
        this.width = width;
        this.height = height;
        setupPanel();
    }

    protected void setupPanel() {
        setFocusable(true);
        requestFocusInWindow();

        // Timer
        Timer timer = new Timer(2000,( ae ->
        {
            Toolkit.getDefaultToolkit().sync();
            repaint();
        }
        ));

        timer.start();

        // todo: Read map, get start coords for pac
        pac = new Pac(100,100);
        //pac.repaint();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) { pac.keyPressed(e); }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        pac.drawPacman(g);

    }

}

class Pac extends JComponent {
    int x, y;
    final int pacSize = 30;
    char lastKey;

    public Pac(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("Creating a Pac at " + x + ", " + y);
    }

    protected void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode());
        switch (e.getKeyCode()) {
            case 38: lastKey = 'U'; // Up
            case 40: lastKey = 'D'; // Down
            case 37: lastKey = 'L'; // Left
            case 39: lastKey = 'R'; // Right
            case 27: lastKey = 'E'; // Escape (Stop playing)
            case 80: lastKey = 'P'; // P (Pause)
        }
    }

    protected void drawPacman(Graphics g) {
        System.out.println("drawing pacman from drawPacman..");
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, pacSize, pacSize);
    }

    protected void paintComponent(Graphics g) {
        System.out.println("drawing pacman from painComponent..");
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, pacSize, pacSize);
    }

    protected void keyUp() {}
    protected void keyDown() {}
    protected void keyLeft() {}
    protected void keyRight() {}
}