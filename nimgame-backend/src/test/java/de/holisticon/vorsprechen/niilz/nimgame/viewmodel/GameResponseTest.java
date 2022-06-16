package de.holisticon.vorsprechen.niilz.nimgame.viewmodel;

import de.holisticon.vorsprechen.niilz.nimgame.service.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameResponseTest {

    @Test
    void canCreateGameResponseWithError() {
        var expectedError = "Some Error";
        var error = new GameResponseError(expectedError);
        assertTrue(error instanceof GameResponse);
        assertEquals(expectedError, error.error());
    }

    @Test
    void canCreateGameResponseWithStateMessage() {
        var gameState = new GameState();
        gameState.startGame();
        var message = GameStateMessage.from(gameState);
        var successResponse = new GameResponseSuccess(message);
        assertTrue(successResponse instanceof  GameResponse);
        assertEquals(GameState.INITIAL_MATCH_COUNT, successResponse.message().currentMatchCount());
    }

    @Test
    void samePlayerMustNotPlayAgain() {
        var gameState = new GameState();
        gameState.startGame();
        var currentRank = gameState.getCurrentPlayer().getRank();
        gameState.makeMove(1, currentRank);
        assertThrows(IllegalArgumentException.class, () -> gameState.makeMove(1, currentRank));
    }

}