import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameEngineFrame extends JFrame {
    int level = 1;
    boolean isRunning = false;
    ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    JLabel debugLabel = new JLabel();
    private PacPanel pacPanel;
    Pacman pacman;
    private Dimension gameSize, gridSize;

    protected GameEngineFrame(int width, int height) {
        JPanel contentPanel = new JPanel(new BorderLayout(3, 1));

        gameSize = new Dimension(width, height);
        gridSize = new Dimension(width / 28, height / 31);

        setupEngine();

        pacPanel = new PacPanel(pacman, ghosts);

        pacPanel.setPreferredSize(gameSize);
        pacPanel.add(debugLabel);

        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        topPanel.setPreferredSize(new Dimension(width, 30));
        topPanel.setBackground(Color.BLACK);

        bottomPanel.setPreferredSize(new Dimension(width, 30));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setOpaque(true);

        contentPanel.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);
        contentPanel.add(pacPanel, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.AFTER_LAST_LINE);

        JLabel testLabel = new JLabel("TEST");
        topPanel.add(testLabel);

        add(contentPanel);

        //setContentPane(pacPanel);
        setContentPane(contentPanel);
        setVisible(true);

        setFocusable(true);
        requestFocusInWindow();
        //setupEngine();
    }

    protected void setupEngine() {
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
                System.out.println("key pressed");
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

        Maze.INSTANCE.startMaze(level, gameSize, gridSize);

        // todo: Read map, get start coords for all objects and pass them for
        // construction

        // Creation of characters
        AbstractFactory pacManFactory = FactoryProducer.getFactory(true);
        AbstractFactory ghostFactory = FactoryProducer.getFactory(false);

        // todo: replace harcoded startpos with Maze.INSTANCE.getPacmanX() & Maze.INSTANCE.getPacmanY()
        pacman = pacManFactory.getCharacter("pacman", 400, 690);

        // Create some monsters

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

        // Move the characters
        pacman.doMove();
        for (Ghost monster : ghosts) {
            monster.doMove();
        }

        // Repaint the characters
        pacPanel.drawCharacters();

    }

}