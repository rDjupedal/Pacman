import java.util.Random;

public class ChaseRandom implements IChaseBehaviour {

    String[] directions = { "up", "down", "left", "right" };
    String previousMove;
    String direction;
    Boolean onTheMove = false;
    boolean pickDirection = true;
    int targetY;
    int targetX;

    @Override
    public String chase(int x, int y) {
        int randomInt = new Random().nextInt(directions.length);

        System.out.println("x: " + x + " TargetX:" + targetX);
        System.out.println("y: " + y + " TargetY:" + targetY);
        if (x == targetX | y == targetY) {

            targetX = -300;
            targetY = -300;

            pickDirection = true;
            onTheMove = false;
            System.out.println("Pickdirection Active");
        }

        if (pickDirection) {
            direction = directions[randomInt];

            System.out.println("Picked Direction " + direction);
            pickDirection = false;

        }

        switch (direction) {
        case "right":

            while (!onTheMove) {
                targetX = x + Maze2.INSTANCE.getBrick(x, y).width;
                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "right";
                return "right";
            }
            break;

        case "left":

            while (!onTheMove) {
                targetX = x - Maze2.INSTANCE.getBrick(x, y).width;
                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "left";
                return "left";
            }
            break;

        case "up":

            while (!onTheMove) {
                targetY = y - Maze2.INSTANCE.getBrick(x, y).height;
                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "up";
                return "up";
            }
            break;

        case "down":

            while (!onTheMove) {
                targetY = y + Maze2.INSTANCE.getBrick(x, y).height;
                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "down";
                return "down";
            }
            break;
        default:
            break;
        }

        // GOing right

        return "";

    }

}

/**
 * Psuedokod Välj riktning, Gå ett steg fram väljriktning gå ett steg fram
 * 
 */