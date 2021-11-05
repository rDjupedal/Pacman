import java.util.ArrayList;
import java.util.List;

/**
 * Chase Aggressive ghost behavior. Chases PacMan on his exact position. All the
 * time.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */
public class ChaseAggressive implements IChaseBehaviour {

    /**
     * Takes the current position and decides the next step forward based on the
     * shortest hypotenuse to Pacman.
     * 
     * @param x                  the x position of the ghost at the time of calling
     * @param y                  the y position of the ghost at the time of calling
     * @param possibleMovesArray the array of possible moves for the ghost at the
     *                           coordinate x,y.
     * 
     */
    @Override
    public String chase(int x, int y, ArrayList<String> possibleMovesArray) {

        // Picks possible moves taking previous move and wall into account.

        // List to store hypotenuse.
        List<Double> hypos = new ArrayList<>();

        // Getters for PacMans positions.
        int pacmanX = GameEngine.INSTANCE.getPacman().get_X();
        int pacmanY = GameEngine.INSTANCE.getPacman().get_Y();

        // If only 1 possible move, pick that one.
        if (possibleMovesArray.size() == 1) {

            return possibleMovesArray.get(0);
        } else {

            double tempX = x;
            double tempY = y;

            // adds possible future moves to x or y to determine hypotenuse on future move.
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

                // calculates hypotenuse on all possible moves
                hypos.add(Math.sqrt(((pacmanY - tempY) * (pacmanY - tempY)) + ((pacmanX - tempX) * (pacmanX - tempX))));
            }

            // Sends all hypotenuse to a "shortest hypotenuse" check and returns the
            // shortest one.

            return possibleMovesArray.get(smallestIndex(hypos));

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
