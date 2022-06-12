package de.holisticon.vorsprechen.niilz.nimgame.service;

import de.holisticon.vorsprechen.niilz.nimgame.model.GameState;
import de.holisticon.vorsprechen.niilz.nimgame.model.GameStateMessage;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessage;
import de.holisticon.vorsprechen.niilz.nimgame.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Main Game functionality
 */
@Service
@Slf4j
public class GameService {

    private static final int MAX_MATCH_COUNT_TO_DRAW = 3;
    private static final int RANDOM_OFFSET = 1;
    private GameState gameState;
    private final Random random;

    GameService() {
        random = new Random();
    }

    private void init(boolean computerOpponent) {
        gameState = new GameState(computerOpponent);
    }
    public boolean isGameStarted() {
        return gameState.getState() == GameState.State.RUNNING;
    }
    public void startGame(boolean computerOpponent) {
        init(computerOpponent);
        try {
            gameState.startGame();
        } catch (IllegalStateException e) {
            log.error("Game could not be started because of illegal state", e);
        }
    }

    public GameStateMessage getGameStateMessage() {
        return GameStateMessage.from(gameState);
    }

    public void resetGame(boolean computerOpponent) {
        gameState = new GameState(computerOpponent);
    }

    public void makeMove(MoveMessage move) {
        log.info("Attempting move for Player '{}' who has drawn '{}' matches",
                move.player().getPosition(), move.player().getCurrentDrawnMatches());
        gameState.makeMove(move.player().getCurrentDrawnMatches(), move.player().getPosition());
    }

    public int decideMatchCountForComputer() {
        var maxMatchesToDraw = Math.min(MAX_MATCH_COUNT_TO_DRAW, gameState.getRemainingMatches());
        var matchCount = random.nextInt(maxMatchesToDraw) + RANDOM_OFFSET;
        assert(matchCount <= gameState.getRemainingMatches());
        return matchCount;
    }

    public boolean isCurrentPlayerComputer() {
        return gameState.getNextPlayer().getType() == Player.PlayerType.COMPUTER;
    }

    public void makeComputerMove() {
        var matchesToDraw = decideMatchCountForComputer();
        var computer = gameState.getCurrentPlayer();
        assert(computer.getType() == Player.PlayerType.COMPUTER);
        gameState.makeMove(matchesToDraw, computer.getPosition());
    }
}
