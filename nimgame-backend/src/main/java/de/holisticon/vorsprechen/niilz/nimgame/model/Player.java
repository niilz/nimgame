package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.swing.text.Position;

@Getter
public class Player {

    private PlayerType type;
    private PlayerRank rank;

    @JsonIgnore
    private int drawnMatchesTotal;

    private Player() {}

    public static Player[] createPlayers() {
        return createPlayers(true);
    }

    public static Player[] createPlayers(boolean isTwoComputer) {
        var playerOne = createPlayer(PlayerRank.ONE);
        var playerTwo = isTwoComputer
                ? createPlayer(PlayerRank.TWO, PlayerType.COMPUTER)
                : createPlayer(PlayerRank.TWO);
        return new Player[] {playerOne, playerTwo};
    }

    private static Player createPlayer(PlayerRank rank) {
        return createPlayer(rank, PlayerType.HUMAN);
    }
    private static Player createPlayer(PlayerRank rank, PlayerType type) {
        var player = new Player();
        player.rank = rank;
        player.type = type;
        return player;
    }
    public void addMatches(int matches) {
        this.drawnMatchesTotal += matches;
    }

    public enum PlayerType {
        HUMAN, COMPUTER
    }

    public enum PlayerRank {
        ONE, TWO
    }
}
