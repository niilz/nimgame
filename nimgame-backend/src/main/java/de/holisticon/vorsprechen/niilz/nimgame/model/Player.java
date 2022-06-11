package de.holisticon.vorsprechen.niilz.nimgame.model;

import lombok.Getter;

@Getter
public class Player {

    private final String name;
    private int drawnMatches;

    Player(String name) {
        this.name = name;
    }

    public void addMatches(int matches) {
        this.drawnMatches += matches;
    }
}
