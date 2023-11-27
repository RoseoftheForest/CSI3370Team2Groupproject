package adventuregame.Entity;

import java.util.Random;

import adventuregame.Game;
import adventuregame.Response;

public class Monster extends Entity {
    private int id;
    private int minMoney;
    private int maxMoney;
    private int tier;
    private String description;

    public Monster(int id, String name, String description, Stats stats, int minMoney, int maxMoney, int tier) {
        super(name, stats);
        this.id = id;
        this.description = description;
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
    }

    public Monster deepCopy() {
        return new Monster(getID(), getName(), getDescription(), getStats(), this.minMoney, this.maxMoney, getTier());
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
    public int getID() {
        return this.id;
    }

    public Response attack(Entity defender) {
        double abilityChance = 0;
        if (getHealthPercent() > 0.8) {
            abilityChance = 0.2;
        } else if (getHealthPercent() > 0.5) {
            abilityChance = 0.4;
        } else if (getHealthPercent() > 0.2) {
            abilityChance = 0.6;
        } else if (getHealthPercent() > 0.1) {
            abilityChance = 0.7;
        } else if (getHealthPercent() > 0.05) {
            abilityChance = 0.8;
        }
        
        Random rand = new Random();
        boolean useAbility = rand.nextInt(10) < (abilityChance*10);

        if (useAbility) {
            return useSpecialAttack(defender);
        } else {
            return useBasicAttack(defender);
        }
    }
}
