package adventuregame;

public class Player {
    String name;
    String id;
    Choice currentChoice;
    Scores scores;
    Settings settings;
    double progress;
    
    public Player(String id) {
        
    }

    public void setChoice(Choice c) {
        this.currentChoice = c;
    }

    public Scores getScores() {
        return this.scores;
    }
}