package adventuregame.Item;

import adventuregame.Entity.Stats;

public class ShopItem extends Item {
    private int cost;
    private int quantity;

    public ShopItem(int id, String name, String description, Stats modifiers, int cost) {
        super(id, name, description, modifiers);
        this.cost = cost;
        this.quantity = 1;
    }

    public int getCost() {
        return this.cost;
    }
    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
