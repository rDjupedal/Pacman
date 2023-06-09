import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * JFrame for the game
 * @author Rasmus Djupedal, Tobias Liljeblad
 */
public class PacmanFrame extends JFrame {
    private JLabel debugLabel = new JLabel();
    private MazePanel mazePanel;
    private JLabel bottomLabel, scoreLabel, highScoreLabel;
    private Dimension gameSize, gridSize;
    private ImageIcon liveIcon;
    private JDialog gameOverDialog;
    private ArrayList<JLabel> livesLeft = new ArrayList<>();
    Timer timer;

    /**
     * Constructor, sets up all the Swing components and tells GameEngine to initialize a the game
     * @param width the with of the playable area
     * @param height the height of the playable area
     */
    protected PacmanFrame(int width, int height) {
        JPanel contentPanel = new JPanel(new BorderLayout(3, 1));

        gameSize = new Dimension(width, height);
        gridSize = new Dimension((int) width / 28, (int) height / 31);

        GameEngine.INSTANCE.setSizes(gameSize, gridSize);
        GameEngine.INSTANCE.initGame();

        mazePanel = new MazePanel(GameEngine.INSTANCE.getPacman(), GameEngine.INSTANCE.getGhosts());
        mazePanel.setPreferredSize(gameSize);
        mazePanel.add(debugLabel);

        // TOP PANEL
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setPreferredSize(new Dimension(width, gridSize.height));
        topPanel.setBackground(Color.BLACK);

        Font topFont = new Font("", Font.BOLD, 22);

        scoreLabel = new JLabel("0");
        scoreLabel.setFont(topFont);
        scoreLabel.setForeground(Color.WHITE);

        highScoreLabel = new JLabel(
                HighScore.INSTANCE.getHighScoreName() + " " + HighScore.INSTANCE.getHighScore(), JLabel.RIGHT);
        highScoreLabel.setFont(topFont);
        highScoreLabel.setForeground(Color.WHITE);

        topPanel.add(scoreLabel);
        topPanel.add(highScoreLabel);

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setPreferredSize(new Dimension(width, gridSize.height));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setOpaque(true);
        bottomLabel = new JLabel();
        bottomPanel.add(bottomLabel);

        // CONTENT PANEL
        contentPanel.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);
        contentPanel.add(mazePanel, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.AFTER_LAST_LINE);
        add(contentPanel);
        setContentPane(contentPanel);
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();

        setupControls();

        // The Pacman icons in the bottom bar indicating number of lives left
        liveIcon = new ImageIcon(GameEngine.INSTANCE.getPacman().charImages.get(1));
        for (int i = 0; i < GameEngine.INSTANCE.getLives(); i++) {
            JLabel label = new JLabel(liveIcon);
            livesLeft.add(label);
            bottomPanel.add(label);
        }
    }

    /**
     * Sets up a timer and a KeyListener to control the game
     */
    private void setupControls() {

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
                // A key was pressed

                if (GameEngine.INSTANCE.isRunning) {
                    // Game is running, send the KeyEvent to Pacman
                    GameEngine.INSTANCE.getPacman().keyPressed(e);
                } else {
                    // Game is not running, start it and send the KeyEvent to Pacman
                    GameEngine.INSTANCE.startGame();
                    GameEngine.INSTANCE.getPacman().keyPressed(e);
                    timer.start();
                }
            }
        });

        // show current x,y at mousepointer
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                /*  Uncomment to decrease gamespeed to 10% when mouse is clicked
                debugLabel.setVisible(true);
                if (timer.getDelay() == 10)
                    timer.setDelay(100);
                else
                    timer.setDelay(10);

                 */

            }
        });
    }

    /**
     * Updates the game, called by timer at each "tick"
     */
    protected void gameUpdate() {

        // Check if game has been stopped
        if (!GameEngine.INSTANCE.isRunning) {
            timer.stop();

            if (GameEngine.INSTANCE.isGameOver) {
                gameOverDialog = new GameOverDialog();
                gameOverDialog.setLocationRelativeTo(this);
                gameOverDialog.setVisible(true);
            }
        } else {
            debugLabel.setText("Pacmans position: " + GameEngine.INSTANCE.getPacman().get_X() + ", "
                    + GameEngine.INSTANCE.getPacman().get_Y() + " food " + Maze.INSTANCE.getFoodLeft());

            GameEngine.INSTANCE.updateGame();

            // Repaint the characters
            mazePanel.drawCharacters();
        }

    }

    /**
     * Checks if the number of lives has changed, and if so update the labels
     */
    private void updateBottom() {

        // how lives many are visible?
        int visible = 0;
        for (JLabel label : livesLeft) {
            if (label.isVisible())
                visible++;
        }

        if (visible > GameEngine.INSTANCE.getLives()) {
            // Make first visible not visible
            for (JLabel label : livesLeft) {
                if (label.isVisible()) {
                    label.setVisible(false);
                    break;
                }
            }
        }

        if (visible < GameEngine.INSTANCE.getLives()) {
            // Make first invisible visible
            for (JLabel label : livesLeft) {
                if (!label.isVisible()) {
                    label.setVisible(true);
                    break;
                }
            }
        }

    }

    /**
     * Update score and highscore in the top
     */
    private void updateTop() {
        scoreLabel.setText("" + GameEngine.INSTANCE.getScore());
        highScoreLabel.setText(HighScore.INSTANCE.getHighScoreName() + " " + HighScore.INSTANCE.getHighScore());
    }

}