package adventuregame.Item;

import java.util.Random;

import adventuregame.Entity.Stats;

public class ItemDrop extends Item {
    // integer 1 - 100 indicating drop percentage
    private int dropChance;
    
    public ItemDrop(int id, String name, String description, Stats modifiers, int dropChance) {
        super(id, name, description, modifiers);
        this.dropChance = dropChance;
    }

    public boolean drop() {
        Random rand = new Random();
        return (rand.nextInt(100)+1) <= dropChance;
    }
}
