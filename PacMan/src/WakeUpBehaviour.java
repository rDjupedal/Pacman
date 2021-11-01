import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WakeUpBehaviour implements IWakeUpBehaviour {
    int x, y;
    int wakeUpX = 420;
    int wakeUpY = 330;
    String direction;
    String previousMove = "";
    Boolean onTheMove = false;
    Boolean pickDirection = true;
    Ghost ghost;

    public WakeUpBehaviour(Ghost ghost) {
        this.ghost = ghost;

    }

    @Override
    public String awokenBehaviour(int x, int y, ArrayList<String> possibleMovesArray) {
        this.x = x;
        this.y = y;

        List<Double> hypos = new ArrayList<>();

        if (possibleMovesArray.size() == 1) {
            previousMove = possibleMovesArray.get(0);
            return previousMove;
        } else {

            // Works on positive values. Need to implement working on negative values
            // aswell.
            double tempX = x;
            double tempY = y;

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

                hypos.add(Math.sqrt(((wakeUpY - tempY) * (wakeUpY - tempY)) + ((wakeUpX - tempX) * (wakeUpX - tempX))));
            }

            previousMove = possibleMovesArray.get(smallestIndex(hypos));

            return previousMove;
        }

    }

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
