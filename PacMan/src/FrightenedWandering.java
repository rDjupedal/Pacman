import java.util.ArrayList;
import java.util.Random;

/**
 * Runs around in the maze, randomly. No real target, but picks a random
 * direction at each possible point. Used when PacMan has eaten a power up
 * Candy.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */
public class FrightenedWandering implements IFrightenedBehaviour {

    /**
     * Picks a random next step based on the possible moves.
     * 
     * @param x                  the x position of the ghost at the time of calling
     * 
     * @param y                  the y position of the ghost at the time of calling
     * @param possibleMovesArray the array of possible moves for the ghost at the
     *                           coordinate x,y.
     */
    @Override
    public String FrightenedBehaviour(int x, int y, ArrayList<String> possibleMovesArray) {

        int randomInt = new Random().nextInt(possibleMovesArray.size());

        return possibleMovesArray.get(randomInt);

    }

}
