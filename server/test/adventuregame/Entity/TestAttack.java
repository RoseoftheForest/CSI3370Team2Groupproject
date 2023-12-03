package server.test.adventuregame.Entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adventuregame.Entity.Attack;
import adventuregame.Entity.Stats;
import adventuregame.Entity.Attack.Type;

public class TestAttack {
    Attack attackM;
    Attack attackP;
    Attack attackT;

    Stats attackerStats;
    Stats defenderStats;

    @Before
    public void setup() {
        attackM = new Attack("fire ball attack", Type.MAGIC, 2);
        attackP = new Attack("sword stab", Type.PHYSICAL, 2);
        attackT = new Attack("True", Type.TRUE, 2);

        attackerStats = new Stats(2, 2, 2, 2, 10);
        defenderStats = new Stats(2, 2, 2, 2, 10);
    }

    @Test
    public void testGetType() {
        assertTrue(attackM.getType() == Type.MAGIC);
        assertTrue(attackP.getType() == Type.PHYSICAL);
        assertTrue(attackT.getType() == Type.TRUE);
    }

    @Test
    public void testGetName() {
        assertTrue(attackM.getName() == "fire ball attack");
    }

    @Test
    public void testSetBaseDamage() {
        attackM.setBaseDamage(1);
        assertTrue(attackM.getBaseDamage() == 1);

        attackM.setBaseDamage(2);
        assertTrue(attackM.getBaseDamage() == 2);
        assertFalse(attackM.getBaseDamage() == 1);
    }

    @Test
    public void testGetDamage() {
        //base test
        assertTrue(attackM.getDamage(attackerStats, defenderStats) == 10);
        assertTrue(attackP.getDamage(attackerStats, defenderStats) == 10);
        assertTrue(attackT.getDamage(attackerStats, defenderStats) == 10);

        //test that magic damage is being applied only
        attackM = new Attack("fire ball attack", Type.MAGIC, 3);
        assertTrue(attackM.getDamage(attackerStats, defenderStats) == 15);

        //test that physical damage is being applied only
        attackP = new Attack("sword stab", Type.PHYSICAL, 4);
        assertTrue(attackP.getDamage(attackerStats, defenderStats) == 20);

        //test that true damage is being applied only
        attackT = new Attack("True", Type.TRUE, 5);
        assertTrue(attackT.getDamage(attackerStats, defenderStats) == 25);
    }

    @After
    public void teardown() {
        attackM = null;
        attackP = null;
        attackT = null;
        attackerStats = null;
        defenderStats = null;
    }
}
