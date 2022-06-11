package de.holisticon.vorsprechen.niilz.nimgame.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GameService.class)
class GameServiceTest {

    private final GameService gameService;

    @Autowired
    public GameServiceTest(GameService gameService) {
        this.gameService = gameService;
    }

    @Test
    void gameIsStartedAfterStartingTheGame() {
        assertFalse(gameService.isGameStarted());
        gameService.startgame();
        assertTrue(gameService.isGameStarted());
    }
}