import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * runs around in the maze, randomly. No real target, but picks a random
 * direction at each possible point.
 */
public class ChaseRandom implements IChaseBehaviour {

    String previousMove = "";
    String direction;

    @Override
    public String chase(int x, int y) {

        // Picks possible moves taking previous move and wall into account.
        ArrayList<String> possibleMovesArray = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
        if (previousMove.equalsIgnoreCase("left") || Maze.INSTANCE.getBrick(x + Maze.INSTANCE.gridWidth, y).isWall()) {
            possibleMovesArray.remove("right");

        }
        if (previousMove.equalsIgnoreCase("right") || Maze.INSTANCE.getBrick(x - Maze.INSTANCE.gridWidth, y).isWall()) {
            possibleMovesArray.remove("left");

        }
        if (Maze.INSTANCE.getBrick(x, y + Maze.INSTANCE.gridHeight).isWall() | previousMove.equalsIgnoreCase("up")) {
            possibleMovesArray.remove("down");

        }
        if (Maze.INSTANCE.getBrick(x, y - Maze.INSTANCE.gridHeight).isWall() | previousMove.equalsIgnoreCase("down")) {
            possibleMovesArray.remove("up");

        }

        // Craetes a random int between 0 and amount of possible moves.
        int randomInt = new Random().nextInt(possibleMovesArray.size());

        // Stores direction to next time.
        previousMove = possibleMovesArray.get(randomInt);
        // Returns direction to caller.
        return previousMove;

    }

}
