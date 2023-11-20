package adventuregame;

public class Entity {
    private String name;
    private Stats stats;
    private int health;
    private boolean isAlive;

    public Entity(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
        heal();
        setAliveStatus();
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
    public boolean isAlive() {
        return this.isAlive;
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

    public void attack(Entity defenderEntity) {
        int damage = getStats().calculateDamageDealt(defenderEntity.getStats());
        defenderEntity.takeDamage(damage);
    }

    private void takeDamage(int damage) {
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
