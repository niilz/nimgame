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

    public void resetGame() {
        gameState = null;
    }

    public void restartGame(boolean computerOpponent) {
        startGame(computerOpponent);
    }

    public GameStateMessage getGameStateMessage() {
        return GameStateMessage.from(gameState);
    }

    public void makeMove(MoveMessage move) {
        if (gameState.getCurrentPlayer().getType() != move.getPlayerType()) {
            throw new IllegalArgumentException("Player is of wrong type");
        }
        if (move instanceof MoveMessageHuman messageHuman) {
            log.info("Move for Human-Player, who has drawn '{}' matches",
                    messageHuman.getDrawnMatches());
            gameState.makeMove(messageHuman.getDrawnMatches(), move.getPlayerRank());
        } else if (move instanceof MoveMessageComputer) {
            var randomlyDrawnMatches = decideMatchCountForComputer();
            log.info("Move for Computer-Player, who has drawn '{}' matches",
                    randomlyDrawnMatches);
            var computer = gameState.getCurrentPlayer();
            assert(computer.getType() == Player.PlayerType.COMPUTER);
            gameState.makeMove(randomlyDrawnMatches, move.getPlayerRank());
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

    public Player.PlayerRank getCurrentPlayersRank() {
        return gameState.getCurrentPlayer().getRank();
    }

    public int getRemainingMatches() {
        return gameState.getRemainingMatches();
    }

    public boolean isCurrentPlayerComputer() {
        return gameState.getCurrentPlayer().getType() == Player.PlayerType.COMPUTER;
    }

    public boolean shouldMakeAutoMove(boolean autoPlay) {
        // Only allow autoPlay if the current Player is of Type Computer
        if (!isCurrentPlayerComputer()) {
            return false;
        }
        // Only make autoPlay move if user wants it
        if (!autoPlay) {
            return false;
        }
        // Do not make an autoplay if the game is not running anymore
        // (for example if it is already won in the last move)
        return gameState.getState() == GameState.State.RUNNING;
    }
}
