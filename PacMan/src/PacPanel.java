import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class PacPanel extends JPanel {
    Pacman pacman;
    RedGhost monster;
    final int width, height;
    final int gridWidth, gridHeight;
    int level = 1;
    boolean isRunning = false;
    ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    JLabel debugLabel = new JLabel();

    public PacPanel(Dimension dimension) {
        setPreferredSize(dimension);
        setOpaque(true);

        width = dimension.width;
        height = dimension.height;

        // Calculate grid size
        gridWidth = width / 28;
        gridHeight = height / 31;

        setupPanel();
    }

    protected void setupPanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        Timer timer = new Timer(10, (ae -> {
            Toolkit.getDefaultToolkit().sync();
            gameUpdate();
        }));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isRunning) {
                    pacman.keyPressed(e);
                } else {
                    isRunning = true;
                    pacman.keyPressed(e);
                    timer.start();
                }
            }
        });

        // show current x,y at mousepointer
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                debugLabel.setText("Mouse coords: " + e.getX() + ", " + e.getY() + "   Pacmans position: " + pacman.x
                        + ", " + pacman.y);
                System.out.println("Mouse coords: " + e.getX() + ", " + e.getY() + "   Pacmans position: " + pacman.x
                        + ", " + pacman.y);
                debugLabel.setVisible(true);
                if (timer.getDelay() == 10) timer.setDelay(100);
                else timer.setDelay(10);
                // debugLabel.setOpaque(true);
            }
        });

        add(debugLabel);

        //maze = new Maze(this);
        Maze.INSTANCE.startMaze(level, new Dimension(width, height), new Dimension(gridWidth, gridHeight));

        // todo: Read map, get start coords for all objects and pass them for
        // construction

        // Creation of characters
        AbstractFactory pacManFactory = FactoryProducer.getFactory(true);
        AbstractFactory ghostFactory = FactoryProducer.getFactory(false);

        pacman = pacManFactory.getCharacter("pacman", 30, 30);

        // Lägger till monster, 300 + i*30 är för att skapa lite space mellen dom, då
        // dom just nu följer samma rörelsemönster.

        ghosts.add(ghostFactory.getCharacter("monster", 100, 300, "red"));
        ghosts.add(ghostFactory.getCharacter("monster", 150, 300, "blue"));
        ghosts.add(ghostFactory.getCharacter("monster", 200, 300, "yellow"));
        ghosts.add(ghostFactory.getCharacter("monster", 250, 300, "pink"));

    }

    protected void gameUpdate() {
        debugLabel.setText("Pacmans position: " + pacman.x + ", " + pacman.y);
        pacman.doMove();
        // Only redraws the area surrounding Pacman
        repaint(pacman.getRectangle());

        for (Ghost monster : ghosts) {
            monster.doMove();
            repaint(monster.getRectangle());
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Important! The order is important! Draw the maze FIRST!
        Maze.INSTANCE.drawMap(g);

        // todo: update moves for all Characters
        pacman.draw(g);
        for (Ghost monster : ghosts) {
            monster.draw(g);
        }

    }
}
