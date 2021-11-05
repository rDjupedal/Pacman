import java.util.ArrayList;
import java.util.List;

/**
 * State and behaviour used when the ghosts move from the livingroom into the
 * maze.
 * 
 * @author Tobias Liljeblad and Rasmus Djupedal
 */
public class WakeUpBehaviour implements IWakeUpBehaviour {
    int x, y;
    int wakeUpX = 420;
    int wakeUpY = 330;

    /**
     * Takes the ghosts current direction and moves out of the living room. Once in
     * the maze, a change in state wikl occur and the ghosts will more to another
     * state.
     * 
     * @param x                  the x position of the ghost at the time of calling
     * 
     * @param y                  the y position of the ghost at the time of calling
     * @param possibleMovesArray the array of possible moves for the ghost at the
     *                           coordinate x,y.
     */
    @Override
    public String awokenBehaviour(int x, int y, ArrayList<String> possibleMovesArray) {

        // List of hypotenuse
        List<Double> hypos = new ArrayList<>();

        // If only one possible move,return that move.
        if (possibleMovesArray.size() == 1) {
            return possibleMovesArray.get(0);
        } else {
            this.x = x;
            this.y = y;

            double tempX = x;
            double tempY = y;

            // For each possible move, determine the hypotenuse of that move
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

                // Add all hypotenuse to a list.
                hypos.add(Math.sqrt(((wakeUpY - tempY) * (wakeUpY - tempY)) + ((wakeUpX - tempX) * (wakeUpX - tempX))));
            }

            // Return the direction which has the shortest hypotenuse as next move.
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
