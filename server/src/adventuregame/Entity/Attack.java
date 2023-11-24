package adventuregame.Entity;

import java.util.Random;

import adventuregame.Response;

public class Attack {
    private String name;
    private double damage;
    private double minVariation;
    private double maxVariation;
    private Random random = new Random();
    private Type attackType;
    private static double DAMAGE_MULTIPLIER = 5.0;
    public static enum Type {
        MAGIC, PHYSICAL, TRUE
    }

    public Attack(String name, Type type, double baseDamage) {
        this.attackType = type;
        this.damage = baseDamage;
        this.minVariation = 1.0;
        this.maxVariation = 1.0;
    }

    public void setBaseDamage(double damage) {
        this.damage = damage;
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
        if (minVariation >= maxVariation) {
            return 1.0;
        }
        return (random.nextInt(((int)minVariation)*100, ((int)maxVariation)*100)) / 100.0;
    }
    public Type getType() {
        return this.attackType;
    }
    public String getName() {
        return this.name;
    }

    public int getDamage(Stats attackerStats, Stats defenderStats) {
        double b_dmg = 0;
        if (getType() == Type.MAGIC) {
            b_dmg = (getBaseDamage() * attackerStats.getMagicAtk()) / defenderStats.getMagicDef();
        } else if (getType() == Type.PHYSICAL) {
            b_dmg = (getBaseDamage() * attackerStats.getPhyAtk()) / defenderStats.getPhyDef();
        } else if (getType() == Type.TRUE) {
            b_dmg = getBaseDamage();
        }
                
        int dmg = (int)(DAMAGE_MULTIPLIER * (b_dmg));
        return dmg;
    }
    public Response useAttack(Entity attacker, Entity defender) {
        Response response = new Response();
        int damage = getDamage(attacker.getStats(), defender.getStats());
        response.addMessage(attacker.getName() + " attacked " + defender.getName() + " with " + getName() + " for " + damage + " damage");
        defender.takeDamage(damage);
        if (!defender.isAlive()) {
            response.addMessage(attacker.getName() + " defeated " + defender.getName());
        }
        return response;
    }
}
