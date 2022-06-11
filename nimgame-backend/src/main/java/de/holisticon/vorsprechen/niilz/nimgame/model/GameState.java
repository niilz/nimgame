package de.holisticon.vorsprechen.niilz.nimgame.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

public class GameState {

    private final int INITIAL_MATCH_COUNT = 13;
    private Integer matches;

    GameState() {
        this.matches = INITIAL_MATCH_COUNT;
    }

    public enum State {
        RUNNING, STOPPED, WON,
    }

    @Getter
    @Setter
    private State state;
}
