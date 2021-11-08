
import java.util.LinkedList;

/**
 * Singleton Class that handles a producer/consumer problem. Messages are sent
 * from and to between ghosts and Pacman.
 * 
 * @author Tobias Liljeblad & Rasmus Djupedal
 */
public class PostMaster {
    private LinkedList<String> ghostMessages = new LinkedList<>();
    private LinkedList<String> pacManMessages = new LinkedList<>();

    private static PostMaster postMaster = null;

    /**
     * Empty constructor
     */
    protected PostMaster() {

    }

    /**
     * The getter and Initializer for the singleton class. Made synchronized so that
     * threads are concurrent.
     * 
     * @return the instance of the class
     */
    public static synchronized PostMaster getPostMaster() {
        if (postMaster == null) {
            postMaster = new PostMaster();
        }
        return postMaster;

    }

    /**
     * Method for ghosts to send a message. List is synchronized to enable
     * concurrency.
     * 
     * @param msg the message
     * 
     */
    protected void sendGhostMessage(String msg) {

        synchronized (ghostMessages) {
            ghostMessages.add(msg);

        }

    }

    /**
     * Method to recieve a message from the ghosts. LinkedList is synchronized to
     * enable concurrency.
     * 
     * @return a message from ghosts.
     */
    protected synchronized String recieveGhostMsg() {
        synchronized (ghostMessages) {
            if (!ghostMessages.isEmpty()) {
                return ghostMessages.pop();
            } else
                return "No messages";
        }

    }

    /**
     * Method for Pacman to send a message. List is synchronized to enable
     * concurrency.
     * 
     * @param msg the message
     * 
     */
    protected synchronized void sendPacManMsg(String msg) {

        synchronized (pacManMessages) {
            pacManMessages.add(msg);
        }

    }

    /**
     * Method for ghosts to recieve a message from Pacman. List is synchronized to
     * enable concurrency.
     * 
     * @return a message
     */
    protected synchronized String recievePacManMsg() {

        synchronized (pacManMessages) {
            if (!pacManMessages.isEmpty()) {
                return pacManMessages.pop();

            } else
                return null;
        }

    }

    /**
     * Checks if Pacman has any messages from Ghost.
     * 
     * @return true if there is mail
     */
    protected synchronized boolean pacManHasMail() {
        return !ghostMessages.isEmpty();
    }

    /**
     * Checks if Ghosts has any messages from Pacman.
     * 
     * @return true if there is mail
     */
    protected synchronized boolean ghostHasMail() {
        return !pacManMessages.isEmpty();
    }

}
