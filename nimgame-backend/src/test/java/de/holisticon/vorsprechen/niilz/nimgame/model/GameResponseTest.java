package de.holisticon.vorsprechen.niilz.nimgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameResponseTest {

    @Test
    void canCreateGameResponseWithError() {
        var error = new GameResponseError("Some Error");
        assertTrue(error instanceof  GameResponse);
    }

    @Test
    void canCreateGameResponseWithStateMessage() {
        var gameState = new GameState();
        var message = GameStateMessage.from(gameState);
        var error = new GameResponseSuccess(message);
        assertTrue(error instanceof  GameResponse);
    }

}