import java.util.ArrayList;
import java.util.List;

public class State {
    private String CurrentState;
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

    public void notifyObservers() {
        observers.forEach(observer -> {
            observer.updateState(CurrentState);
        });
    }

    public void setCurrentState(String state) {
        CurrentState = state;
        notifyObservers();

    }

}
