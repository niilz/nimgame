package de.holisticon.vorsprechen.niilz.nimgame.model;

import lombok.Getter;

import java.util.Random;

/**
 * Representation of the Game's state
 */
public class GameState {

    private static final int PLAYER_COUNT = 2;

    static final int INITIAL_MATCH_COUNT = 13;

    @Getter
    private State state;
    @Getter
    private Integer matches;

    @Getter
    private Player currentPlayer;

    private Player[] players;

    public GameState() {
        this.state = State.STOPPED;
        this.matches = INITIAL_MATCH_COUNT;
        this.players = Player.createPlayers();
    }

    public enum State {
        RUNNING, STOPPED, WON,
    }

    public void startGame() {
        if (this.state == State.RUNNING) {
            throw new IllegalArgumentException("Running Game cannot be started");
        }
        this.state = State.RUNNING;
        var playerPosition = new Random().nextInt(PLAYER_COUNT);
        this.currentPlayer = players[playerPosition];
    }
}
