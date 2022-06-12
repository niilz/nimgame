package de.holisticon.vorsprechen.niilz.nimgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void decutMatchesFailsWhenMatchCountNotBetween1and3() {
        var gameState = new GameState();
        gameState.startGame();
        assertThrows(IllegalArgumentException.class,
                () -> gameState.deductMatches(0, Player.Position.ONE));
        assertThrows(IllegalArgumentException.class,
                () -> gameState.deductMatches(4, Player.Position.ONE));
    }

    @Test
    void decutMatchesFailsWhenPlayerIsNotTheCurrentPlayer() {
        var gameState = new GameState();
        gameState.startGame();
        var currentPlayerPosition = gameState.getCurrentPlayer().getPosition();
        var notCurrentPlayerPosition = currentPlayerPosition == Player.Position.ONE
                ? Player.Position.TWO
                : Player.Position.ONE;
        assertThrows(IllegalArgumentException.class,
                () -> gameState.deductMatches(1, notCurrentPlayerPosition));
    }

    @Test
    void swappingPlayersWorks() {
        var gameState = new GameState();
        var currentPlayer = gameState.getCurrentPlayer();
        var nextPlayer = gameState.getNextPlayer();
        gameState.swapPlayers();
        assertEquals(currentPlayer, gameState.getNextPlayer());
        assertEquals(nextPlayer, gameState.getCurrentPlayer());

    }

}