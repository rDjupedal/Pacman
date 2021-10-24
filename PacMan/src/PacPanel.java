import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class PacPanel extends JPanel {
    Pacman pacman;
    Monster monster;
    Maze maze;
    final int width, height;
    final int gridWidth, gridHeight;
    int level = 1;
    boolean isRunning = false;
    ArrayList<Monster> monsters = new ArrayList<Monster>();
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
                debugLabel.setText("Mouse coords: " + e.getX() + ", " + e.getY() +
                        "   Pacmans position: " + pacman.x + ", " + pacman.y + " keyBuffer: " + pacman.keyBuffer.toString());
                System.out.println("Mouse coords: " + e.getX() + ", " + e.getY() +
                        "   Pacmans position: " + pacman.x + ", " + pacman.y + " keyBuffer: " + pacman.keyBuffer.toString());
                debugLabel.setVisible(true);
                //debugLabel.setOpaque(true);
            }
        });

        add(debugLabel);

        //maze = new Maze(this);
        Maze2.INSTANCE.startMaze(level, new Dimension(width, height), new Dimension(gridWidth, gridHeight));

        // todo: Read map, get start coords for all objects and pass them for
        // construction

        // Creation of characters
        AbstractFactory pacManFactory = FactoryProducer.getFactory(true);
        AbstractFactory monsterFactory = FactoryProducer.getFactory(false);

        pacman = pacManFactory.getCharacter("pacman", 30, 30);

        // Lägger till monster, 300 + i*30 är för att skapa lite space mellen dom, då
        // dom just nu följer samma rörelsemönster.
        for (int i = 0; i < 4; i++) {
            monsters.add(monsterFactory.getCharacter("monster", 300, 300 + i * 30, i));
        }

    }

    protected void gameUpdate() {
        debugLabel.setText("Pacmans position: " + pacman.x + ", " + pacman.y + " keyBuffer: " + pacman.keyBuffer.toString());
        pacman.doMove();
        //Only redraws the area surrounding Pacman
        repaint(pacman.getRectangle());

        for (Monster monster : monsters) {
            monster.doMove();
            repaint(monster.getRectangle());
        }

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Important! The order is important! Draw the maze FIRST!
        //maze.drawMap(g);
        Maze2.INSTANCE.drawMap(g);

        // todo: update moves for all Characters
        pacman.draw(g);
        for (Monster monster : monsters) {
            monster.draw(g);
        }

    }

}
