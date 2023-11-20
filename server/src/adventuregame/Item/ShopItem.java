package adventuregame.Item;

import adventuregame.Stats;

public class ShopItem extends Item {
    private int cost;

    public ShopItem(int id, String name, String description, Stats modifiers, int cost) {
        super(id, name, description, modifiers);
        this.cost = cost;
    }

    public int getCost() {
        return this.cost;
    }
    
}
