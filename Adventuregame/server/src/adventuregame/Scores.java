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
    }
}