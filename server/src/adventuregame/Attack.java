package adventuregame;

import java.util.Random;

public class Attack {
    private double baseDamage;
    private int baseVariation; // 0 to 100
    private double abilityDamage;
    private int abilityVariation; // 0 to 100
    private double trueDamage;
    private Random random = new Random();
    private static double DAMAGE_MULTIPLIER = 5.0;


    public Attack(double baseDamage, double abilityDamage, double trueDamage) {
        this.baseDamage = baseDamage;
        this.abilityDamage = abilityDamage;
        this.trueDamage = trueDamage;
        this.baseVariation = 0;
        this.abilityVariation = 0;
    }

    public void setBaseDamage(double damage) {
        this.baseDamage = damage;
    }
    public void setAbilityDamage(double damage) {
        this.abilityDamage = damage;
    }
    public void setTrueDamage(double damage) {
        this.trueDamage = damage;
    }
    public void setBaseVariation(int variation) {
        this.baseVariation = variation;
    }
    public void setAbilityVariation(int variation) {
        this.abilityVariation = variation;
    }

    public double getBaseDamage() {
        return (this.baseDamage) * getBaseVariation();
    }
    private double getBaseVariation() {
        return (random.nextInt(-baseVariation, baseVariation) + 100) / 100.0;
    }
    private double getAbilityVariation() {
        return (random.nextInt(-abilityVariation, abilityVariation) + 100) / 100.0;
    }
    public double getAbilityDamage() {
        return (this.abilityDamage) * getAbilityVariation();
    }
    public double getTrueDamage() {
        return this.trueDamage;
    }
}
