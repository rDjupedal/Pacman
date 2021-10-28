import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WakeUpBehaviour implements IWakeUpBehaviour {
    int x, y;
    int targetX, targetY;
    int wakeUpX = 420;
    int wakeUpY = 30;
    String direction;
    String previousMove = "";
    Boolean onTheMove = false;
    Boolean pickDirection = true;
    Ghost ghost;

    public WakeUpBehaviour(Ghost ghost) {
        this.ghost = ghost;

    }

    @Override
    public String awokenBehaviour(int x, int y) {

        if (x == 390 && y == 330 || x == 420 && y == 330) {
            ghost.setChase();

        }

        if (x == targetX | y == targetY) {

            targetX = -300;
            targetY = -300;

            pickDirection = true;
            onTheMove = false;
            // System.out.println("Pick direction Active");
        }

        if (pickDirection) {

            direction = possibleMoves(x, y);
            // System.out.println("Picked Direction " + direction);
            pickDirection = false;

        }

        switch (direction) {
        case "right":

            while (!onTheMove) {
                targetX = x + Maze.INSTANCE.gridWidth;

                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "right";
                return "right";
            }
            break;

        case "left":

            while (!onTheMove) {
                targetX = x - Maze.INSTANCE.gridWidth;
                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "left";
                return "left";
            }
            break;

        case "up":

            while (!onTheMove) {
                targetY = y - Maze.INSTANCE.gridHeight;
                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "up";
                return "up";
            }
            break;

        case "down":

            while (!onTheMove) {
                targetY = y + Maze.INSTANCE.gridHeight;
                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "down";
                return "down";
            }
            break;
        default:
            break;
        }

        return "";

    }

    private String possibleMoves(int x, int y) {

        /**
         * Hypo = Math.sqrt()
         */

        // TODO! Byta ut +30 till riktiga nummer från mazeBrick.

        ArrayList<String> possibleMovesArray = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
        if (Maze.INSTANCE.getBrick(x + Maze.INSTANCE.gridWidth, y).isWall() | previousMove.equalsIgnoreCase("left")) {
            possibleMovesArray.remove("right");

        }
        if (Maze.INSTANCE.getBrick(x - Maze.INSTANCE.gridWidth, y).isWall() | previousMove.equalsIgnoreCase("right")) {
            possibleMovesArray.remove("left");

        }
        if (Maze.INSTANCE.getBrick(x, y + Maze.INSTANCE.gridHeight).isWall() | previousMove.equalsIgnoreCase("up")) {
            possibleMovesArray.remove("down");

        }
        if (Maze.INSTANCE.getBrick(x, y - Maze.INSTANCE.gridHeight).isWall() | previousMove.equalsIgnoreCase("down")) {
            possibleMovesArray.remove("up");

        }

        List<Double> hypos = new ArrayList<>();

        // DEBUG And Fake PAcman Positions. To be removed.
        // System.out.println("Possible moves: " + possibleMovesArray.size());

        // TODO! Check hypo for negative values.
        if (possibleMovesArray.size() == 1) {
            return possibleMovesArray.get(0);
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

            return possibleMovesArray.get(smallestIndex(hypos));
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
