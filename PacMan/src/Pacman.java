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

        if (!isMoving) {
            keyBuffer.clear();
        }
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

        for (int i = 0; i < keyBuffer.size(); i++) {
            if (keyBuffer.peekFirst() == direction) {
                keyBuffer.removeFirst();
            }
        }

        if (!keyBuffer.isEmpty()) {
            if (keyBuffer.peekFirst() != direction) {

                // Only change direction to up / down when pacman is inside a vertical grid
                if (keyBuffer.peekFirst() == 'U' || keyBuffer.peekFirst() == 'D') {
                    if (x % Maze2.INSTANCE.gridWidth <= 1) {
                        direction = keyBuffer.pollFirst();
                        System.out.println("changing direction to " + direction + " at pos " + x + ", " + y);
                    }
                }

                // Only change direction to left / right when pacman is inside a horizontal grid
                else if (keyBuffer.peekFirst() == 'L' || keyBuffer.peekFirst() == 'R') {
                    if (y % Maze2.INSTANCE.gridHeight <= 1) {
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
            if (!Maze2.INSTANCE.getBrick(x, y - moveDistance).isWall() ) {
                y -= moveDistance;
            } else isMoving = false;
            currentImg = pacImages.get(2);
            break;

        case 'D':
            if (!Maze2.INSTANCE.getBrick(x, y + pacSize).isWall() ) {
                y += moveDistance;
            } else isMoving = false;
            currentImg = pacImages.get(3);
            break;

        case 'L':
            if (x - moveDistance < 0) x = Maze2.INSTANCE.width - Maze2.INSTANCE.gridWidth;
            if (!Maze2.INSTANCE.getBrick(x - moveDistance,y).isWall() ) {
                x -= moveDistance;
            } else isMoving = false;
            currentImg = pacImages.get(0);
            break;

        case 'R':
            if (x > Maze2.INSTANCE.width + moveDistance) x = 0;
            if (!Maze2.INSTANCE.getBrick(x + pacSize , y).isWall() ) {
                x += moveDistance;
            } else isMoving = false;
            currentImg = pacImages.get(1);
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