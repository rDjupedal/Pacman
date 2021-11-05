import java.util.ArrayList;
import java.util.Random;

/**
 * runs around in the maze, randomly. No real target, but picks a random
 * direction at each possible point.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */
public class ChaseRandom implements IChaseBehaviour {

    /**
     * Picks a random direction of the possible directions.
     * 
     * @param x                  the x position of the ghost at the time of calling
     * 
     * @param y                  the y position of the ghost at the time of calling
     * @param possibleMovesArray the array of possible moves for the ghost at the
     *                           coordinate x,y.
     */
    @Override
    public String chase(int x, int y, ArrayList<String> possibleMovesArray) {

        // Creates a random int between 0 and amount of possible moves.
        int randomInt = new Random().nextInt(possibleMovesArray.size());

        // Returns direction to caller.

        return possibleMovesArray.get(randomInt);

    }

}
