package adventuregame.Entity;

import java.util.Random;

import adventuregame.Game;

public class Monster extends Entity {
    private int id;
    private int minMoney;
    private int maxMoney;
    private int tier;
    private String description;
    private int cooldown;
    


    public Monster(String name, String description, Stats stats, int minMoney, int maxMoney, int tier) {
        super(name, stats);
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

    public Damage attack(Entity defender) {
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
            int damage = useSpecialAttack(defender);
            return new Damage(damage, getSpecialAttack().getName());
        } else {
            int damage = useBasicAttack(defender);
            return new Damage(damage, getBasicAttack().getName());
        }
    }
}
