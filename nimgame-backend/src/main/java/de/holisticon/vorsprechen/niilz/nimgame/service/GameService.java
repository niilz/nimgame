package de.holisticon.vorsprechen.niilz.nimgame.service;

import de.holisticon.vorsprechen.niilz.nimgame.model.GameState;
import de.holisticon.vorsprechen.niilz.nimgame.model.GameStateMessage;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessage;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessageComputer;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessageHuman;
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
        return gameState != null && gameState.getState() == GameState.State.RUNNING;
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
        if (move instanceof MoveMessageHuman messageHuman) {
            log.info("Attempting move for Human-Player, who has drawn '{}' matches",
                    messageHuman.getDrawnMatches());
            gameState.makeMove(messageHuman.getDrawnMatches(), move.getPlayerRank());
        } else if (move instanceof MoveMessageComputer) {
            var matchesToDraw = decideMatchCountForComputer();
            var computer = gameState.getCurrentPlayer();
            assert(computer.getType() == Player.PlayerType.COMPUTER);
            gameState.makeMove(matchesToDraw, move.getPlayerRank());
        } else {
            throw new IllegalArgumentException("Move must be of type Human or Computer");
        }
    }

    public void makeComputerMove(Player.PlayerRank playerRank) {
        makeMove(new MoveMessageComputer(playerRank));
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

}
