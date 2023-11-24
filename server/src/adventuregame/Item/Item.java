package adventuregame.Item;

import adventuregame.Entity.Stats;

public class Item {
    private int id;
    private String name;
    private String description;
    private Stats modifiers;

    public Item(int id, String name, String description, Stats modifiers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modifiers = modifiers;
    }

    public Stats getModifiers() {
        return this.modifiers;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public int getID() {
        return this.id;
    }

    public String stringifyModifiers() {
        String str = "";
        if (getModifiers().getMaxHealth() > 0) {
            str += "+" + getModifiers().getMaxHealth() + " Max HP\n";
        }
        if (getModifiers().getPhyAtk() > 0) {
            str += "+" + getModifiers().getPhyAtk() + " Phy Atk\n";
        }
        if (getModifiers().getMagicAtk() > 0) {
            str += "+" + getModifiers().getMagicAtk() + " Mag Atk\n";
        }
        if (getModifiers().getPhyDef() > 0) {
            str += "+" + getModifiers().getPhyDef() + " Phy Def\n";
        }
        if (getModifiers().getMagicDef() > 0) {
            str += "+" + getModifiers().getMagicDef() + " Mag Def\n";
        }
        return str;
    }
}
