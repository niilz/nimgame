package de.holisticon.vorsprechen.niilz.nimgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateMessageTest {

    @Test
    void unstartedGameHasNoPlayerPosition() {
        var gameState = new GameState();
        var expectedState = GameState.State.STOPPED;
        var gameStateMessage = GameStateMessage.from(gameState);
        var expectedCurrentMatches = GameState.INITIAL_MATCH_COUNT;
        assertEquals(expectedState, gameStateMessage.gameState());
        assertNull(gameStateMessage.currentPlayerPosition());
        assertEquals(expectedCurrentMatches, gameStateMessage.currentMatchCount());
    }

    @Test
    void canCreateGameStateMessageFromGameState() {
        var gameState = new GameState();
        gameState.startGame();
        var expectedState = GameState.State.RUNNING;
        var expectedCurrentPlayer = gameState.getCurrentPlayer();
        var gameStateMessage = GameStateMessage.from(gameState);
        var expectedCurrentMatches = GameState.INITIAL_MATCH_COUNT;
        assertEquals(expectedState, gameStateMessage.gameState());
        assertEquals(expectedCurrentPlayer.getPosition(), gameStateMessage.currentPlayerPosition());
        assertEquals(expectedCurrentMatches, gameStateMessage.currentMatchCount());
    }

}