import java.util.ArrayList;
import java.util.List;

/**
 * The behavoir when the Ghosts has state Scatter Activated. Depending on which
 * ghost, they move towards a specific corner and circles that area.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */
public class ScatterBehaviour implements IScatterBehaviour {
    int x, y, scatterX, scatterY;

    /**
     * Depending on which corner the ghosts has assigned, scatter Target is adjusted
     * in the constructor.
     * 
     * @param corner the corner of which the ghost should aim for in Scatter state.
     *               TL = Top Left etc.
     */
    public ScatterBehaviour(String corner) {
        switch (corner) {
        case "TL":
            scatterX = 162;
            scatterY = -10;
            break;

        case "TR":
            scatterX = 780;
            scatterY = -105;
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

    /**
     * Determines the shortest route to the assigned scatter Corner.
     * 
     * @param x                  the x position of the ghost at the time of calling
     * 
     * @param y                  the y position of the ghost at the time of calling
     * @param possibleMovesArray the array of possible moves for the ghost at the
     *                           coordinate x,y.
     */
    @Override
    public String scatter(int x, int y, ArrayList<String> possibleMovesArray) {

        // Create list for hypotenuse
        List<Double> hypos = new ArrayList<>();

        // if only one possible move, return that one.
        if (possibleMovesArray.size() == 1) {
            return possibleMovesArray.get(0);
        } else {

            double tempX = x;
            double tempY = y;

            // add future possible move to all moves.
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

                // Calculate the hypotenuse and add to lost.
                hypos.add(Math
                        .sqrt(((scatterY - tempY) * (scatterY - tempY)) + ((scatterX - tempX) * (scatterX - tempX))));
            }

            // REturn the shortest hypotenuse as direction.
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
