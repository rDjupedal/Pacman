import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FrightenedWandering implements IFrightenedBehaviour {

    String previousMove = "";

    @Override
    public String FrightenedBehaviour(int x, int y) {

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

        previousMove = possibleMoves.get(randomInt);
        return previousMove;

    }

}
