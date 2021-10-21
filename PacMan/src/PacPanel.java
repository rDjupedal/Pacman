import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class PacPanel extends JPanel {
    Character pacman;
    GameLevel gamelevel;
    final int width;
    final int height;
    int level = 1;

    public PacPanel(int width, int height) {
        this.width = width;
        this.height = height;
        setupPanel();
    }

    protected void setupPanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        Timer timer = new Timer(10,( ae ->
        {
            Toolkit.getDefaultToolkit().sync();
            repaint();
        }
        ));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) { pacman.keyPressed(e); }
        });

        gamelevel = new GameLevel(this);
        add(gamelevel);
        //gamelevel.drawMap();



        // todo: Read map, get start coords for all objects and pass them for construction
        //pac = new Pac(100,100);
        characterFactory cFactory = new characterFactory();

        pacman = cFactory.getCharacter("pacman", 100, 100);
        // todo: monster1 = cFactory.getCharacter("monster", 200, 200); etc.



        timer.start();
    }

    protected void paintComponent(Graphics g) {

        // if removed, we don't have to redraw everything?
        super.paintComponent(g);

        // todo: update moves for all Characters
        pacman.doMove();

        // todo: redraw all Characters
        pacman.draw(g);
    }

}
