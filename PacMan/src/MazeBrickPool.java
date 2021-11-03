import java.util.ArrayList;

/**
 * Object pool and factory of MazeBricks
 */
public class MazeBrickPool {
    protected static final MazeBrickPool INSTANCE = new MazeBrickPool();
    private static ArrayList<MazeBrick> pool = new ArrayList<>();

    /**
     * Private empty constructor as it is a Singleton
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

    protected void returnBrickObject(MazeBrick returnedBrick) {
        pool.add(returnedBrick);
    }
}
