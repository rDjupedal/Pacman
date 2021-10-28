import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChaseAggresive implements IChaseBehaviour {

    int x, y, pacmanX, pacmanY;
    String previousMove = "";
    Boolean onTheMove = false;
    Boolean pickDirection = true;

    @Override
    public String chase(int x, int y) {
        // Tunnel
        if (x - Maze.INSTANCE.gridWidth < 1) {
            x = Maze.INSTANCE.width;
        }
        if (x + Maze.INSTANCE.gridWidth > Maze.INSTANCE.width) {
            x = 0;
        }

        ArrayList<String> possibleMovesArray = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
        if (Maze.INSTANCE.getBrick(x + Maze.INSTANCE.gridWidth, y).isWall() | previousMove.equalsIgnoreCase("left")) {
            possibleMovesArray.remove("right");

        }
        if (Maze.INSTANCE.getBrick(x - Maze.INSTANCE.gridWidth, y).isWall() | previousMove.equalsIgnoreCase("right")) {
            possibleMovesArray.remove("left");

        }
        if (Maze.INSTANCE.getBrick(x, y + Maze.INSTANCE.gridHeight).isWall() | previousMove.equalsIgnoreCase("up")) {
            possibleMovesArray.remove("down");

        }
        if (Maze.INSTANCE.getBrick(x, y - Maze.INSTANCE.gridHeight).isWall() | previousMove.equalsIgnoreCase("down")) {
            possibleMovesArray.remove("up");

        }

        List<Double> hypos = new ArrayList<>();

        // System.out.println("Possible moves: " + possibleMovesArray.size());
        pacmanX = Maze.INSTANCE.getPacManPos()[0];
        pacmanY = Maze.INSTANCE.getPacManPos()[1];

        // TODO! Check hypo for negative values.
        if (possibleMovesArray.size() == 1) {
            previousMove = possibleMovesArray.get(0);
            return previousMove;
        } else {

            // Works on positive values. Need to implement working on negative values
            // aswell.
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

                hypos.add(Math.sqrt(((pacmanY - tempY) * (pacmanY - tempY)) + ((pacmanX - tempX) * (pacmanX - tempX))));
            }

            previousMove = possibleMovesArray.get(smallestIndex(hypos));

            return previousMove;
        }

    }

    // private String possibleMoves(int x, int y) {

    // /**
    // * Hypo = Math.sqrt()
    // */

    // }

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
