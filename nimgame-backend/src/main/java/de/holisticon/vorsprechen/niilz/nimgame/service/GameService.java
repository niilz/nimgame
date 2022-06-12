package de.holisticon.vorsprechen.niilz.nimgame.service;

import de.holisticon.vorsprechen.niilz.nimgame.model.GameState;
import de.holisticon.vorsprechen.niilz.nimgame.model.GameStateMessage;
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
}
