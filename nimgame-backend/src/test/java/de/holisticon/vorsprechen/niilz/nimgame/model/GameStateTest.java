package de.holisticon.vorsprechen.niilz.nimgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameStateTest {

    @Test
    void initialGameStateIsCorrect() {
        var initalGameState = new GameState(true);
        var expectedGameState = GameState.State.STOPPED;
        assertEquals(expectedGameState, initalGameState.getState());
        var expectedMatchCount = GameState.INITIAL_MATCH_COUNT;
        assertEquals(expectedMatchCount, initalGameState.getRemainingMatches());
        assertNull(initalGameState.getCurrentPlayer());
    }

    @Test
    void decutMatchesFailsWhenMatchCountNotBetween1and3() {
        var gameState = new GameState(true);
        gameState.startGame();
        assertThrows(IllegalArgumentException.class,
                () -> gameState.makeMove(0));
        assertThrows(IllegalArgumentException.class,
                () -> gameState.makeMove(4));
    }

    @Test
    void swappingPlayersWorks() {
        var gameState = new GameState(true);
        var currentPlayer = gameState.getCurrentPlayer();
        var nextPlayer = gameState.getNextPlayer();
        gameState.swapPlayers();
        assertEquals(currentPlayer, gameState.getNextPlayer());
        assertEquals(nextPlayer, gameState.getCurrentPlayer());
    }

    @Test
    void ifComputerOpponentIsEnabledOnePlayerMustBeComputer() {
        var gameState = new GameState(true);
        gameState.startGame();
        if (gameState.getCurrentPlayer().getType() == Player.PlayerType.HUMAN) {
            assertEquals(Player.PlayerType.COMPUTER, gameState.getNextPlayer().getType());
        } else {
            assertEquals(Player.PlayerType.COMPUTER, gameState.getCurrentPlayer().getType());
            assertEquals(Player.PlayerType.HUMAN, gameState.getNextPlayer().getType());
        }
    }
}