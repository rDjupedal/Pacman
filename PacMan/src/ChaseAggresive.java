import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChaseAggresive implements IChaseBehaviour {

    int x, y, pacmanX, pacmanY, scatterX, scatterY, targetX, targetY;
    String direction;
    String previousMove = "";
    Boolean onTheMove = false;
    Boolean pickDirection = true;

    @Override
    public String chase(int x, int y) {

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
                targetX = x + Maze2.INSTANCE.getBrick(x, y).width;

                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "right";
                return "right";
            }
            break;

        case "left":

            while (!onTheMove) {
                targetX = x - Maze2.INSTANCE.getBrick(x, y).width;
                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "left";
                return "left";
            }
            break;

        case "up":

            while (!onTheMove) {
                targetY = y - Maze2.INSTANCE.getBrick(x, y).height;
                onTheMove = true;
            }

            while (onTheMove) {

                previousMove = "up";
                return "up";
            }
            break;

        case "down":

            while (!onTheMove) {
                targetY = y + Maze2.INSTANCE.getBrick(x, y).height;
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
        if (Maze2.INSTANCE.getBrick(x + 30, y).isWall() | previousMove.equalsIgnoreCase("left")) {
            possibleMovesArray.remove("right");

        }
        if (Maze2.INSTANCE.getBrick(x - 30, y).isWall() | previousMove.equalsIgnoreCase("right")) {
            possibleMovesArray.remove("left");

        }
        if (Maze2.INSTANCE.getBrick(x, y + 30).isWall() | previousMove.equalsIgnoreCase("up")) {
            possibleMovesArray.remove("down");

        }
        if (Maze2.INSTANCE.getBrick(x, y - 30).isWall() | previousMove.equalsIgnoreCase("down")) {
            possibleMovesArray.remove("up");

        }

        // FÖR VARJE ELEMENT I POSSIBLE MOVES, MÅSTE VI RÄKNA HYPO, ANVÄNDA DEN KORTASTE
        // OFTAST ÄR DET BARA TVÅ, MEN KAN VAR ATRE I STARTEN.
        List<Double> hypos = new ArrayList<>();

        System.out.println("Possible moves: " + possibleMovesArray.size());
        pacmanX = 642;
        pacmanY = 900;

        // KOllar vad som är närmast.
        // TODO! lägga in att den kan jämföra med tre samt att den kan räkna hypotenusa
        // på negativa tal.
        if (possibleMovesArray.size() == 1) {
            return possibleMovesArray.get(0);
        } else {

            double tempX = x;
            double tempY = y;

            for (int i = 0; i < 2; i++) {
                if (possibleMovesArray.get(i).equals("right")) {
                    tempX = x + 30;
                } else if (possibleMovesArray.get(i).equals("left")) {
                    tempX = x - 30;
                } else if (possibleMovesArray.get(i).equals("down")) {
                    tempY = y + 30;
                } else if (possibleMovesArray.get(i).equals("up")) {
                    tempY = y - 30;
                }

                hypos.add(Math.sqrt(((pacmanY - tempY) * (pacmanY - tempY)) + ((pacmanX - tempX) * (pacmanX - tempX))));
            }

            return possibleMovesArray.get(smallestIndex(hypos));
        }

        // double XR = x+30;
        // double XL = x-30;
        // double YD = y+30;
        // double YU = y-30;

        // double a = pacmanY - y;
        // double b = pacmanX - x;
        // double hypo = Math.sqrt((a * a) + (b * b));

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
