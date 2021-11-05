import java.util.ArrayList;
import java.util.List;

/**
 * Chases pacman on his exact position, until closer than hypotenuse 250, then
 * starts to scatter towards Bottom Right corner.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */
public class ChasePatrol implements IChaseBehaviour {

    int pacmanX, pacmanY;
    IScatterBehaviour iScatterBehaviour = new ScatterBehaviour("BR");

    /**
     * Determines PacMans position and chases PacMan until closer than hypotenuse
     * <200, then scatters away.
     * 
     * @param x                  the x position of the ghost at the time of calling
     * 
     * @param y                  the y position of the ghost at the time of calling
     * @param possibleMovesArray the array of possible moves for the ghost at the
     *                           coordinate x,y.
     */
    @Override
    public String chase(int x, int y, ArrayList<String> possibleMovesArray) {

        // List to store hypotenuse
        List<Double> hypos = new ArrayList<>();

        // Gets PacMans position.
        pacmanX = GameEngine.INSTANCE.getPacman().get_X();
        pacmanY = GameEngine.INSTANCE.getPacman().get_Y();

        double tempX = x;
        double tempY = y;

        // adds possible future moves to x or y to determine hypotenusa on future move.

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

        // Get the direction to the shortest hypo and stores it.
        String chaseDiretion = possibleMovesArray.get(smallestIndex(hypos));

        // Gets the shortest hypo and stores is
        double shortestHypo = hypos.get(smallestIndex(hypos));

        // If the shortest hypo is less than 200 units away, returns a scatterBehaviour,
        // else continous to chase PacMan
        // instead.
        if (shortestHypo < 200) {
            return iScatterBehaviour.scatter(x, y, possibleMovesArray);
        } else {
            return chaseDiretion;
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
