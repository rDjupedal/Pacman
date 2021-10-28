import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Chases pacman on his exact position, until closer than hypotenusa 250, then
 * starts to scatter towards Bottom Right corner.
 * 
 */
public class ChasePatrol implements IChaseBehaviour {

    int pacmanX, pacmanY;
    String previousMove = "";
    IScatterBehaviour iScatterBehaviour = new ScatterBehaviour("BR");

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

        // List to store hypotenusas.
        List<Double> hypos = new ArrayList<>();

        // Gets PacMans position.
        pacmanX = Maze.INSTANCE.getPacManPos()[0];
        pacmanY = Maze.INSTANCE.getPacManPos()[1];

        double tempX = x;
        double tempY = y;

        // adds possible future moves to x or y to determine hypotenusa on future move.

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
            // calculates hypotenusa on all possible moves
            hypos.add(Math.sqrt(((pacmanY - tempY) * (pacmanY - tempY)) + ((pacmanX - tempX) * (pacmanX - tempX))));
        }

        // Get the direction to the shortest hypo and stores it.
        previousMove = possibleMovesArray.get(smallestIndex(hypos));

        // Gets the shortest hypo and stores is
        double shortestHypo = hypos.get(smallestIndex(hypos));

        // If the shortest hypo is less than 200 units away, returns a scatterBehaviour,
        // else continous to chase PacMan
        // instead.
        if (shortestHypo < 200) {
            return iScatterBehaviour.scatter(x, y);
        } else {
            return previousMove;
        }

    }

    /**
     * Takes an array and returns the index of the shortest double of the array.
     * 
     * @param array
     * @return index of the shortest Double in array.
     */
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
