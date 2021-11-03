import java.util.ArrayList;

public class MazeBrickPool {
    protected static final MazeBrickPool INSTANCE = new MazeBrickPool();
    private static ArrayList<MazeBrick> pool = new ArrayList<MazeBrick>();

    private MazeBrickPool(){}

    protected MazeBrickPool getInstance() {
        return INSTANCE;
    }

    protected MazeBrick getBrickObject() {
        if (pool.isEmpty()) return new MazeBrick();
        return pool.get(pool.size() - 1);
    }

    protected void returnBrickObject(MazeBrick returnedBrick) {
        pool.add(returnedBrick);
    }
}
