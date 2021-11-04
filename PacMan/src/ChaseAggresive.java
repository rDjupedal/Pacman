import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Chase Aggressive ghost behavior. Chases PacMan on his exact position. All the
 * time.
 * 
 * 
 */
public class ChaseAggresive implements IChaseBehaviour {

    int pacmanX, pacmanY;
    String previousMove = "";

    @Override
    public String chase(int x, int y, ArrayList<String> possibleMovesArray) {

        // Picks possible moves taking previous move and wall into account.

        // List to store hypotenusas.
        List<Double> hypos = new ArrayList<>();

        // Getters for PacMans positions.
        pacmanX = GameEngine.INSTANCE.getPacman().getX();
        pacmanY = GameEngine.INSTANCE.getPacman().getY();
        //pacmanX = Maze.INSTANCE.getPacManPos()[0];
        //pacmanY = Maze.INSTANCE.getPacManPos()[1];

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

            // Returns the direction which has the shortest hypotenusa.
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
