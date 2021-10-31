import java.util.ArrayList;
import java.util.List;

public class State {
    private String CurrentState;
    private String previousState = "";
    private final List<StateObserver> observers;

    public State() {
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

    public void setCurrentState(String state) {
        if (!isNewState(state)) {
            previousState = CurrentState;
            CurrentState = state;
            notifyObservers();

        }

    }

    private boolean isNewState(String state) {
        return state.equalsIgnoreCase(previousState);
    }

    public boolean isScatter() {
        return CurrentState.equalsIgnoreCase("scatter");
    }

    public boolean isChase() {
        return CurrentState.equalsIgnoreCase("chase");
    }

    public boolean isFright() {
        return CurrentState.equalsIgnoreCase("fright");
    }

}
