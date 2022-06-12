package de.holisticon.vorsprechen.niilz.nimgame.model;

import lombok.Getter;

@Getter
public class Player {

    private Position position;
    private PlayerType type;
    private int drawnMatches;

    private Player() {}

    public static Player[] createPlayers() {
        return createPlayers(true);
    }

    public static Player[] createPlayers(boolean isTwoComputer) {
        var playerOne = createPlayer(Position.ONE);
        var playerTwo = isTwoComputer
                ? createPlayer(Position.TWO, PlayerType.COMPUTER)
                : createPlayer(Position.TWO);
        return new Player[] {playerOne, playerTwo};
    }

    private static Player createPlayer(Position position) {
        return createPlayer(position, PlayerType.HUMAN);
    }
    private static Player createPlayer(Position position, PlayerType type) {
        var player = new Player();
        player.position = position;
        player.type = type;
        return player;
    }
    public void addMatches(int matches) {
        this.drawnMatches += matches;
    }

    public enum Position {
        ONE, TWO,
    }

    public enum PlayerType {
        HUMAN, COMPUTER
    }
}
