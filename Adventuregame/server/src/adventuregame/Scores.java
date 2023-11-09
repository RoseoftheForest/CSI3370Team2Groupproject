package adventuregame;

public class Scores {
    double[] values;
    public enum Attribute {
        HEALTH,
        STRENGTH,
        GOOD,
        BAD,
        NEUTRAL
    }

    public Scores() {
        values = new double[]{1.0, 1.0, 1.0, 1.0, 1.0};
    }
    public Scores(double init) {
        values = new double[]{init, init, init, init, init};
    }
    public Scores(double health, double strength, double good, double bad, double neutral) {
        this.values = new double[]{health, strength, good, bad, neutral};
    }

    public double getScore(Attribute attribute) {
        return values[attribute.ordinal()];
    }

    public void applyScores(Scores s) {
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] *= s.values[i];
        }
    }

    public boolean isGreaterThan(Scores s) {
        for (int i = 0; i < s.values.length; i++) {
            if (this.values[i] <= s.values[i]) {
                return false;
            }
        }
        return true;
    }
}