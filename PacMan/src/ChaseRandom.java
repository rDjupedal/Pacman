import java.util.ArrayList;
import java.util.Arrays;
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

        /**
         * FÖr moves, Ett ghost kan egentligen bara röra sig åt 3 håll och egentligen
         * bara åt två håll i och med maze.
         */

        if (x == targetX | y == targetY) {

            targetX = -300;
            targetY = -300;

            pickDirection = true;
            onTheMove = false;
            System.out.println("Pickdirection Active");
        }

        if (pickDirection) {

            direction = possibleMoves(x, y);
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

    private String possibleMoves(int x, int y) {

        ArrayList<String> possibleMoves = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
        if (Maze2.INSTANCE.getBrick(x + 30, y).isWall()) {
            possibleMoves.remove("right");

        }
        if (Maze2.INSTANCE.getBrick(x - 30, y).isWall()) {
            possibleMoves.remove("left");

        }
        if (Maze2.INSTANCE.getBrick(x, y + 30).isWall()) {
            possibleMoves.remove("down");

        }
        if (Maze2.INSTANCE.getBrick(x, y - 30).isWall()) {
            possibleMoves.remove("up");

        }

        if (previousMove != null) {
            switch (previousMove) {
            case "up":
                possibleMoves.remove("down");
                break;

            case "down":
                possibleMoves.remove("up");
                break;
            case "left":
                possibleMoves.remove("right");
                break;

            case "right":
                possibleMoves.remove("left");
                break;
            default:
                break;
            }

        }

        int randomInt = new Random().nextInt(possibleMoves.size());

        return possibleMoves.get(randomInt);

    }

}

/**
 * Psuedokod Välj riktning, Gå ett steg fram väljriktning gå ett steg fram
 * 
 */