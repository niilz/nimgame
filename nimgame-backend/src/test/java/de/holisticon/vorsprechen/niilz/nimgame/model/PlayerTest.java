package de.holisticon.vorsprechen.niilz.nimgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
   private static final int ZERO_MATCHES = 0;

    @Test
    void playerHasGivenNameOnInitialization() {
        var expectedType = Player.PlayerType.HUMAN;
        var player = new Player(expectedType);
        assertEquals(expectedType, player.getType());
    }

    @Test
    void playerHasNoDrawnMatchesOnInitialization() {
        var player = new Player(Player.PlayerType.HUMAN);
        assertEquals(ZERO_MATCHES, player.getDrawnMatches());
    }

}