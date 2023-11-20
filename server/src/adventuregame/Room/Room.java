package adventuregame.Room;

public class Room {
    private int id;
    private int minDepth;
    private int maxDepth;

    public Room(int id, int minDepth, int maxDepth) {
        this.id = id;
        this.minDepth = minDepth;
        this.maxDepth = maxDepth;
    }

    public int getMinDepth() {
        return this.minDepth;
    }
    public int getMaxDepth() {
        return this.maxDepth;
    }
    public int getID() {
        return this.id;
    }

    public boolean isWithinDepth(int depth) {
        return (getMinDepth() <= depth && getMaxDepth() >= depth);
    }
}
