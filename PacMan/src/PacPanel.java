import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class PacPanel extends JPanel {
//class PacPanel extends JLayeredPane {
    Pacman pacman;
    Monster monster;
    Maze maze;
    final int width;
    final int height;
    int level = 1;
    ArrayList<Monster> monsters = new ArrayList<Monster>();
    JLabel debugLabel = new JLabel();

    public PacPanel(Dimension dimension) {
        setPreferredSize(dimension);
        setOpaque(true);
        width = dimension.width;
        height = dimension.height;
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
                pacman.keyPressed(e);
            }
        });

        // show current x,y at mousepointer
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                debugLabel.setText("Mouse coords: " + e.getX() + ", " + e.getY());
                System.out.println("Mouse coords: " + e.getX() + ", " + e.getY());
                debugLabel.setVisible(true);
                debugLabel.setOpaque(true);
            }
        });

        add(debugLabel);
        //add(debugLabel, 100);

        maze = new Maze(this);
        //add(maze);
        //add(maze, 200);
        //maze.repaint();


        // todo: Read map, get start coords for all objects and pass them for
        // construction

        // Creation of characters
        AbstractFactory pacManFactory = FactoryProducer.getFactory(true);
        AbstractFactory monsterFactory = FactoryProducer.getFactory(false);

        pacman = pacManFactory.getCharacter("pacman", 100, 100);
        //add(pacman, DRAG_LAYER);
        //add(pacman);

        // Lägger till monster, 300 + i*30 är för att skapa lite space mellen dom, då
        // dom just nu följer samma rörelsemönster.
        for (int i = 0; i < 4; i++) {

            monsters.add(monsterFactory.getCharacter("monster", 300, 300 + i * 30, i));

        }

        timer.start();
    }

    protected void gameUpdate() {

        pacman.doMove();
        //Only redraws the area surrounding Pacman
        repaint(pacman.getRectangle());

        for (Monster monster : monsters) {
            monster.doMove();
            repaint(monster.getRectangle());
        }


        //repaint();

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Important! The order is important! Draw the maze FIRST!
        maze.drawMap(g);

        // todo: update moves for all Characters
        pacman.draw(g);
        for (Monster monster : monsters) {
            monster.draw(g);
        }



        /**
         * Den här kör monsters::move och hämtar bilden för alla monster.
         */
        /*
        for (Monster monster : monsters) {
            monster.doMove();
            g.drawImage(monster.getImage(), monster.getX(), monster.getY(), 30, 30, null);
        }
         */



        // todo: redraw all Characters
        // pacman.draw(g);
        // monster.draw(g);

        /**
         * Den här målar bara en bild på x,y,width,height. Har lagt in att hämta
         * positionen från klasserna.
         * 
         * 
         */

        //g.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), 30, 30, null);

        // todo: is there any way we can avoid calling the method at each tick?

    }

}
