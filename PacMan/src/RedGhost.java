
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.*;

//Image imports
import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RedGhost extends Ghost implements LivingCharacter {

    int x, y;
    String name;
    final int ghostSize = 30;
    final int moveDistance = 2;
    String direction = "up";

    ArrayList<BufferedImage> monsterImg = new ArrayList<BufferedImage>();
    BufferedImage currentImg;

    public RedGhost(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        IchaseBehaviour = new ChaseRandom();

        this.name = "Red ghost";
        System.out.println(name + "created at " + x + ", " + y);

        // Laddar deras bilder beroende på sekvens. (Ska man göra olika klasser för alla
        // spöken istället? Färgkodat?)
        try {

            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/redGhost/redUp.png")));
            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/redGhost/redDown.png")));
            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/redGhost/redRight.png")));
            monsterImg.add(ImageIO.read(this.getClass().getResource("resources/redGhost/redLeft.png")));
        } catch (IOException e) {
            System.out.println("Spökenas bild kunde inte hämtas: " + e.getMessage());
        }
        currentImg = monsterImg.get(0);

    }

    public BufferedImage getImage() {
        return monsterImg.get(0);
    }

    /**
     * Enkel liten loop som späkena gör. Vänden i olika hörn och snurrar ett helt
     * varv.
     * 
     * @return
     */
    private String getDirection() {
        if (x == 0 && y > 0) {
            direction = "up";

        } else if (x == 800 && y == 0) {
            direction = "down";

        } else if (y == 0 && x > 0) {
            direction = "right";

        } else if (y == 600 && x == 800) {
            direction = "left";
        } else if (x == 0 && y == 0) {
            direction = "right";
        }

        return direction;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Empty, as monsters do not listen to keys.
    }

    @Override
    public void doMove() {

        switch (IchaseBehaviour.chase()) {
        case "left":
            currentImg = monsterImg.get(3);
            x = x - moveDistance;
            break;
        case "right":
            currentImg = monsterImg.get(2);
            x = x + moveDistance;
            break;
        case "up":
            currentImg = monsterImg.get(0);
            y = y - moveDistance;
            break;
        case "down":
            currentImg = monsterImg.get(1);
            y = y + moveDistance;
        default:
            break;
        }

        // Pacman Switch
        switch (IchaseBehaviour.chase()) {

        case "up":
            if (!Maze2.INSTANCE.getBrick(x, y - moveDistance).isWall()) {
                y -= moveDistance;
                currentImg = monsterImg.get(0);
            } else

                break;

        case "down":
            if (!Maze2.INSTANCE.getBrick(x, y + ghostSize).isWall()) {
                y += moveDistance;
                currentImg = monsterImg.get(1);
            } else

                break;

        case "left":
            if (x - moveDistance < 0)
                x = Maze2.INSTANCE.width - Maze2.INSTANCE.gridWidth;
            if (!Maze2.INSTANCE.getBrick(x - moveDistance, y).isWall()) {
                x -= moveDistance;
                currentImg = monsterImg.get(3);
            } else

                break;

        case "right":
            if (x + ghostSize >= Maze2.INSTANCE.width + moveDistance)
                x = 0;
            if (!Maze2.INSTANCE.getBrick(x + ghostSize, y).isWall()) {
                x += moveDistance;
                currentImg = monsterImg.get(2);
            } else

                break;
        }

        // DEbug test.
        String debug = String.format("%s is at %d, %d", name, x, y);
        // System.out.println(debug);
    }

    /**
     * Getters för positionen.
     */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void draw(Graphics g) {
        // System.out.printf("Drawing %s from Monsterclass %n", name);
        g.drawImage(currentImg, x, y, ghostSize, ghostSize, null);
    }

    /**
     * Returns a rectangle around pacman in order to only redraw this part
     * 
     * @return rectangle surronding object
     */
    public Rectangle getRectangle() {
        return new Rectangle(x - 2, y - 2, ghostSize + 4, ghostSize + 4);
    }

}
