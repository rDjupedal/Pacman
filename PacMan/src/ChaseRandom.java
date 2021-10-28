import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ChaseRandom implements IChaseBehaviour {

    String previousMove = "";
    String direction;
    Boolean onTheMove = false;
    boolean pickDirection = true;
    int targetY;
    int targetX;

    @Override
    public String chase(int x, int y) {

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

        int randomInt = new Random().nextInt(possibleMovesArray.size());

        previousMove = possibleMovesArray.get(randomInt);
        return previousMove;

    }

}
