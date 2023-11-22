package adventuregame.Entity;

public class Stats {
    private int baseAtk;
    private int magicAtk;
    private int baseDef;
    private int magicDef;
    private int max_health;

    public Stats() {
        this.baseAtk = 0;
        this.magicAtk = 0;
        this.baseDef = 1;
        this.magicDef = 1;
        this.max_health = 0;
    }
    public Stats(int value) {
        if (value == 0) {
            this.baseDef = 1;
            this.magicDef = 1;
        } else {
            this.baseDef = value;
            this.magicDef = value;
        }
        
        this.baseAtk = value;
        this.magicAtk = value;
        this.max_health = value;
    }
    public Stats(int baseAtk, int magicAtk, int baseDef, int magicDef, int maxHealth) {
        this.baseAtk = baseAtk;
        this.magicAtk = magicAtk;
        if (baseDef == 0) {
            this.baseDef = 1;
        } else {
            this.baseDef = baseDef;
        }
        if (magicDef == 0) {
            this.magicDef = 1;
        } else {
            this.magicDef = magicDef;
        }
        this.max_health = maxHealth;
    }

    public int getBaseAtk() {
        return this.baseAtk;
    }
    public int getMagicAtk() {
        return this.magicAtk;
    }
    public int getBaseDef() {
        return this.baseDef;
    }
    public int getMagicDef() {
        return this.magicDef;
    }
    public int getMaxHealth() {
        return this.max_health;
    }

    public void applyModifiers(Stats modifiers) {
        this.baseAtk += modifiers.getBaseAtk();
        this.magicAtk += modifiers.getMagicAtk();
        this.baseDef += modifiers.getBaseDef();
        this.magicDef += modifiers.getMagicDef();
        this.max_health += modifiers.getMaxHealth();
    }
}
