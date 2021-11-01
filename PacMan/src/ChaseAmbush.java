import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Chases pacmans future position by adding 3 grids to his direction.
 */
public class ChaseAmbush implements IChaseBehaviour {

    int pacmanX, pacmanY;
    char pacManDirection;
    String previousMove = "";

    @Override
    public String chase(int x, int y, ArrayList<String> possibleMovesArray) {

        // Picks possible moves taking previous move and wall into account.

        // List of hypotenusas
        List<Double> hypos = new ArrayList<>();

        // Getter of pacmans Direction, and makes calculations on his "future"
        // positions.
        pacManDirection = Maze.INSTANCE.getPacManDirection();
        switch (pacManDirection) {
        case 'U':
            // This is a special bug in Pacman, so when pacman is going up, you need to also
            // add a bit to the left of him.
            pacmanX = Maze.INSTANCE.getPacManPos()[0] - (Maze.INSTANCE.gridWidth * 2);
            pacmanY = Maze.INSTANCE.getPacManPos()[1] + (Maze.INSTANCE.gridHeight * 3);

            break;
        case 'D':
            pacmanX = Maze.INSTANCE.getPacManPos()[0];
            pacmanY = Maze.INSTANCE.getPacManPos()[1] - (Maze.INSTANCE.gridHeight * 3);

            break;

        case 'L':
            pacmanX = Maze.INSTANCE.getPacManPos()[0] - (Maze.INSTANCE.gridWidth * 3);
            pacmanY = Maze.INSTANCE.getPacManPos()[1];

            break;

        case 'R':
            pacmanX = Maze.INSTANCE.getPacManPos()[0] + (Maze.INSTANCE.gridWidth * 3);
            pacmanY = Maze.INSTANCE.getPacManPos()[1];

            break;

        default:
            break;
        }

        // If only 1 possible move, pick that one.

        if (possibleMovesArray.size() == 1) {
            previousMove = possibleMovesArray.get(0);
            return previousMove;
        } else {

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
            // Sends all hypotenusas to a "shortest hypotenusa" check. Stores in
            // previousmove.
            previousMove = possibleMovesArray.get(smallestIndex(hypos));

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
