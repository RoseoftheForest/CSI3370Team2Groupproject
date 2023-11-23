package adventuregame.Entity;

public class Stats {
    private int phyAtk;
    private int magicAtk;
    private int phyDef;
    private int magicDef;
    private int maxHealth;

    public Stats() {
        this.phyAtk = 0;
        this.magicAtk = 0;
        this.phyDef = 1;
        this.magicDef = 1;
        this.maxHealth = 0;
    }
    public Stats(int value) {
        if (value == 0) {
            this.phyDef = 1;
            this.magicDef = 1;
        } else {
            this.phyDef = value;
            this.magicDef = value;
        }
        
        this.phyAtk = value;
        this.magicAtk = value;
        this.maxHealth = value;
    }
    public Stats(int phyAtk, int magicAtk, int phyDef, int magicDef, int maxHealth) {
        this.phyAtk = phyAtk;
        this.magicAtk = magicAtk;
        if (phyDef == 0) {
            this.phyDef = 1;
        } else {
            this.phyDef = phyDef;
        }
        if (magicDef == 0) {
            this.magicDef = 1;
        } else {
            this.magicDef = magicDef;
        }
        this.maxHealth = maxHealth;
    }

    public int getPhyAtk() {
        return this.phyAtk;
    }
    public int getMagicAtk() {
        return this.magicAtk;
    }
    public int getPhyDef() {
        return this.phyDef;
    }
    public int getMagicDef() {
        return this.magicDef;
    }
    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void applyModifiers(Stats modifiers) {
        this.phyAtk += modifiers.getPhyAtk();
        this.magicAtk += modifiers.getMagicAtk();
        this.phyDef += modifiers.getPhyDef();
        this.magicDef += modifiers.getMagicDef();
        this.maxHealth += modifiers.getMaxHealth();
    }
}
