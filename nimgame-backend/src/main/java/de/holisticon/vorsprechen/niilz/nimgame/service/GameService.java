package de.holisticon.vorsprechen.niilz.nimgame.service;

import de.holisticon.vorsprechen.niilz.nimgame.common.Constants.Error;
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
        gameState = new GameState();
    }

    public boolean isGameStarted() {
        return gameState != null && gameState.getState() == GameState.State.RUNNING;
    }
    public void startGame(boolean computerOpponent) {
        try {
            gameState.startGame(computerOpponent);
        } catch (IllegalStateException e) {
            log.error(Error.ILLEGAL_GAME_STATE, e);
        }
    }

    public void resetGame() {
        gameState = new GameState();
    }

    public void restartGame(boolean computerOpponent) {
        resetGame();
        startGame(computerOpponent);
    }

    /**
     * @return The current state of the game in a client consumable form
     */
    public GameStateMessage getGameStateMessage() {
        return GameStateMessage.from(gameState);
    }

    /**
     * @param move Meta information to make the next move for a player
     */
    public void makeMove(MoveMessage move) {
        if (gameState.getCurrentPlayer().getType() != move.getPlayerType()) {
            throw new IllegalArgumentException(Error.INVALID_PLAYER_TYPE);
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
            throw new IllegalArgumentException(Error.PLAYER_TYPE_NOT_SUPPORTED);
        }
    }

    /**
     * @param playerRank Which rank/position this moves player has (One | Two)
     */
    public void makeComputerMove(Player.PlayerRank playerRank) {
        makeMove(new MoveMessageComputer(playerRank));
    }

    /**
     * @return A randomized amount matches that the computer will draw between 1 and 3, where the max is limited
     * by the amount of remaining matches in the game
     */
    public int decideMatchCountForComputer() {
        var maxMatchesToDraw = Math.min(MAX_MATCH_COUNT_TO_DRAW, gameState.getRemainingMatches());
        var matchCount = random.nextInt(maxMatchesToDraw) + RANDOM_OFFSET;
        assert(matchCount <= gameState.getRemainingMatches());
        return matchCount;
    }

    /**
     * @return The rank/position (one or two) of the current player
     */
    public Player.PlayerRank getCurrentPlayersRank() {
        return gameState.getCurrentPlayer().getRank();
    }

    /**
     * @return The amount of remaining matches in the game (0 == game is finished/won)
     */
    public int getRemainingMatches() {
        return gameState.getRemainingMatches();
    }

    /**
     * @return Whether the current player is of type computer
     */
    public boolean isCurrentPlayerComputer() {
        return gameState.getCurrentPlayer().getType() == Player.PlayerType.COMPUTER;
    }

    /**
     * @param autoPlay Whether the human player wants the computer to make its move automatically
     * @return Whether a automatically invoked computer move is appropriate
     */
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
