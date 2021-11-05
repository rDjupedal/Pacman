import java.util.ArrayList;
import java.util.List;

/**
 * Chases pacmans future position by adding 3 grids to his direction.
 * 
 * @author Tobias Liljeblad and Rasmus Djupedal
 */
public class ChaseAmbush implements IChaseBehaviour {

    int pacmanX, pacmanY;
    char pacManDirection;

    /**
     * Determins pacmans "future" position and chases after that target coordinate.
     * checks the shortest hypotenuse of all possible moves.
     * 
     * @param x                  the x position of the ghost at the time of calling
     * 
     * @param y                  the y position of the ghost at the time of calling
     * @param possibleMovesArray the array of possible moves for the ghost at the
     *                           coordinate x,y.
     */
    @Override
    public String chase(int x, int y, ArrayList<String> possibleMovesArray) {

        // List of Hypotenuse
        List<Double> hypos = new ArrayList<>();

        int pacmanX = GameEngine.INSTANCE.getPacman().get_X();
        int pacmanY = GameEngine.INSTANCE.getPacman().get_Y();

        pacManDirection = GameEngine.INSTANCE.getPacman().direction;

        // Adds 3 steps in pacmans direction.
        switch (pacManDirection) {
        case 'U':

            pacmanY = pacmanY + (Maze.INSTANCE.gridHeight * 3);

            break;
        case 'D':

            pacmanY = pacmanY - (Maze.INSTANCE.gridHeight * 3);

            break;

        case 'L':
            pacmanX = pacmanX - (Maze.INSTANCE.gridWidth * 3);

            break;

        case 'R':
            pacmanX = pacmanX + (Maze.INSTANCE.gridWidth * 3);

            break;

        default:
            break;
        }

        // If only 1 possible move, pick that one.

        if (possibleMovesArray.size() == 1) {
            return possibleMovesArray.get(0);
        } else {

            double tempX = x;
            double tempY = y;

            // adds possible future moves to x or y to determine Hypotenuse on future move.

            for (int i = 0; i < possibleMovesArray.size(); i++) {
                switch (possibleMovesArray.get(i)) {
                case "right":
                    tempX = x + Maze.INSTANCE.gridWidth;

                    break;
                case "left":
                    tempX = x - Maze.INSTANCE.gridWidth;
                    break;
                case "down":
                    tempY = y + Maze.INSTANCE.gridHeight;
                    break;
                case "up":
                    tempY = y - Maze.INSTANCE.gridHeight;
                    break;

                default:
                    break;
                }

                // calculates Hypotenuse on all possible moves

                hypos.add(Math.sqrt(((pacmanY - tempY) * (pacmanY - tempY)) + ((pacmanX - tempX) * (pacmanX - tempX))));
            }
            // Sends all Hypotenuse to a "shortest Hypotenuse" check. Stores in
            // previousmove.
            return possibleMovesArray.get(smallestIndex(hypos));

        }

    }

    /**
     * Takes an array and returns the index of the shortest double of the array.
     * 
     * @param array of hypotenuse as Double
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
