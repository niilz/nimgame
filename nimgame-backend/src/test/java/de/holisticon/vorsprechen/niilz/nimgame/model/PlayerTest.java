package de.holisticon.vorsprechen.niilz.nimgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
   private static final int ZERO_MATCHES = 0;
   
    @Test
    void playerHasGivenNameOnInitialization() {
        var expectedName = "dummy";
        var player = new Player(expectedName);
        assertEquals(expectedName, player.getName());
    }

    @Test
    void playerHasNoDrawnMatchesOnInitialization() {
        var player = new Player("new_user");
        assertEquals(ZERO_MATCHES, player.getDrawnMatches());
    }

}