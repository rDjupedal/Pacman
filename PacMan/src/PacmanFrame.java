import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PacmanFrame extends JFrame {
    //private int level = 1;
    //private boolean isRunning = false;
    //private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private JLabel debugLabel = new JLabel();
    private MazePanel mazePanel;
    private JPanel bottomPanel;
    private JLabel bottomLabel;
    //private Pacman pacman;
    private Dimension gameSize, gridSize;
    private Timer timer, scatterTimer;

    protected PacmanFrame(int width, int height) {
        JPanel contentPanel = new JPanel(new BorderLayout(3, 1));

        gameSize = new Dimension(width, height);
        gridSize = new Dimension(width / 28, height / 31);

        GameEngine.INSTANCE.createPacman();
        GameEngine.INSTANCE.createGhosts();

        mazePanel = new MazePanel(GameEngine.INSTANCE.getPacman(), GameEngine.INSTANCE.getGhosts());
        mazePanel.setPreferredSize(gameSize);
        mazePanel.add(debugLabel);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(width, 30));
        topPanel.setBackground(Color.BLACK);

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(width, 30));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setOpaque(true);
        bottomLabel = new JLabel();
        bottomPanel.add(bottomLabel);

        contentPanel.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);
        contentPanel.add(mazePanel, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.AFTER_LAST_LINE);

        JLabel testLabel = new JLabel("TEST");
        topPanel.add(testLabel);

        add(contentPanel);

        setContentPane(contentPanel);
        setVisible(true);

        setFocusable(true);
        requestFocusInWindow();

        setupControls();

        GameEngine.INSTANCE.setSizes(gameSize, gridSize);
        GameEngine.INSTANCE.initilizeGame();
    }

    private void setupControls() {

        scatterTimer = new Timer(10000, (ae -> {
            Toolkit.getDefaultToolkit().sync();
            GameEngine.INSTANCE.setScatter();
        }));

        timer = new Timer(10, (ae -> {
            Toolkit.getDefaultToolkit().sync();
            if (GameEngine.INSTANCE.isRunning) gameUpdate();
            System.out.println("timer ticking..");
        }));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("key pressed");
                if (GameEngine.INSTANCE.isRunning) {
                    GameEngine.INSTANCE.getPacman().keyPressed(e);
                    GameEngine.INSTANCE.getGhosts().forEach(z -> z.keyPressed(e));
                } else {
                    GameEngine.INSTANCE.startGame();
                    GameEngine.INSTANCE.getPacman().keyPressed(e);
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

                //System.out.println("Mouse coords: " + e.getX() + ", " + e.getY() + "   Pacmans position: " + pacman.x
                //        + ", " + pacman.y);
                debugLabel.setVisible(true);
                if (timer.getDelay() == 10)
                    timer.setDelay(100);
                else
                    timer.setDelay(10);
                // debugLabel.setOpaque(true);
            }
        });
    }


    protected void gameUpdate() {

        debugLabel.setText("Pacmans position: " + GameEngine.INSTANCE.getPacman().x + ", " + GameEngine.INSTANCE.getPacman().y + " food " + Maze.INSTANCE.getFoodLeft());
        System.out.println("Pacmans position: " + GameEngine.INSTANCE.getPacman().x + ", " + GameEngine.INSTANCE.getPacman().y + " food " + Maze.INSTANCE.getFoodLeft());
        GameEngine.INSTANCE.updateGame();

        // Repaint the characters
        mazePanel.drawCharacters();

        //Update game stats i bottom panel
        updateBottom();
    }

    private void updateBottom() {

        bottomLabel.setText("test");
    }

}