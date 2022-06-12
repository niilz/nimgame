package de.holisticon.vorsprechen.niilz.nimgame.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * Representation of the Game's state
 */
@Slf4j
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
        log.info("GameState has been created");
    }

    public void startGame() {
        if (this.state == State.RUNNING) {
            throw new IllegalArgumentException("Running Game cannot be started");
        }
        this.state = State.RUNNING;
        var playerPosition = new Random().nextInt(PLAYER_COUNT);
        this.currentPlayer = players[playerPosition];
        log.info("Game has been started. CurrentPlayer is: {}", this.currentPlayer.getPosition());
    }

    public void deductMatches(int drawnMatches, Player.Position playerPosition) {
        if (drawnMatches < 1 || drawnMatches > 3) {
            throw new IllegalArgumentException("Player is only allowed to draw between 1 and 3 matches");
        }
        if (playerPosition != currentPlayer.getPosition()) {
            throw new IllegalArgumentException("Only the current Player is allowed to draw matches");
        }
        matches -= drawnMatches;
        currentPlayer.addMatches(drawnMatches);
        var newPlayerPosition = playerPosition == Player.Position.ONE
                ? Player.Position.TWO
                : Player.Position.ONE;
        currentPlayer = players[newPlayerPosition.getValue() - 1];
    }

    public enum State {
        RUNNING, STOPPED, WON,
    }
}
