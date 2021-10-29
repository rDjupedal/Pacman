import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ScatterBehaviour implements IScatterBehaviour {
    int x, y, targetX, targetY;
    int scatterX;
    int scatterY;
    String direction;
    String previousMove = "";
    Boolean onTheMove = false;
    Boolean pickDirection = true;

    public ScatterBehaviour(String corner) {
        switch (corner) {
        case "TL":
            scatterX = 162;
            scatterY = -10;
            break;

        case "TR":
            scatterX = 660;
            scatterY = -10;
            break;

        case "BL":
            scatterX = 0;
            scatterY = 950;
            break;

        case "BR":
            scatterX = 810;
            scatterY = 950;
            break;

        default:
            break;
        }
    }

    @Override
    public String scatter(int x, int y) {

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

        List<Double> hypos = new ArrayList<>();

        if (possibleMovesArray.size() == 1) {
            previousMove = possibleMovesArray.get(0);
            return previousMove;
        } else {

            double tempX = x;
            double tempY = y;

            for (int i = 0; i < possibleMovesArray.size(); i++) {
                if (possibleMovesArray.get(i).equals("right")) {
                    tempX = x + Maze.INSTANCE.gridWidth;
                } else if (possibleMovesArray.get(i).equals("left")) {
                    tempX = x - Maze.INSTANCE.gridWidth;
                } else if (possibleMovesArray.get(i).equals("down")) {
                    tempY = y + Maze.INSTANCE.gridHeight;
                } else if (possibleMovesArray.get(i).equals("up")) {
                    tempY = y - Maze.INSTANCE.gridHeight;
                }

                hypos.add(Math
                        .sqrt(((scatterY - tempY) * (scatterY - tempY)) + ((scatterX - tempX) * (scatterX - tempX))));
            }

            previousMove = possibleMovesArray.get(smallestIndex(hypos));

            return previousMove;
        }

    }

    private int smallestIndex(List<Double> array) {
        int idx = 0;
        Double min = array.get(idx);

        for (int i = 0; i < array.size(); i++) {

            if (array.get(i) <= min) {
                min = array.get(i);
                idx = i;

            }

        }

        return idx;

    }

}
