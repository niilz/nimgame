package de.holisticon.vorsprechen.niilz.nimgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
   private static final int ZERO_MATCHES = 0;

    @Test
    void playersHaveGivenCorrectTypeOnInitalization() {
        var expectedTypePlayerOne = Player.PlayerType.HUMAN;
        var expectedTypePlayerTwo = Player.PlayerType.COMPUTER;
        var expectedRankPlayerOne = Player.PlayerRank.ONE;
        var expectedRankPlayerTwo = Player.PlayerRank.TWO;
        var players = Player.createPlayers();
        var playerOne = players[0];
        assertEquals(expectedTypePlayerOne, playerOne.getType());
        assertEquals(expectedRankPlayerOne, playerOne.getRank());
        var playerTwo = players[1];
        assertEquals(expectedTypePlayerTwo, playerTwo.getType());
        assertEquals(expectedRankPlayerTwo, playerTwo.getRank());
        var expectedTypeHuman = Player.PlayerType.HUMAN;
        var twoHumanPlayers = Player.createPlayers(false);
        var playerOneHuman = twoHumanPlayers[0];
        assertEquals(expectedTypeHuman, playerOneHuman.getType());
        var playerTwoHuman = twoHumanPlayers[1];
        assertEquals(expectedTypeHuman, playerTwoHuman.getType());
    }

    @Test
    void playersHaveNoDrawnMatchesOnInitialization() {
        var players = Player.createPlayers();
        assertEquals(ZERO_MATCHES, players[0].getDrawnMatchesTotal());
        assertEquals(ZERO_MATCHES, players[1].getDrawnMatchesTotal());
    }

}