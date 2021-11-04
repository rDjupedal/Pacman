
import java.util.ArrayList;
import java.util.LinkedList;

public class PostMaster {
    private LinkedList<String> ghostMessages = new LinkedList<>();
    private LinkedList<String> pacManMessages = new LinkedList<>();

    private static PostMaster postMaster = null;

    protected PostMaster() {

    }

    public static synchronized PostMaster getPostMaster() {
        if (postMaster == null) {
            postMaster = new PostMaster();
        }
        return postMaster;

    }

    protected boolean sendGhostMessage(String msg) {

        synchronized (ghostMessages) {
            return ghostMessages.add(msg);

        }

    }

    protected synchronized String recieveGhostMsg() {
        synchronized (ghostMessages) {
            if (!ghostMessages.isEmpty()) {
                return ghostMessages.pop();
            } else
                return "No messages";
        }

    }

    protected synchronized boolean sendPacManMsg(String msg) {

        synchronized (pacManMessages) {
            return pacManMessages.add(msg);
        }

    }

    protected synchronized String recievePacManMsg() {

        synchronized (pacManMessages) {
            if (!pacManMessages.isEmpty()) {
                return pacManMessages.pop();

            } else
                return "No messages from PacMan";
        }

    }

    protected synchronized boolean pacManHasMail() {
        return !ghostMessages.isEmpty();
    }

    protected synchronized boolean ghostHasMail() {
        return !pacManMessages.isEmpty();
    }

}
