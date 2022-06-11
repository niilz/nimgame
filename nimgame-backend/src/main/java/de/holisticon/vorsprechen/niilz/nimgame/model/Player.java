package de.holisticon.vorsprechen.niilz.nimgame.model;

import lombok.Getter;

@Getter
public class Player {

    private final Player.PlayerType type;
    private int drawnMatches;

    Player(Player.PlayerType type) {
        this.type = type;
    }

    public void addMatches(int matches) {
        this.drawnMatches += matches;
    }

    public enum PlayerType {
        HUMAN, COMPUTER
    }
}
