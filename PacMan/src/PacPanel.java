import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class PacPanel extends JPanel {
    Pacman pacman;

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

        Timer scatterTimer = new Timer(10000, (ae -> {
            Toolkit.getDefaultToolkit().sync();
            setScatter();
        }));

        Timer timer = new Timer(10, (ae -> {
            Toolkit.getDefaultToolkit().sync();
            if (isRunning) gameUpdate();
        }));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isRunning) {
                    pacman.keyPressed(e);
                    ghosts.forEach(z -> z.keyPressed(e));
                } else {
                    isRunning = true;
                    pacman.keyPressed(e);
                    timer.start();
                    scatterTimer.start();
                }
            }
        });

        // show current x,y at mousepointer
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //debugLabel.setText("Mouse coords: " + e.getX() + ", " + e.getY() + "   Pacmans position: " + pacman.x
                //        + ", " + pacman.y + " food left: " + Maze.INSTANCE.getFoodLeft());
                System.out.println("Mouse coords: " + e.getX() + ", " + e.getY() + "   Pacmans position: " + pacman.x
                        + ", " + pacman.y);
                debugLabel.setVisible(true);
                if (timer.getDelay() == 10)
                    timer.setDelay(100);
                else
                    timer.setDelay(10);
                // debugLabel.setOpaque(true);
            }
        });

        add(debugLabel);

        // maze = new Maze(this);
        Maze.INSTANCE.startMaze(level, new Dimension(width, height), new Dimension(gridWidth, gridHeight));

        // todo: Read map, get start coords for all objects and pass them for
        // construction

        // Creation of characters
        AbstractFactory pacManFactory = FactoryProducer.getFactory(true);
        AbstractFactory ghostFactory = FactoryProducer.getFactory(false);

        // todo: replace harcoded startpos with Maze.INSTANCE.getPacmanX() & Maze.INSTANCE.getPacmanY()
        pacman = pacManFactory.getCharacter("pacman", 400, 690);

        // Lägger till monster, 300 + i*30 är för att skapa lite space mellen dom, då
        // dom just nu följer samma rörelsemönster.

        ghosts.add(ghostFactory.getCharacter("ghost", 330, 390, "red"));
        ghosts.add(ghostFactory.getCharacter("ghost", 330, 450, "blue"));
        ghosts.add(ghostFactory.getCharacter("ghost", 480, 390, "yellow"));
        ghosts.add(ghostFactory.getCharacter("ghost", 480, 450, "pink"));

    }

    protected void setScatter() {
        ghosts.forEach(ghost -> {
            ghost.setScatter();
        });
    }

    protected void gameUpdate() {
        // Check if game is finished (if food is still left)
        if (Maze.INSTANCE.getFoodLeft() < 1) {
            System.out.println("game finished!!");

            isRunning = false;
        }
        debugLabel.setText("Pacmans position: " + pacman.x + ", " + pacman.y + " food " + Maze.INSTANCE.getFoodLeft());
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
