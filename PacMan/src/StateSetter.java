import java.util.ArrayList;
import java.util.List;

public class StateSetter {
    private String CurrentState;
    private String previousState = "";
    private final List<StateObserver> observers;

    public StateSetter() {
        observers = new ArrayList<>();

    }

    public void addObserver(StateObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(StateObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        observers.forEach(observer -> {
            observer.updateState(CurrentState);
        });
    }

    public void setCurrentState(String newState) {

        if (!isCurrentState(newState)) {
            previousState = CurrentState;
            CurrentState = newState;
            notifyObservers();

        }

    }

    public void setPreviousState() {
        setCurrentState(previousState);
    }

    private boolean isCurrentState(String newState) {
        return newState.equalsIgnoreCase(CurrentState);
    }

    protected boolean isScatter() {
        return CurrentState.equalsIgnoreCase("scatter");
    }

    protected boolean isChase() {
        return CurrentState.equalsIgnoreCase("chase");
    }

    protected boolean isFright() {
        return CurrentState.equalsIgnoreCase("fright");
    }

    protected boolean isWakeUp() {
        return CurrentState.equalsIgnoreCase("wakeup");
    }

    protected void setScatter() {
        setCurrentState("scatter");
    }

    protected void setChase() {
        setCurrentState("chase");
    }

    protected void setFright() {
        previousState = CurrentState;
        CurrentState = "fright";
        notifyObservers();
    }

    protected void setWakeUp() {
        CurrentState = "wakeup";
        Maze.INSTANCE.openDoor();
        notifyObservers();
    }

    // protected void releaseGhost(ArrayList<Ghost> deadGhosts) {

    // Maze.INSTANCE.openDoor();
    // deadGhosts.forEach(ghost -> {
    // ghost.updateState("wakeup");
    // });

    // }

}
