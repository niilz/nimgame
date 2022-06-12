package de.holisticon.vorsprechen.niilz.nimgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameResponseTest {

    @Test
    void canCreateGameResponseWithError() {
        var expectedError = "Some Error";
        var error = new GameResponseError(expectedError);
        assertTrue(error instanceof  GameResponse);
        assertEquals(expectedError, error.error());
    }

    @Test
    void canCreateGameResponseWithStateMessage() {
        var gameState = new GameState(true);
        gameState.startGame();
        var message = GameStateMessage.from(gameState);
        var expectedPlayerPosition = gameState.getCurrentPlayer().getPosition();
        var successResponse = new GameResponseSuccess(message);
        assertTrue(successResponse instanceof  GameResponse);
        assertEquals(expectedPlayerPosition, successResponse.message().currentPlayerPosition());
        assertEquals(GameState.INITIAL_MATCH_COUNT, successResponse.message().currentMatchCount());
    }

}