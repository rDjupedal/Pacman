import java.util.ArrayList;

/**
 * Interface of the Scatter Behaviour for ghosts.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */
public interface IScatterBehaviour {
    String scatter(int x, int y, ArrayList<String> possibleMovesArray);

}
