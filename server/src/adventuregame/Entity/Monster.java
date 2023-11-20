package adventuregame.Entity;

import java.util.Random;

import adventuregame.ItemDrop;


public class Monster extends Entity {
    private int minMoney;
    private int maxMoney;
    private ItemDrop item;


    public Monster(String name, Stats stats, int minMoney, int maxMoney) {
        super(name, stats);
        if (minMoney > maxMoney) {
            this.maxMoney = minMoney;
            this.minMoney = maxMoney;
        } else {
            this.minMoney = minMoney;
            this.maxMoney = maxMoney;
        }
        
        this.item = new ItemDrop(0, "Nothing", "It's nothing.", new Stats(), 0);
    }

    private ItemDrop getItem() {
        return this.item;
    }
    public void setItem(ItemDrop item) {
        this.item = item;
    }

    public ItemDrop dropItem() {
        if (getItem().drop()) {
            return getItem();
        }
        return null;
    }

    public int getDroppedMoney() {
        Random rand = new Random();
        return rand.nextInt(maxMoney-minMoney) + minMoney;
    }
}
