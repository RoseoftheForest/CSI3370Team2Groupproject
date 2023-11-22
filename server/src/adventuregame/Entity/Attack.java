package adventuregame.Entity;

import java.util.Random;

public class Attack {
    private double damage;
    private double minVariation;
    private double maxVariation;
    private double trueDamage;
    private Random random = new Random();
    private Type attackType;
    private static double DAMAGE_MULTIPLIER = 5.0;
    public static enum Type {
        MAGIC, PHYSICAL
    }

    public Attack(Type type, double baseDamage, double trueDamage) {
        this.attackType = type;
        this.damage = baseDamage;
        this.trueDamage = trueDamage;
        this.minVariation = 1.0;
        this.maxVariation = 1.0;
        

    }

    public void setBaseDamage(double damage) {
        this.damage = damage;
    }
    public void setTrueDamage(double damage) {
        this.trueDamage = damage;
    }
    public void setMinVariation(double variation) {
        this.minVariation = variation;
    }
    public void setMaxVariation(double variation) {
        this.maxVariation = variation;
    }

    public double getBaseDamage() {
        return (this.damage) * getBaseVariation();
    }
    private double getBaseVariation() {
        return (random.nextInt(((int)minVariation)*100, ((int)maxVariation)*100)) / 100.0;
    }
    public double getTrueDamage() {
        return this.trueDamage;
    }
    public Type getType() {
        return this.attackType;
    }

    public int getDamage(Stats attackerStats, Stats defenderStats) {
        double b_dmg = 0;
        if (getType() == Type.MAGIC) {
            b_dmg = (getBaseDamage() * attackerStats.getMagicAtk()) / defenderStats.getMagicDef();
        } else if (getType() == Type.PHYSICAL) {
            b_dmg = (getBaseDamage() * attackerStats.getBaseAtk()) / defenderStats.getBaseDef();
        }
        
        double t_dmg = (getTrueDamage());
        
        int dmg = (int)(DAMAGE_MULTIPLIER * (b_dmg + t_dmg));
        return dmg;
    }
    public void useAttack(Entity attacker, Entity defender) {
        int damage = getDamage(attacker.getStats(), defender.getStats());
        defender.takeDamage(damage);
    }
}
