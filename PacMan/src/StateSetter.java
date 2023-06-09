import java.util.ArrayList;
import java.util.List;

/**
 * The Observable in the Observer pattern. Used for settign the states of the
 * ghosts to enable them to change movement behaviour.
 * 
 * @author Tobias Liljeblad & Rasumus Djupedal
 */
public class StateSetter {

    private String currentState;
    private String previousState = "";
    private final List<StateObserver> observers;

    /**
     * Constructor, createing the ArrayList that observers will be added to.
     */
    public StateSetter() {
        observers = new ArrayList<>();

    }

    /**
     * Adds an observer to the observerlist.
     * 
     * @param observer Concrete class of StateObserver
     */
    public void addObserver(final StateObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observere from the observerList.
     * 
     * @param observer Instance of StateObserver
     */
    public void removeObserver(final StateObserver observer) {
        observers.remove(observer);
    }

    /**
     * Method responsible for notifying the observers that a change has occured.
     */
    private void notifyObservers() {
        observers.forEach(observer -> {
            observer.updateState(currentState);
        });
    }

    /**
     * Private method that is responsible for changing the state and storing the
     * current and previous state. Checks if a new state is coming and notifies the
     * observers.
     * 
     * @param newState a new change in state.
     */
    private void setCurrentState(final String newState) {

        if (!isCurrentState(newState)) {
            previousState = currentState;
            currentState = newState;
            notifyObservers();

        }

    }

    /**
     * If the state needs to be reversed, this method handles that. (Depreciated?)
     */
    public void setPreviousState() {
        setCurrentState(previousState);
    }

    /**
     * Checks if the new State is the current state.
     * 
     * @param newState Ghost state
     * @return boolean if newState is equal to currentState
     */
    private boolean isCurrentState(final String newState) {
        return newState.equalsIgnoreCase(currentState);
    }

    /**
     * Checks if scatter is current state.
     * 
     * @return true if current state is scatter.
     */
    protected boolean isScatter() {
        return currentState.equalsIgnoreCase("scatter");
    }

    /**
     * Checks if chase is current state.
     * 
     * @return true if current state is chase.
     */
    protected boolean isChase() {
        return currentState.equalsIgnoreCase("chase");
    }

    /**
     * Checks if fright is current state.
     * 
     * @return true if current state is fright.
     */
    protected boolean isFright() {
        return currentState.equalsIgnoreCase("fright");
    }

    /**
     * 
     * @return true if current state is Wakeup
     */
    protected boolean isWakeUp() {
        return currentState.equalsIgnoreCase("wakeup");
    }

    /**
     * Sets scatter state.
     */
    protected void setScatter() {
        setCurrentState("scatter");
    }

    /**
     * Sets chase state.
     */
    protected void setChase() {
        setCurrentState("chase");
    }

    /**
     * Sets fright state and stores previous state.
     */
    protected void setFright() {
        previousState = currentState;
        setCurrentState("fright");
    }

    /**
     * Opens ghost living room door and set state to Wakeup.
     */
    protected void setWakeUp() {

        Maze.INSTANCE.openDoor();
        setCurrentState("wakeup");

    }

}
