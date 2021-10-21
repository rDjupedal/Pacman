import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.imageio.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

class PacPanel extends JPanel {
    Character pacman;
    Character monster;
    GameLevel gamelevel;
    final int width;
    final int height;
    int level = 1;

    // IMAGE
    BufferedImage img = null;

    public PacPanel(int width, int height) {
        this.width = width;
        this.height = height;
        setupPanel();

    }

    protected void setupPanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        // Laddar bild.
        try {

            img = ImageIO.read(this.getClass().getResource("resources/1.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        Timer timer = new Timer(10, (ae -> {
            Toolkit.getDefaultToolkit().sync();
            repaint();
        }));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pacman.keyPressed(e);
            }
        });

        gamelevel = new GameLevel(this);
        // the level is being constantly repainted... is this a problem?
        // gamelevel.drawMap(); <--- Doesn't work as it needs a Graphics g passed as
        // argument, and thats only possible to do from withing painComponent()!?
        // which makes it being called ALL the time.

        // todo: Read map, get start coords for all objects and pass them for
        // construction

        characterFactory cFactory = new characterFactory();

        pacman = cFactory.getCharacter("pacman", 100, 100);
        monster = cFactory.getCharacter("monster", 300, 300);
        // todo: monster1 = cFactory.getCharacter("monster", 200, 200); etc.

        timer.start();
    }

    protected void paintComponent(Graphics g) {

        // if removed, we don't have to redraw everything?
        super.paintComponent(g);

        // todo: update moves for all Characters
        pacman.doMove();
        monster.doMove();

        // todo: redraw all Characters
        pacman.draw(g);
        monster.draw(g);

        /**
         * Den här målar bara en bild på x,y,width,height. Om vi bara hittar ett sätt
         * att ändra x,y på (getx and Y kanske?) så kanske vi inte behöver paint i
         * själva klasserna, utan bara ta fram positionen där.
         * 
         */
        g.drawImage(img, 50, 50, 50, 50, null);

        // todo: is there any way we can avoid calling the method at each tick?
        // gamelevel.drawMap(g);
    }

}
