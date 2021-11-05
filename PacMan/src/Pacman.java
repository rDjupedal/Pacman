import java.awt.*;
import java.awt.event.KeyEvent;
import javax.imageio.*;
import java.awt.image.*;
import java.io.IOException;

/**
 * Pacman class
 * @author Rasmus Djupedal, Tobias Liljeblad
 */
class Pacman extends LivingCharacter implements Runnable {

    // . means no lastKey waiting
    char lastKey = '.';

    BufferedImage currentImgBig;
    BufferedImage currentImgSmall;

    /**
     * Constructor. Sets the position of Pacman and loads his images
     * @param x
     * @param y
     */
    protected Pacman(int x, int y) {
        this.x = x;
        this.y = y;
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
            System.out.println("Error loading Pacman images: " + e.getMessage());
        }
    }

    /**
     * Sets the initial image for Pacman
     */
    protected void setInitImage() {
        currentImgBig = charImages.get(1);
        currentImgSmall = charImages.get(4);
    }

    /**
     * Called when a key is pressed
     * @param e KeyEvent the key event
     */
    @Override
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
            // Escape key, pauses the game
            GameEngine.INSTANCE.isRunning = false;
            break;
        case 80:
            // P key, pauses the game
            GameEngine.INSTANCE.isRunning = false;
        }
    }

    /**
     * Checks if legal and sets the direction for Pacman
     */
    @Override
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

    /**
     * Draw Pacman facing correct direction and check for wall before moving him
     */
    @Override
    protected void moveCharacter() {
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
                // Call gameEngine and set highOnCandy
                GameEngine.INSTANCE.setHighOnCandy();
            }
        }

    }

    /**
     * Draw Pacman
     * @param g Graphics object
     */
    @Override
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