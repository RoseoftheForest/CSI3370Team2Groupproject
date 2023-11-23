package adventuregame.Entity;

import adventuregame.Entity.Attack.Type;

public class Entity {
    private String name;
    private Stats stats;
    private int health;
    private boolean isAlive;
    private Attack basicAttack;
    private Attack specialAttack;

    public Entity(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
        heal();
        setAliveStatus();
        setBasicAttack(new Attack("", Type.PHYSICAL, 2));
        setSpecialAttack(new Attack("", Type.MAGIC, 2));
    }

    public String getName() {
        return this.name;
    }
    public Stats getStats() {
        return this.stats;
    }
    public int getHealth() {
        return this.health;
    }
    public double getHealthPercent() {
        return (double)getHealth() / getStats().getMaxHealth();
    }
    public boolean isAlive() {
        return this.isAlive;
    }
    public Attack getBasicAttack() {
        return this.basicAttack;
    }
    public Attack getSpecialAttack() {
        return this.specialAttack;
    }

    public void heal() {
        this.health = stats.getMaxHealth();
    }
    public void heal(int amount) {
        if (health + amount > stats.getMaxHealth()) {
            this.health = stats.getMaxHealth();
            return;
        }
        this.health += amount;
    }

    public void setAliveStatus() {
        this.isAlive = checkDeath();
    }
    public void setBasicAttack(Attack attack) {
        this.basicAttack = attack;
    }
    public void setSpecialAttack(Attack attack) {
        this.specialAttack = attack;
    }

    public int useBasicAttack(Entity defenderEntity) {
        return basicAttack.useAttack(this, defenderEntity);
    }
    public int useSpecialAttack(Entity defenderEntity) {
        return specialAttack.useAttack(this, defenderEntity);
    }

    public void takeDamage(int damage) {
        if (getHealth() < damage) {
            damage = getHealth();
        }
        this.health -= damage;
        setAliveStatus();
    }

    private boolean checkDeath() {
        if (this.health < 1) {
            return false;
        }
        return true;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHealth(int health) {
        if (health > getStats().getMaxHealth()) {
            health = getStats().getMaxHealth();
        }
        this.health = health;
    }
}
