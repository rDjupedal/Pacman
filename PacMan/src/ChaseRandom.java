import java.util.Random;

public class ChaseRandom implements IChaseBehaviour {

    String[] directions = { "up", "down", "left", "right" };
    String previousMove;
    String CurrentMove;

    @Override
    public String chase() {
        int randomInt = new Random().nextInt(directions.length);

        CurrentMove = directions[randomInt];

        return directions[randomInt];

    }

}
