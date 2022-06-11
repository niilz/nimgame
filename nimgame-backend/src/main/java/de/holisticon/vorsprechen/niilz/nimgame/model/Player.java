package de.holisticon.vorsprechen.niilz.nimgame.model;

import lombok.Getter;

@Getter
public class Player {

    private final GameState.PlayerType type;
    private int drawnMatches;

    Player(GameState.PlayerType type) {
        this.type = type;
    }

    public void addMatches(int matches) {
        this.drawnMatches += matches;
    }
}
