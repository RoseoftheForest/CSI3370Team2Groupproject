package adventuregame.Entity;

import java.util.Random;

import adventuregame.Game;

public class Monster extends Entity {
    private int minMoney;
    private int maxMoney;
    private int tier;
    private int cooldown;


    public Monster(String name, Stats stats, int minMoney, int maxMoney) {
        super(name, stats);
        if (minMoney > maxMoney) {
            this.maxMoney = minMoney;
            this.minMoney = maxMoney;
        } else {
            this.minMoney = minMoney;
            this.maxMoney = maxMoney;
        }
        if (tier > Game.MAX_TIER || tier < Game.MIN_TIER) {
            this.tier = Game.MIN_TIER;
        } else {
            this.tier = tier;
        }
        this.cooldown = 0;
    }

    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getTier() {
        return this.tier;
    }
    public String getDescription() {
        return this.description;
    }

    public int getDroppedMoney() {
        Random rand = new Random();
        return rand.nextInt(maxMoney-minMoney) + minMoney;
    }
}
