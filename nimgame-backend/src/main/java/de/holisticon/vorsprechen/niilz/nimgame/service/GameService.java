package de.holisticon.vorsprechen.niilz.nimgame.service;

import de.holisticon.vorsprechen.niilz.nimgame.model.GameState;
import de.holisticon.vorsprechen.niilz.nimgame.model.GameStateMessage;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Main Game functionality
 */
@Service
@Slf4j
public class GameService {

    private GameState gameState;

    GameService() {
        this.gameState = new GameState();
    }

    public boolean isGameStarted() {
        return this.gameState.getState() == GameState.State.RUNNING;
    }
    public void startGame() {
        try {
            this.gameState.startGame();
        } catch (IllegalStateException e) {
            log.error("Game could not be started because of illegal state", e);
        }
    }

    public GameStateMessage getGameStateMessage() {
        return GameStateMessage.from(this.gameState);
    }

    public void resetGame() {
        this.gameState = new GameState();
    }

    public void makeMove(MoveMessage move) {
        log.info("Attempting move for Player '{}' who has drawn '{}' matches",
                move.player().getPosition(), move.player().getCurrentDrawnMatches());
        this.gameState.deductMatches(move.player().getCurrentDrawnMatches(), move.player().getPosition());
        // TODO: if human and autopla make auto move
        //if (move.player().getType())
    }
}
