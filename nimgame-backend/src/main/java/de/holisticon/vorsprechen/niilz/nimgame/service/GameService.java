package de.holisticon.vorsprechen.niilz.nimgame.service;

import de.holisticon.vorsprechen.niilz.nimgame.model.GameState;
import org.springframework.stereotype.Service;

/**
 * Main Game functionality
 */
@Service
public class GameService {

    private final GameState gameState;

    GameService() {
        this.gameState = new GameState();
    }

    public boolean isGameStarted() {
        return this.gameState.isStarted();
    }
    public void startgame() {
        this.gameState.setStarted(true);
    }
}
