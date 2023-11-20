package adventuregame;

import java.util.Random;

public class Stats {
    private int phy_atk;
    private int mgc_atk;
    private int phy_def;
    private int mgc_def;
    private int max_health;

    public Stats() {
        this.phy_atk = 0;
        this.mgc_atk = 0;
        this.phy_def = 0;
        this.mgc_def = 0;
        this.max_health = 0;
    }
    public Stats(int value) {
        this.phy_atk = value;
        this.mgc_atk = value;
        this.phy_def = value;
        this.mgc_def = value;
        this.max_health = value;
    }
    public Stats(int phy_atk, int mgc_atk, int phy_def, int mgc_def, int max_health) {
        this.phy_atk = phy_atk;
        this.mgc_atk = mgc_atk;
        this.phy_def = phy_def;
        this.mgc_def = mgc_def;
        this.max_health = max_health;
    }

    public int getPhyAtk() {
        return this.phy_atk;
    }
    public int getMgcAtk() {
        return this.mgc_atk;
    }
    public int getPhyDef() {
        return this.phy_def;
    }
    public int getMgcDef() {
        return this.mgc_def;
    }
    public int getMaxHealth() {
        return this.max_health;
    }

    public void applyModifiers(Stats modifiers) {
        this.phy_atk += modifiers.getPhyAtk();
        this.mgc_atk += modifiers.getMgcAtk();
        this.phy_def += modifiers.getPhyDef();
        this.mgc_def += modifiers.getMgcDef();
        this.max_health += modifiers.getMaxHealth();
    }

    public int calculateDamageDealt(Stats defender) {
        Random rand = new Random();

        double baseDamage = 25 * ((getPhyAtk() / (double)defender.getPhyDef()) + (getMgcAtk() / (double)defender.getMgcDef()));
        double random = rand.nextInt(20) + 80;
        int damage = (int)(baseDamage * random);

        
        // int damage = (getPhyAtk() - defender.getPhyDef()) + (getMgcAtk() - defender.getMgcDef());
        if (damage < 1) {
            return 1;
        }
        return damage;
    }
}
