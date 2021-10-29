import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PacmanFrame extends JFrame {
    private JLabel debugLabel = new JLabel();
    private MazePanel mazePanel;
    private JPanel bottomPanel;
    private JLabel bottomLabel, scoreLabel;
    private Dimension gameSize, gridSize;
    private ImageIcon liveIcon;
    private JDialog gameOverDialog;
    private ArrayList<JLabel> livesLeft = new ArrayList<>();
    Timer timer;

    protected PacmanFrame(int width, int height) {
        JPanel contentPanel = new JPanel(new BorderLayout(3, 1));

        gameSize = new Dimension(width, height);
        gridSize = new Dimension((int) width / 28, (int) height / 31);

        GameEngine.INSTANCE.setSizes(gameSize, gridSize);
        GameEngine.INSTANCE.initGame();

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

        gameOverDialog = new JDialog(this, "Game over !");

        add(contentPanel);
        setContentPane(contentPanel);
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();

        setupControls();

        // The pacman icons in the bottom bar indicating number of lives left
        liveIcon = new ImageIcon(GameEngine.INSTANCE.getPacman().pacImages.get(1));
        for (int i = 0; i < GameEngine.INSTANCE.getLives(); i++) {
            JLabel label = new JLabel(liveIcon);
            livesLeft.add(label);
            bottomPanel.add(label);
        }
    }

    private void setupControls() {

        Timer scatterTimer = new Timer(10000, (ae -> {
            Toolkit.getDefaultToolkit().sync();
            GameEngine.INSTANCE.setScatter();
        }));

        Timer chaseTimer = new Timer(13000, (ae -> {
            Toolkit.getDefaultToolkit().sync();
            GameEngine.INSTANCE.setChase();
        }));

        timer = new Timer(10, (ae -> {
            Toolkit.getDefaultToolkit().sync();
            // Update the game
            gameUpdate();

            // Update game stats in bottom panel
            updateBottom();

            // Update score in topPanel
            updateTop();

        }));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (GameEngine.INSTANCE.isRunning) {
                    GameEngine.INSTANCE.getPacman().keyPressed(e);
                    // GameEngine.INSTANCE.getGhosts().forEach(z -> z.keyPressed(e));
                } else {
                    GameEngine.INSTANCE.startGame();
                    GameEngine.INSTANCE.getPacman().keyPressed(e);
                    timer.start();
                    scatterTimer.start();
                    chaseTimer.start();
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

        // Check if game has been stopped
        if (!GameEngine.INSTANCE.isRunning) {
            timer.stop();

            if (GameEngine.INSTANCE.isGameOver) {
                gameOverDialog.add(new JLabel("Game over, score: " + GameEngine.INSTANCE.getScore()));
                gameOverDialog.setSize(200, 200);
                gameOverDialog.setVisible(true);
                System.out.println("game over from frame");
            }
        } else {
            debugLabel.setText("Pacmans position: " + GameEngine.INSTANCE.getPacman().get_X() + ", "
                    + GameEngine.INSTANCE.getPacman().get_Y() + " food " + Maze.INSTANCE.getFoodLeft());
            // System.out.println("Pacmans position: " + GameEngine.INSTANCE.getPacman().x +
            // ", " + GameEngine.INSTANCE.getPacman().y + " food " +
            // Maze.INSTANCE.getFoodLeft());
            GameEngine.INSTANCE.updateGame();

            // Repaint the characters
            mazePanel.drawCharacters();
        }
    }

    /**
     * Checks if the number of lives has changed, and if so updates labels
     */
    private void updateBottom() {

        if (!livesLeft.isEmpty()) {
            if (livesLeft.size() != GameEngine.INSTANCE.getLives()) {
                livesLeft.get(livesLeft.size() - 1).setVisible(false);
                this.remove(livesLeft.get(livesLeft.size() - 1));
                System.out.println("setting the last one to invisibble..");
                livesLeft.remove(livesLeft.size() - 1);
                System.out.println("Lives left " + livesLeft.size());
            }
        }
    }

    private void updateTop() {
        // Update score in the top
        scoreLabel.setText("" + GameEngine.INSTANCE.getScore());
    }

}