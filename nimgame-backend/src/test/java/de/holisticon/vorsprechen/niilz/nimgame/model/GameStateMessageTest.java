package de.holisticon.vorsprechen.niilz.nimgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateMessageTest {

    @Test
    void unstartedGameHasNoPlayerPosition() {
        var gameState = new GameState(true);
        var expectedState = GameState.State.STOPPED;
        var gameStateMessage = GameStateMessage.from(gameState);
        var expectedCurrentMatches = GameState.INITIAL_MATCH_COUNT;
        assertEquals(expectedState, gameStateMessage.gameState());
        assertEquals(expectedCurrentMatches, gameStateMessage.currentMatchCount());
    }

    @Test
    void canCreateGameStateMessageFromGameState() {
        var gameState = new GameState(true);
        gameState.startGame();
        var expectedCurrentPlayer = gameState.getCurrentPlayer();
        var expectedState = GameState.State.RUNNING;
        var gameStateMessage = GameStateMessage.from(gameState);
        var expectedCurrentMatches = GameState.INITIAL_MATCH_COUNT;
        assertEquals(expectedState, gameStateMessage.gameState());
        assertEquals(expectedCurrentMatches, gameStateMessage.currentMatchCount());
        assertEquals(expectedCurrentPlayer.getRank(), gameStateMessage.player());
        assertEquals(expectedCurrentPlayer.getType(), gameStateMessage.type());
    }

}