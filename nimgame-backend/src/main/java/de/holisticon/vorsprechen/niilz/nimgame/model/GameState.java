package de.holisticon.vorsprechen.niilz.nimgame.model;

import lombok.Getter;
import lombok.Setter;

public class GameState {

    public enum State {
        RUNNING, STOPPED, WON,
    }

    @Getter
    @Setter
    private State state;
}
