package de.holisticon.vorsprechen.niilz.nimgame.service;

import de.holisticon.vorsprechen.niilz.nimgame.model.GameState;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessage;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessageComputer;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessageHuman;
import de.holisticon.vorsprechen.niilz.nimgame.model.Player;
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

    @Test
    void autoMoveIsOnlyAllowedWhenApproriate() {
        // Version A: Human vs Human
        gameService.startGame(false);
        // Both players are Human so setting automove to true has no effect
        assertFalse(gameService.shouldMakeAutoMove(true));
        var currentPlayersRankA = gameService.getCurrentPlayersRank();
        var humanMoveA = new MoveMessageHuman(currentPlayersRankA, 1, true);
        // Make a move and check again
        gameService.makeMove(humanMoveA);
        assertFalse(gameService.shouldMakeAutoMove(true));

        // Version B: Human vs Computer
        gameService.restartGame(true);
        if (gameService.isCurrentPlayerComputer()) {
            // If the user would specify 'no-auto-play' aka autoPlay==false
            assertFalse(gameService.shouldMakeAutoMove(false));

            // make a move so it's the human's turn
            var currentPlayersRankBComputer = gameService.getCurrentPlayersRank();
            var computerMoveB = new MoveMessageComputer(currentPlayersRankBComputer);
            gameService.makeMove(computerMoveB);
            // No automove should be possible when the next player is a Human
            // (even with autoPlay==true)
            assertFalse(gameService.shouldMakeAutoMove(true));

        } else {
            // Human starts (and wants no autoplay)
            assertFalse(gameService.shouldMakeAutoMove(false));
            // Make move so that it's the Computer's turn
            var currentPlayersRankBHuman = gameService.getCurrentPlayersRank();
            var humanMoveB = new MoveMessageHuman(currentPlayersRankBHuman, 1, false);
            gameService.makeMove(humanMoveB);

            // It is the computers turn if autoPlay was set to true it autoplay is allowed
            assertTrue(gameService.isCurrentPlayerComputer());
            assertTrue(gameService.shouldMakeAutoMove(true));
        }

        // Version C: Human vs Computer and Game has ended
        gameService.restartGame(true);
        var currentRank = gameService.getCurrentPlayersRank();
        var nextRank = currentRank == Player.PlayerRank.ONE
                ? Player.PlayerRank.TWO
                : Player.PlayerRank.ONE;
        MoveMessage currentMessage;
        MoveMessage nextMessage;
        if (gameService.isCurrentPlayerComputer()) {
            currentMessage = new MoveMessageComputer(currentRank);
            nextMessage = new MoveMessageHuman(nextRank, 1, false);

        } else {
            currentMessage = new MoveMessageHuman(currentRank, 1, false);
            nextMessage = new MoveMessageComputer(nextRank);
        }
        // play the game until it is finished
        while (gameService.isGameStarted()) {
            gameService.makeMove(currentMessage);
            var tempMessage = currentMessage;
            currentMessage = nextMessage;
            nextMessage = tempMessage;
        }
        // Game should be won now
        assertFalse(gameService.isGameStarted());
        if (gameService.isCurrentPlayerComputer()) {
            // Even if current-Player is Computer and the autoPlay is specified
            // autoMove should not happen because game is already won
            assertFalse(gameService.shouldMakeAutoMove(true));
        }
    }
}