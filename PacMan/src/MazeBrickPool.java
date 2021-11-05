import java.util.ArrayList;

/**
 * Object pool and factory for MazeBricks
 * @author Rasmus Djupedal, Tobias Liljeblad
 */
public class MazeBrickPool {
    protected static final MazeBrickPool INSTANCE = new MazeBrickPool();
    private static ArrayList<MazeBrick> pool = new ArrayList<>();

    /**
     * Private constructor as it is a Singleton
     */
    private MazeBrickPool(){}

    /**
     * Instantiates a new MazeBrick object if there aren't any free available
     * @return MazaBrick object
     */
    protected MazeBrick getBrickObject() {
        if (pool.isEmpty()) { return new MazeBrick(); }

        MazeBrick tempBrick = pool.get(pool.size() - 1);
        pool.remove(pool.size() - 1);
        return tempBrick;
    }

    /**
     * Gets a free MazeBrick and stores it for future use
     * @param returnedBrick
     */
    protected void returnBrickObject(MazeBrick returnedBrick) {
        pool.add(returnedBrick);
    }
}
