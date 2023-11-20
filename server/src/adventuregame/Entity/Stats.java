package adventuregame.Entity;

public class Stats {
    private int baseAtk;
    private int abilityAtk;
    private int baseDef;
    private int abilityDef;
    private int max_health;

    public Stats() {
        this.baseAtk = 0;
        this.abilityAtk = 0;
        this.baseDef = 1;
        this.abilityDef = 1;
        this.max_health = 0;
    }
    public Stats(int value) {
        if (value == 0) {
            this.baseDef = 1;
            this.abilityDef = 1;
        } else {
            this.baseDef = value;
            this.abilityDef = value;
        }
        
        this.baseAtk = value;
        this.abilityAtk = value;
        this.max_health = value;
    }
    public Stats(int baseAtk, int abilityAtk, int baseDef, int abilityDef, int maxHealth) {
        this.baseAtk = baseAtk;
        this.abilityAtk = abilityAtk;
        if (baseDef == 0) {
            this.baseDef = 1;
        } else {
            this.baseDef = baseDef;
        }
        if (abilityDef == 0) {
            this.abilityDef = 1;
        } else {
            this.abilityDef = abilityDef;
        }
        this.max_health = maxHealth;
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
}
