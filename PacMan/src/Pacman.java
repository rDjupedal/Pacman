import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
//Image imports
import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

class Pacman extends JComponent implements LivingCharacter {
    int x, y;
    final int pacSize = 30;
    final int moveDistance = 2;
    char lastKey;
    char direction = ' ';
    boolean isMoving = false;
    Deque<Character> keyBuffer = new LinkedList<>();

    ArrayList<BufferedImage> pacImages = new ArrayList<BufferedImage>();
    BufferedImage currentImg;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("Creating a Pac at " + x + ", " + y);

        // Load images.
        try {

            pacImages.add(ImageIO.read(this.getClass().getResource("resources/pac/pacLeft.png")));
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/pac/pacRight.png")));
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/pac/pacUp.png")));
            pacImages.add(ImageIO.read(this.getClass().getResource("resources/pac/pacDown.png")));

            // Initial pic.
            currentImg = pacImages.get(0);

        } catch (IOException e) {
            System.out.println("Pacmans bild kunde inte hämtas: " + e.getMessage());
        }
    }

    public BufferedImage getImage() {
        return currentImg;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                keyBuffer.addLast('U');
                break;
            case KeyEvent.VK_DOWN:
                keyBuffer.addLast('D');
                break;
            case KeyEvent.VK_LEFT:
                keyBuffer.addLast('L');
                break;
            case KeyEvent.VK_RIGHT:
                keyBuffer.addLast('R');
                break;
            case 27:
                //key = 'E'; // todo Escape (Stop playing)
                break;
            case 80:
                //key = 'P'; // todo P (Pause)
        }

        if (!isMoving) {
            isMoving = true;
            direction = keyBuffer.pollFirst();
        }

        System.out.println("size of keyBuffer: " + keyBuffer.size());
        for (char key : keyBuffer) {
            System.out.print(key + ", ");
        }

    }

    public void doMove() {

        // todo: dont hardcode gridsize! (30x30)

        for (int i = 0; i < keyBuffer.size(); i++) {
            if (keyBuffer.peekFirst() == direction) {
                keyBuffer.removeFirst();
            }
        }

        if (!keyBuffer.isEmpty()) {
            if (keyBuffer.peekFirst() != direction) {

                // Only change direction to up / down when we are horizontally in center of a grid
                if (keyBuffer.peekFirst() == 'U' || keyBuffer.peekFirst() == 'D') {
                    if (x % 30 <= 1) {
                        direction = keyBuffer.pollFirst();
                        System.out.println("changing direction to " + direction + " at pos " + x + ", " + y);
                    }
                }

                // Only change direction to left / right when we are vertically in center of a grid
                else if (keyBuffer.peekFirst() == 'L' || keyBuffer.peekFirst() == 'R') {
                    if (y % 30 <= 1) {
                        direction = keyBuffer.pollFirst();
                        System.out.println("changing direction to " + direction + " at pos " + x + ", " + y);
                    }
                }
            }
        }


        /**
         * Rita pacman med munnen åt rätt håll samt förflytta honom
         */

        switch (direction) {
        case 'U':
            y = y - moveDistance;
            currentImg = pacImages.get(2);
            break;
        case 'D':
            currentImg = pacImages.get(3);
            y = y + moveDistance;
            break;
        case 'L':
            currentImg = pacImages.get(0);
            x = x - moveDistance;
            break;
        case 'R':
            currentImg = pacImages.get(1);
            x = x + moveDistance;
            break;
        }
        // DEBUG
        //System.out.println("pacman moved to " + x + ", " + y);
    }

    public void draw(Graphics g) {
        g.drawImage(currentImg, x, y, pacSize, pacSize, null);
    }

    public Rectangle getRectangle() {
        return new Rectangle(x-2, y-2, pacSize+4, pacSize+4);
    }

}