package de.holisticon.vorsprechen.niilz.nimgame.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveUtilsTest {

    private static final int MAX_AMOUNT_TO_DRAW = 3;

    @Test
    void computerCanMakeSmartChoiceBasedOnRemainingMatches() {
        assertEquals(1, MoveUtils.makeSmartChoice(13, MAX_AMOUNT_TO_DRAW));
        assertEquals(3, MoveUtils.makeSmartChoice(12, MAX_AMOUNT_TO_DRAW));
        assertEquals(2, MoveUtils.makeSmartChoice(11, MAX_AMOUNT_TO_DRAW));
        assertEquals(1, MoveUtils.makeSmartChoice(10, MAX_AMOUNT_TO_DRAW));
        assertEquals(1, MoveUtils.makeSmartChoice(9, MAX_AMOUNT_TO_DRAW));
        assertEquals(3, MoveUtils.makeSmartChoice(8, MAX_AMOUNT_TO_DRAW));
        assertEquals(2, MoveUtils.makeSmartChoice(7, MAX_AMOUNT_TO_DRAW));
        assertEquals(1, MoveUtils.makeSmartChoice(6, MAX_AMOUNT_TO_DRAW));
        assertEquals(1, MoveUtils.makeSmartChoice(5, MAX_AMOUNT_TO_DRAW));
        assertEquals(3, MoveUtils.makeSmartChoice(4, MAX_AMOUNT_TO_DRAW));
        assertEquals(2, MoveUtils.makeSmartChoice(3, MAX_AMOUNT_TO_DRAW));
        assertEquals(1, MoveUtils.makeSmartChoice(2, MAX_AMOUNT_TO_DRAW));
        assertEquals(1, MoveUtils.makeSmartChoice(1, MAX_AMOUNT_TO_DRAW));
    }

}