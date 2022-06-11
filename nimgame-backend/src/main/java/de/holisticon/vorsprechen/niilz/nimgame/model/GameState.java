package de.holisticon.vorsprechen.niilz.nimgame.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Representation of the Game's state
 */
public class GameState {

    static final int INITIAL_MATCH_COUNT = 13;

    @Getter
    private Integer matches;

    private Player player;
    private Player computer;

    public GameState() {
        this.state = State.STOPPED;
        this.matches = INITIAL_MATCH_COUNT;
    }

    public enum State {
        RUNNING, STOPPED, WON,
    }

    @Getter
    @Setter
    private State state;
}
