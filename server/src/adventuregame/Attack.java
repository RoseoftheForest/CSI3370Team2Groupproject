package adventuregame;

public class Attack {
    private double baseDamage;
    private double abilityDamage;
    private double trueDamage;

    public Attack(double baseDamage, double abilityDamage, double trueDamage) {
        this.baseDamage = baseDamage;
        this.abilityDamage = abilityDamage;
        this.trueDamage = trueDamage;
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

    public double getBaseDamage() {
        return this.baseDamage;
    }
    public double getAbilityDamage() {
        return this.abilityDamage;
    }
    public double getTrueDamage() {
        return this.trueDamage;
    }
}
