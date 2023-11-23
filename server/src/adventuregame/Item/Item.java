package adventuregame.Item;

import adventuregame.Entity.Stats;

public class Item {
    private int id;
    private String name;
    private String description;
    private Stats modifiers;

    public Item(int id, String name, String description, Stats modifiers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modifiers = modifiers;
    }

    public Stats getModifiers() {
        return this.modifiers;
    }
}
