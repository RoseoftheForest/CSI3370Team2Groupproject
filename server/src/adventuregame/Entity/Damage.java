package adventuregame.Entity;

public class Damage {
    private int amount;
    private String attackName;

    public Damage(int amount, String name) {
        this.amount = amount;
        this.attackName = name;
    }

    public int getAmount() {
        return this.amount;
    }
    public String getName() {
        return this.attackName;
    }
}
