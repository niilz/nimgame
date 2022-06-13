package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.swing.text.Position;

@Getter
public class Player {

    private PlayerType type;

    @JsonIgnore
    private int drawnMatchesTotal;

    private Player() {}

    public static Player[] createPlayers() {
        return createPlayers(true);
    }

    public static Player[] createPlayers(boolean isTwoComputer) {
        var playerOne = createPlayer();
        var playerTwo = isTwoComputer
                ? createPlayer(PlayerType.COMPUTER)
                : createPlayer();
        return new Player[] {playerOne, playerTwo};
    }

    private static Player createPlayer() {
        return createPlayer(PlayerType.HUMAN);
    }
    private static Player createPlayer(PlayerType type) {
        var player = new Player();
        player.type = type;
        return player;
    }
    public void addMatches(int matches) {
        this.drawnMatchesTotal += matches;
    }

    public enum PlayerType {
        HUMAN, COMPUTER
    }
}
