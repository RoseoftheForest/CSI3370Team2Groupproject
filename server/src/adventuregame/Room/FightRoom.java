package adventuregame.Room;

import adventuregame.Entity.Monster;

public class FightRoom extends Room {
    private Monster monster;
    
    public FightRoom(int id, int minDepth, int maxDepth, Monster monster) {
        super(id, minDepth, maxDepth);
        this.monster = monster;
    }

    public Monster getMonster() {
        return this.monster;
    }

    public FightRoom deepCopy() {
        return new FightRoom(getID(), getMinDepth(), getMaxDepth(), getMonster().deepCopy());
    }
}
