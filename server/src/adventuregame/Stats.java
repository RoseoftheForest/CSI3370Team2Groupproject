package adventuregame;

import java.util.Random;

public class Stats {
    private int baseAtk;
    private int abilityAtk;
    private int baseDef;
    private int abilityDef;
    private int max_health;

    public Stats() {
        this.baseAtk = 0;
        this.abilityAtk = 0;
        this.baseDef = 0;
        this.abilityDef = 0;
        this.max_health = 0;
    }
    public Stats(int value) {
        this.baseAtk = value;
        this.abilityAtk = value;
        this.baseDef = value;
        this.abilityDef = value;
        this.max_health = value;
    }
    public Stats(int phy_atk, int mgc_atk, int phy_def, int mgc_def, int max_health) {
        this.baseAtk = phy_atk;
        this.abilityAtk = mgc_atk;
        this.baseDef = phy_def;
        this.abilityDef = mgc_def;
        this.max_health = max_health;
    }

    public int getBaseAtk() {
        return this.baseAtk;
    }
    public int getAbilityAtk() {
        return this.abilityAtk;
    }
    public int getBaseDef() {
        return this.baseDef;
    }
    public int getAbilityDef() {
        return this.abilityDef;
    }
    public int getMaxHealth() {
        return this.max_health;
    }

    public void applyModifiers(Stats modifiers) {
        this.baseAtk += modifiers.getBaseAtk();
        this.abilityAtk += modifiers.getAbilityAtk();
        this.baseDef += modifiers.getBaseDef();
        this.abilityDef += modifiers.getAbilityDef();
        this.max_health += modifiers.getMaxHealth();
    }

    public int calculateDamageDealt(Stats defender) {
        Random rand = new Random();

        double baseDamage = 25 * ((getBaseAtk() / (double)defender.getBaseDef()) + (getAbilityAtk() / (double)defender.getAbilityDef()));
        double random = rand.nextInt(20) + 80;
        int damage = (int)(baseDamage * random);

        
        // int damage = (getPhyAtk() - defender.getPhyDef()) + (getMgcAtk() - defender.getMgcDef());
        if (damage < 1) {
            return 1;
        }
        return damage;
    }
}
