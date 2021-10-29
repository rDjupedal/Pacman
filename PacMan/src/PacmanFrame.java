import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PacmanFrame extends JFrame {
    private JLabel debugLabel = new JLabel();
    private MazePanel mazePanel;
    private JPanel bottomPanel;
    private JLabel bottomLabel, scoreLabel;
    private Dimension gameSize, gridSize;

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
        topPanel.setPreferredSize(new Dimension(width, gridSize.height));
        topPanel.setBackground(Color.BLACK);
        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("", Font.BOLD, 25));
        scoreLabel.setForeground(Color.RED);
        topPanel.add(scoreLabel);

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(width, gridSize.height));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setOpaque(true);
        bottomLabel = new JLabel();
        bottomPanel.add(bottomLabel);

        contentPanel.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);
        contentPanel.add(mazePanel, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.AFTER_LAST_LINE);

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

        Timer scatterTimer = new Timer(10000, (ae -> {
            Toolkit.getDefaultToolkit().sync();
            GameEngine.INSTANCE.setScatter();
        }));

        Timer timer = new Timer(10, (ae -> {
            Toolkit.getDefaultToolkit().sync();
            if (GameEngine.INSTANCE.isRunning) {

                // Update the game
                gameUpdate();

                //Update game stats in bottom panel
                updateBottom();

                //Update score i topPanel
                updateTop();
            }

        }));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

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

                debugLabel.setVisible(true);
                if (timer.getDelay() == 10)
                    timer.setDelay(100);
                else
                    timer.setDelay(10);

            }
        });
    }

    protected void gameUpdate() {

        debugLabel.setText("Pacmans position: " + GameEngine.INSTANCE.getPacman().x + ", " + GameEngine.INSTANCE.getPacman().y + " food " + Maze.INSTANCE.getFoodLeft());
        //System.out.println("Pacmans position: " + GameEngine.INSTANCE.getPacman().x + ", " + GameEngine.INSTANCE.getPacman().y + " food " + Maze.INSTANCE.getFoodLeft());
        GameEngine.INSTANCE.updateGame();

        // Repaint the characters
        mazePanel.drawCharacters();

    }

    private void updateBottom() {
        bottomLabel.setText("test");

    }

    private void updateTop() {
        //Update score in the top
        scoreLabel.setText("" + GameEngine.INSTANCE.getScore());
    }

}