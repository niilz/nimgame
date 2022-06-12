package de.holisticon.vorsprechen.niilz.nimgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GameStateTest {

    @Test
    void initialGameStateIsCorrect() {
        var initalGameState = new GameState();
        var expectedGameState = GameState.State.STOPPED;
        assertEquals(expectedGameState, initalGameState.getState());
        var expectedMatchCount = GameState.INITIAL_MATCH_COUNT;
        assertEquals(expectedMatchCount, initalGameState.getMatches());
        assertNull(initalGameState.getCurrentPlayer());
    }

}