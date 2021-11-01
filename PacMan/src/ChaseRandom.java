import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * runs around in the maze, randomly. No real target, but picks a random
 * direction at each possible point.
 */
public class ChaseRandom implements IChaseBehaviour {

    String previousMove = "";
    String direction;

    @Override
    public String chase(int x, int y, ArrayList<String> possibleMovesArray) {

        // Craetes a random int between 0 and amount of possible moves.
        int randomInt = new Random().nextInt(possibleMovesArray.size());

        // Stores direction to next time.
        previousMove = possibleMovesArray.get(randomInt);
        // Returns direction to caller.
        return previousMove;

    }

}
