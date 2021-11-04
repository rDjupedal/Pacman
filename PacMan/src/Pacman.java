import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
//Image imports
import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;

class Pacman extends LivingCharacter implements Runnable {

    // . means no lastKey waiting
    char lastKey = '.';

    BufferedImage currentImgBig;
    BufferedImage currentImgSmall;

    protected Pacman(int x, int y) {
        this.x = x;
        this.y = y;
        //Maze.INSTANCE.setPacManPos(x, y);
        animation = true;

        // Load images.
        try {

            charImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacLeftBig.png"))); // 0
            charImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacRightBig.png"))); // 1
            charImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacUpBig.png"))); // 2
            charImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacDownBig.png"))); // 3

            charImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacLeftSmall.png"))); // 4
            charImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacRightSmall.png"))); // 5
            charImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacUpSmall.png"))); // 6
            charImages.add(ImageIO.read(this.getClass().getResource("resources/PacMan/pacDownSmall.png"))); // 7

            setInitImage();

        } catch (IOException e) {
            System.out.println("Pacmans bild kunde inte hämtas: " + e.getMessage());
        }
    }

    protected void setInitImage() {
        // Initial pic.
        currentImgBig = charImages.get(1);
        currentImgSmall = charImages.get(4);
    }

    protected void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
            lastKey = 'U';
            break;
        case KeyEvent.VK_DOWN:
            lastKey = 'D';
            break;
        case KeyEvent.VK_LEFT:
            lastKey = 'L';
            break;
        case KeyEvent.VK_RIGHT:
            lastKey = 'R';
            break;
        case 27:
            // key = 'E'; // todo Escape (Stop playing)
            break;
        case 80:
            // key = 'P'; // todo P (Pause)
        }
    }

    protected void setDirection() {

        // Check if a key has been pressed...
        if (lastKey != '.') {

            // Only evaluate for free path when Pacman is at the center of a grid

            // Pacman is inside a vertical grid
            if (inVerticalGrid() && withinBoundary()) {
                if ((lastKey == 'U' && goUp()) || (lastKey == 'D' && goDown())) {
                    direction = lastKey;
                    lastKey = '.';
                }
            }

            // Pacman is inside a horizontal grid
            if (inHorizontalGrid() && withinBoundary()) {
                if ((lastKey == 'L' && goLeft()) || lastKey == 'R' && goRight()) {
                    direction = lastKey;
                    lastKey = '.';
                }
            }
        }
    }

    protected void moveCharacter() {
        /**
         * Rita pacman med munnen åt rätt håll samt förflytta honom Kolla innan varje
         * förflyttning at närliggande ruta ej är en vägg
         */

        //Maze.INSTANCE.setPacManDirection(direction);

        isMoving = false;
        switch (direction) {

        case 'U':
            if (goUp() && inVerticalGrid()) {
                y -= moveDistance;
                currentImgBig = charImages.get(2);
                currentImgSmall = charImages.get(6);
                isMoving = true;
            }
            break;

        case 'D':
            if (goDown() && inVerticalGrid()) {
                y += moveDistance;
                currentImgBig = charImages.get(3);
                currentImgSmall = charImages.get(7);
                isMoving = true;
            }
            break;

        case 'L':
            // if pacman went thru tunnel
            if (x - moveDistance < 0)
                x = Maze.INSTANCE.width - moveDistance;

            if (goLeft() && inHorizontalGrid()) {
                x -= moveDistance;
                currentImgBig = charImages.get(0);
                currentImgSmall = charImages.get(4);
                isMoving = true;
            }
            break;

        case 'R':
            // if pacman went thru tunnel
            if (x + cSize + moveDistance > Maze.INSTANCE.width)
                // x = -pacSize;
                x = 0;

            if (goRight() && inHorizontalGrid()) {
                x += moveDistance;
                currentImgBig = charImages.get(1);
                currentImgSmall = charImages.get(5);
                isMoving = true;
            }
            break;
        }

        // Update Pacmans position to Maze
        //Maze.INSTANCE.setPacManPos(x, y);

        // Check for food and candy at new position
        if (isMoving && x >= 0) {
            MazeBrick brick = Maze.INSTANCE.getBrick(x, y);

            if (brick.getType() == "food") {
                Maze.INSTANCE.ateFood();
                GameEngine.INSTANCE.ateFood();
                brick.changeBrick("space", Maze.INSTANCE.space);
            }

            if (brick.getType() == "candy") {
                brick.changeBrick("space", Maze.INSTANCE.space);
                // Calls gameEngine which sets highOnCandy
                GameEngine.INSTANCE.setHighOnCandy();
            }
        }

    }

    public void draw(Graphics g) {
        // Stops animation if pacman is not moving.
        if (isMoving) {
            animation = !animation;
        }

        g.drawImage(animation ? currentImgBig : currentImgSmall, x, y, cSize, cSize, null);
    }

    private String getRandomMsg() {
        String[] messages = { "HAHA! You will NEVER catch me!", "RUN FORREST RUUUUUN!",
                "Food food food.. mmmm GOOD FOOD!", "One little food, two little food, threeee little food! SO GOOD!",
                "I will NEVER STOP!", "Scary ghosts EVERYWHERE!", "Ooooh, CANDY!" };

        int randomIdx = (int) (Math.random() * messages.length);

        return messages[randomIdx];
    }

    @Override
    public void run() {

        doMove();

        // Producer / Consumer.
        if (PostMaster.getPostMaster().pacManHasMail()) {
            String ghostMsg = PostMaster.getPostMaster().recieveGhostMsg();
            System.out.println(ghostMsg);
        }

        int sendMsgChance = (int) (Math.random() * 1000);

        if (sendMsgChance == 5) {
            String msg = String.format("Pacman says: %s", getRandomMsg());
            PostMaster.getPostMaster().sendPacManMsg(msg);

        }

    }
}