import java.util.ArrayList;

/**
 * Interface of the Wakeup Behaviour for ghosts.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */
public interface IWakeUpBehaviour {
    String awokenBehaviour(int x, int y, ArrayList<String> possibleMovesArray);

}
