import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FrightenedWandering implements IFrightenedBehaviour {

    String previousMove = "";

    @Override
    public String FrightenedBehaviour(int x, int y, ArrayList<String> possibleMovesArray) {

        int randomInt = new Random().nextInt(possibleMovesArray.size());

        previousMove = possibleMovesArray.get(randomInt);
        return previousMove;

    }

}
