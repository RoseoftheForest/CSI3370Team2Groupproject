package adventuregame;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ScoresTest {
    Scores s;
    Scores sGreaterThan;
    Scores initialScores;
    Scores multiplier;

    @Before
    public void setup() {
        s = new Scores(2.0, 3.0, 4.0, 1.0, 5.0);
        sGreaterThan = new Scores(3.0, 4.0, 5.0, 2.0, 6.0);
        initialScores = new Scores(1.0);
        multiplier =  new Scores(2.0, 2.0, 2.0, 2.0, 2.0);
    }

    @Test
    public void testGetScore() {

        assertTrue(s.getScore(Scores.Attribute.HEALTH) == 2.0);
        assertTrue(s.getScore(Scores.Attribute.STRENGTH) == 3.0);
        assertTrue(s.getScore(Scores.Attribute.GOOD) == 4.0);
        assertTrue(s.getScore(Scores.Attribute.BAD) == 1.0);
        assertTrue(s.getScore(Scores.Attribute.NEUTRAL) == 5.0);

        assertFalse(s.getScore(Scores.Attribute.HEALTH) == 3.0);
        assertFalse(s.getScore(Scores.Attribute.STRENGTH) == 2.0);
        assertFalse(s.getScore(Scores.Attribute.GOOD) == 5.0);
        assertFalse(s.getScore(Scores.Attribute.BAD) == 0.5);
        assertFalse(s.getScore(Scores.Attribute.NEUTRAL) == 6.0);
    }

    @Test
    public void testApplyScores() {

        initialScores.applyScores(multiplier);

        assertTrue(initialScores.getScore(Scores.Attribute.HEALTH) == 2.0);
        assertTrue(initialScores.getScore(Scores.Attribute.STRENGTH) == 2.0);
        assertTrue(initialScores.getScore(Scores.Attribute.GOOD) == 2.0);
        assertTrue(initialScores.getScore(Scores.Attribute.BAD) == 2.0);
        assertTrue(initialScores.getScore(Scores.Attribute.NEUTRAL) == 2.0);

        //testing if the values remain unchanged
        assertFalse(initialScores.getScore(Scores.Attribute.HEALTH) == 1.0);
        assertFalse(initialScores.getScore(Scores.Attribute.STRENGTH) == 1.0);
        assertFalse(initialScores.getScore(Scores.Attribute.GOOD) == 1.0);
        assertFalse(initialScores.getScore(Scores.Attribute.BAD) == 1.0);
        assertFalse(initialScores.getScore(Scores.Attribute.NEUTRAL) == 1.0);
    }

    @Test
    public void testIsGreaterThan() {
        assertTrue(sGreaterThan.isGreaterThan(s) == true);

        assertFalse(sGreaterThan.isGreaterThan(sGreaterThan) == true);
        
        assertFalse(s.isGreaterThan(sGreaterThan) == true);
    }


}
