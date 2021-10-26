import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FrightenedWandering implements IFrightenedBehaviour {

    String previousMove = "";
    String direction;
    Boolean onTheMove = false;
    boolean pickDirection = true;
    int targetY;
    int targetX;

    @Override
    public String FrightenedBehaviour(int x, int y) {
        /**
         * FÖr moves, Ett ghost kan egentligen bara röra sig åt 3 håll och egentligen
         * bara åt två håll i och med maze.
         */

        if (x == targetX | y == targetY) {

            targetX = -300;
            targetY = -300;

            pickDirection = true;
            onTheMove = false;

        }

        if (pickDirection) {

            direction = possibleMoves(x, y);

            pickDirection = false;

        }

        switch (direction) {
        case "right":

            while (!onTheMove) {
                targetX = x + Maze.INSTANCE.getBrick(x, y).width;

                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "right";
                return "right";
            }
            break;

        case "left":

            while (!onTheMove) {
                targetX = x - Maze.INSTANCE.getBrick(x, y).width;
                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "left";
                return "left";
            }
            break;

        case "up":

            while (!onTheMove) {
                targetY = y - Maze.INSTANCE.getBrick(x, y).height;
                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "up";
                return "up";
            }
            break;

        case "down":

            while (!onTheMove) {
                targetY = y + Maze.INSTANCE.getBrick(x, y).height;
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

        return "";

    }

    private String possibleMoves(int x, int y) {

        ArrayList<String> possibleMoves = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
        if (Maze.INSTANCE.getBrick(x + Maze.INSTANCE.gridWidth, y).isWall() | previousMove.equalsIgnoreCase("left")) {
            possibleMoves.remove("right");

        }
        if (Maze.INSTANCE.getBrick(x - Maze.INSTANCE.gridWidth, y).isWall() | previousMove.equalsIgnoreCase("right")) {
            possibleMoves.remove("left");

        }
        if (Maze.INSTANCE.getBrick(x, y + Maze.INSTANCE.gridHeight).isWall() | previousMove.equalsIgnoreCase("up")) {
            possibleMoves.remove("down");

        }
        if (Maze.INSTANCE.getBrick(x, y - Maze.INSTANCE.gridHeight).isWall() | previousMove.equalsIgnoreCase("down")) {
            possibleMoves.remove("up");

        }

        int randomInt = new Random().nextInt(possibleMoves.size());

        return possibleMoves.get(randomInt);

    }

}
