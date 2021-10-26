
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
    final int moveDistance = 1;
    String direction;

    ArrayList<BufferedImage> redGhostImgs = new ArrayList<BufferedImage>();
    BufferedImage currentImg;

    public RedGhost(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        IchaseBehaviour = new ChaseAggresive();

        try {

            redGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/redGhost/redUp.png")));
            redGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/redGhost/redDown.png")));
            redGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/redGhost/redRight.png")));
            redGhostImgs.add(ImageIO.read(this.getClass().getResource("resources/redGhost/redLeft.png")));
        } catch (IOException e) {
            System.out.println("Spökenas bild kunde inte hämtas: " + e.getMessage());
        }
        currentImg = redGhostImgs.get(0);

    }

    @Override
    public void doMove() {

        switch (IchaseBehaviour.chase(x, y)) {

        case "up":
            y -= moveDistance;
            currentImg = redGhostImgs.get(0);
            break;

        case "down":
            y += moveDistance;
            currentImg = redGhostImgs.get(1);
            break;

        case "left":
            x -= moveDistance;
            currentImg = redGhostImgs.get(3);
            break;

        case "right":
            x += moveDistance;
            currentImg = redGhostImgs.get(2);
            break;
        }

    }

    /**
     * Getters för positionen.
     */

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

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public BufferedImage getImage() {
        // TODO Auto-generated method stub
        return null;
    }

}
