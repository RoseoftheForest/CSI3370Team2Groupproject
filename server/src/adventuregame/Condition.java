package adventuregame;

public class Condition {
    Scores scoreCondition;
    Scores scoreResults;
    int nextChoiceID;
    boolean checkGreaterThan;

    public Condition(Scores scoreCondition, Scores scoreResults, int nextChoiceID, boolean checkGreaterThan) {
        this.scoreCondition = scoreCondition;
        this.scoreResults = scoreResults;
        this.nextChoiceID = nextChoiceID;
        this.checkGreaterThan = checkGreaterThan;
    }

    // always true condition (used when there is no condition needed)
    public Condition(int nextChoiceID) {
        this.scoreCondition = new Scores(0.0);
        this.scoreResults = new Scores(1.0);
        this.checkGreaterThan = false;
        this.nextChoiceID = nextChoiceID;
    }
    public Condition(int nextChoiceID, Scores scoreResults) {
        this.scoreCondition = new Scores(0.0);
        this.scoreResults = scoreResults;
        this.checkGreaterThan = false;
        this.nextChoiceID = nextChoiceID;
    }
    
    public boolean checkCondition(Scores s) {
        return (checkGreaterThan == this.scoreCondition.isGreaterThan(s));
    }

    public int getNextChoiceID() {
        return this.nextChoiceID;
    }

}
