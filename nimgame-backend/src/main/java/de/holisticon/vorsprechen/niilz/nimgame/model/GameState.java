package de.holisticon.vorsprechen.niilz.nimgame.model;

import lombok.Getter;

/**
 * Representation of the Game's state
 */
public class GameState {

    static final int INITIAL_MATCH_COUNT = 13;

    @Getter
    private State state;
    @Getter
    private Integer matches;

    @Getter
    private Player currentPlayer;

    private Player playerOne;
    private Player playerTwo;

    public GameState() {
        this.state = State.STOPPED;
        this.matches = INITIAL_MATCH_COUNT;
        this.playerOne = new Player(Player.PlayerType.HUMAN);
        this.playerTwo = new Player(Player.PlayerType.COMPUTER);
    }

    public enum State {
        RUNNING, STOPPED, WON,
    }

    public void startGame() {
        if (this.state == State.RUNNING) {
            throw new IllegalArgumentException("Running Game cannot be started");
        }
        this.state = State.RUNNING;
    }
}
