package server.test.adventuregame.Entity;

import adventuregame.Entity.Stats;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestStats {
    Stats baseStats;
    Stats modifierStats;
    Stats modifiedStats;

    @Before
    public void setup() {
        baseStats = new Stats(6,6,6,6,6);
        modifierStats = new Stats(1,2,3,4,5);
        modifiedStats = new Stats(7,8,9,10,11);
    }

    @Test
    public void testGetPhyAtk() {
        assertTrue(modifierStats.getPhyAtk() == 1);
    }

    @Test
    public void testGetMagicAtk() {
        assertTrue(modifierStats.getMagicAtk() == 2);
    }

    @Test
    public void testGetPhyDef() {
        assertTrue(modifierStats.getPhyDef() == 3);
    }

    @Test
    public void testGetMagicDef() {
        assertTrue(modifierStats.getMagicDef() == 4);
    }

    @Test
    public void testMaxHealth() {
        assertTrue(modifierStats.getMaxHealth() == 5);
    }

    @Test
    public void testApplyModifiers() {
        baseStats.applyModifiers(modifierStats);
        assertTrue(baseStats.getPhyAtk() == modifiedStats.getPhyAtk());
        assertTrue(baseStats.getMagicAtk() == modifiedStats.getMagicAtk());
        assertTrue(baseStats.getPhyDef() == modifiedStats.getPhyDef());
        assertTrue(baseStats.getMagicDef() == modifiedStats.getMagicDef());
        assertTrue(baseStats.getMaxHealth() == modifiedStats.getMaxHealth());
    }

    @After
    public void teardown() {
        baseStats = null;
        modifierStats = null;
        modifiedStats = null;
    }
}
