package adventuregame.Room;

import adventuregame.Entity.Monster;

public class FightRoom extends Room {
    private Monster monster;
    
    public FightRoom(int id, int min_depth, int max_depth, Monster monster) {
        super(id, min_depth, max_depth);
        this.monster = monster;
    }

    public Monster getMonster() {
        return this.monster;
    }

    public FightRoom deepCopy() {
        return new FightRoom(getID(), getMinDepth(), getMaxDepth(), getMonster().deepCopy());
    }
}
