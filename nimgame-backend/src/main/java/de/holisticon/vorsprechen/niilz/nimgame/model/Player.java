package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.swing.text.Position;

/**
 * Model representation of a NimGame player
 */
@Getter
public class Player {

    private PlayerType type;
    private PlayerRank rank;

    @JsonIgnore
    private int drawnMatchesTotal;

    private Player() {}

    /**
     * @return Two Players where the first is a {@link PlayerType#HUMAN} and the second is {@link PlayerType#COMPUTER}
     */
    public static Player[] createPlayers() {
        return createPlayers(true);
    }

    /**
     * @param isTwoComputer Whether the second player should be a {@link PlayerType#COMPUTER}
     * @return Two Players
     */
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

    /**
     * @param matches The matches that have just been drawn
     *                (not important for the endresult of this NimGame)
     */
    public void addMatches(int matches) {
        this.drawnMatchesTotal += matches;
    }

    /**
     * Represents whether the player is a human or a computer
     */
    public enum PlayerType {
        HUMAN, COMPUTER
    }

    /**
     * Whether the player on rank/position one or two
     */
    public enum PlayerRank {
        ONE, TWO
    }
}
