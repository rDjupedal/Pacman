import java.util.ArrayList;

/**
 * Interface of the Chase Behaviour for ghosts.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */
public interface IChaseBehaviour {
    String chase(int x, int y, ArrayList<String> list);
}
