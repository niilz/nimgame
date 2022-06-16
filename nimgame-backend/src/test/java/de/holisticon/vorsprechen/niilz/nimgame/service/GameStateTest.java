package de.holisticon.vorsprechen.niilz.nimgame.service;

import de.holisticon.vorsprechen.niilz.nimgame.model.Player;
import de.holisticon.vorsprechen.niilz.nimgame.service.GameState;
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
        assertEquals(expectedMatchCount, initalGameState.getRemainingMatches());
        assertNull(initalGameState.getCurrentPlayer());
    }

    @Test
    void decutMatchesFailsWhenMatchCountNotBetween1and3() {
        var gameState = new GameState();
        gameState.startGame();
        assertThrows(IllegalArgumentException.class,
                () -> gameState.makeMove(0, Player.PlayerRank.ONE));
        assertThrows(IllegalArgumentException.class,
                () -> gameState.makeMove(4, Player.PlayerRank.ONE));
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

    @Test
    void ifComputerOpponentIsEnabledOnePlayerMustBeComputer() {
        var gameState = new GameState();
        gameState.startGame(true);
        if (gameState.getCurrentPlayer().getType() == Player.PlayerType.HUMAN) {
            assertEquals(Player.PlayerType.COMPUTER, gameState.getNextPlayer().getType());
        } else {
            assertEquals(Player.PlayerType.COMPUTER, gameState.getCurrentPlayer().getType());
            assertEquals(Player.PlayerType.HUMAN, gameState.getNextPlayer().getType());
        }
    }

    @Test
    void ifNoComputerIsActivatedBothPlayersAreHuman() {
        var gameState = new GameState();
        gameState.startGame();
        assertEquals(Player.PlayerType.HUMAN, gameState.getCurrentPlayer().getType());
        assertEquals(Player.PlayerType.HUMAN, gameState.getNextPlayer().getType());
    }

    @Test
    void whenLastMatchIsTakenGameIsWon() {
        var gameState = new GameState();
        gameState.startGame();
        // Make sure Player TWO is the currentPlayer in the beginning
        if (gameState.getCurrentPlayer().getRank() == Player.PlayerRank.ONE) {
            gameState.swapPlayers();
        }
        var playerRanks = Player.PlayerRank.values();
        // Draw matches until only one is left
        for (int matchCount = 1; matchCount < 13; matchCount++) {
            gameState.makeMove(1, playerRanks[matchCount % 2]);
        }
        assertEquals(1, gameState.getRemainingMatches());
        assertEquals(GameState.State.RUNNING, gameState.getState());
        // Finish the game
        gameState.makeMove(1, Player.PlayerRank.TWO);
        assertEquals(GameState.State.WON, gameState.getState());
    }

    @Test
    void thereCanNeverBeLessThanZeroRemainingMatches() {
        var gameState = new GameState();
        gameState.startGame();
        // Make sure Player TWO is the currentPlayer in the beginning
        if (gameState.getCurrentPlayer().getRank() == Player.PlayerRank.ONE) {
            gameState.swapPlayers();
        }
        var playerRanks = Player.PlayerRank.values();
        for (int matchCount = 1; matchCount < 14; matchCount++) {
            gameState.makeMove(1, playerRanks[matchCount % 2]);
        }
        assertEquals(0, gameState.getRemainingMatches());
        assertThrows(IllegalStateException.class, () -> gameState.makeMove(1, Player.PlayerRank.TWO));
    }
}