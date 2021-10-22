import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.imageio.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

class PacPanel extends JPanel {
    Pacman pacman;
    Monster monster;
    GameLevel gamelevel;
    final int width;
    final int height;
    int level = 1;
    ArrayList<Monster> monsters = new ArrayList<Monster>();

    public PacPanel(int width, int height) {
        this.width = width;
        this.height = height;
        setupPanel();

    }

    protected void setupPanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

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

        // Creation of characters
        AbstractFactory pacManFactory = FactoryProducer.getFactory(true);
        AbstractFactory monsterFactory = FactoryProducer.getFactory(false);

        pacman = pacManFactory.getCharacter("pacman", 100, 100);

        // Lägger till monster, 300 + i*30 är för att skapa lite space mellen dom, då
        // dom just nu följer samma rörelsemönster.
        for (int i = 0; i < 4; i++) {

            monsters.add(monsterFactory.getCharacter("monster", 300, 300 + i * 30, i));

        }

        timer.start();
    }

    protected void paintComponent(Graphics g) {

        // if removed, we don't have to redraw everything?
        super.paintComponent(g);

        // todo: update moves for all Characters
        pacman.doMove();

        /**
         * Den här kör monsters::move och hämtar bilden för alla monster.
         */
        for (Monster monster : monsters) {
            monster.doMove();
            g.drawImage(monster.getImage(), monster.getX(), monster.getY(), 30, 30, null);

        }

        // todo: redraw all Characters
        // pacman.draw(g);
        // monster.draw(g);

        /**
         * Den här målar bara en bild på x,y,width,height. Har lagt in att hämta
         * positionen från klasserna.
         * 
         * 
         */

        g.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), 30, 30, null);

        // todo: is there any way we can avoid calling the method at each tick?
        // gamelevel.drawMap(g);
    }

}
