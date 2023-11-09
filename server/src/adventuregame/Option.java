package adventuregame;

public class Option {
    String text;
    int id;
    Outcome outcome;
    
    public Option() {
        
    }
    public int getID() {
        return this.id;
    }
    public void makeSelection(Player p) {
        outcome.applyResults(p);
        outcome.setNextChoice(p);
    }
}