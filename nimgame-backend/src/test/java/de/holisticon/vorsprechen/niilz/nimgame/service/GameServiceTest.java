package de.holisticon.vorsprechen.niilz.nimgame.service;

import de.holisticon.vorsprechen.niilz.nimgame.model.GameState;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessageComputer;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessageHuman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GameService.class)
class GameServiceTest {

    private final GameService gameService;

    @BeforeEach
    void reset() {
        gameService.resetGame();
    }

    @Autowired
    public GameServiceTest(GameService gameService) {
        this.gameService = gameService;
    }

    @Test
    void gameIsStartedAfterStartingTheGame() {
        assertFalse(gameService.isGameStarted());
        gameService.startGame(true);
        assertTrue(gameService.isGameStarted());
    }

    @Test
    void resettingTheGameReturnsToInitalState() {
        gameService.startGame(true);
        assertTrue(gameService.isGameStarted());
        gameService.resetGame();
        assertFalse(gameService.isGameStarted());
    }

    @Test
    void restartingTheGameCreatesNewInitialState() {
        gameService.startGame(false);
        assertTrue(gameService.isGameStarted());
        var currentPlayersRank = gameService.getCurrentPlayersRank();
        var matchesToDraw = 3;
        var moveMessage = new MoveMessageHuman(currentPlayersRank, matchesToDraw, false);
        gameService.makeMove(moveMessage);
        // Remaining Matches are less than initally
        assertEquals(GameState.INITIAL_MATCH_COUNT - matchesToDraw, gameService.getRemainingMatches());

        // Restart should bring back a new started game
        gameService.restartGame(false);
        assertTrue(gameService.isGameStarted());
        assertEquals(GameState.INITIAL_MATCH_COUNT, gameService.getRemainingMatches());
    }

    @Test
    void computerMustNotBePlayAsHuman() {
        gameService.startGame(false);
        // Both Players are HUMAN
        var currentPlayersRank = gameService.getCurrentPlayersRank();
        var moveMessageComputer = new MoveMessageComputer(currentPlayersRank);
        assertThrows(IllegalArgumentException.class, () -> gameService.makeMove(moveMessageComputer));
    }
}