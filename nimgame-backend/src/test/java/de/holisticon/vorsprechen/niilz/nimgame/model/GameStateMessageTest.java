package de.holisticon.vorsprechen.niilz.nimgame.model;

import de.holisticon.vorsprechen.niilz.nimgame.service.GameState;
import de.holisticon.vorsprechen.niilz.nimgame.viewmodel.GameStateMessage;
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
        assertEquals(expectedCurrentMatches, gameStateMessage.currentMatchCount());
    }

    @Test
    void canCreateGameStateMessageFromGameState() {
        var gameState = new GameState();
        gameState.startGame();
        var expectedCurrentPlayer = gameState.getCurrentPlayer();
        var expectedState = GameState.State.RUNNING;
        var expectedCurrentMatches = GameState.INITIAL_MATCH_COUNT;
        var gameStateMessage = GameStateMessage.from(gameState);
        assertEquals(expectedState, gameStateMessage.gameState());
        assertEquals(expectedCurrentMatches, gameStateMessage.currentMatchCount());
        assertEquals(expectedCurrentPlayer.getRank(), gameStateMessage.player());
        assertEquals(expectedCurrentPlayer.getType(), gameStateMessage.type());
    }

    @Test
    void canCreateGameStateMessageEvenIfGameHasNotStarted() {
        var gameState = new GameState();
        var expectedState = GameState.State.STOPPED;
        var expectedCurrentMatches = GameState.INITIAL_MATCH_COUNT;
        var gameStateMessage = GameStateMessage.from(gameState);
        assertEquals(expectedState, gameStateMessage.gameState());
        assertEquals(expectedCurrentMatches, gameStateMessage.currentMatchCount());
        assertNull(gameStateMessage.player());
        assertNull(gameStateMessage.type());
    }

}