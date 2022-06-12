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
    private Integer remainingMatches;

    @Getter
    private Player currentPlayer;
    @Getter
    private Player nextPlayer;

    private Player[] players;

    public GameState(boolean computerOpponent) {
        this.state = State.STOPPED;
        this.remainingMatches = INITIAL_MATCH_COUNT;
        this.players = Player.createPlayers(computerOpponent);
        log.info("GameState has been created");
    }

    public void startGame() {
        if (this.state == State.RUNNING) {
            throw new IllegalArgumentException("Running Game cannot be started");
        }
        this.state = State.RUNNING;
        this.currentPlayer = players[0];
        this.nextPlayer = players[1];
        // Randomize which player starts the game
        if (new Random().nextBoolean()) {
            swapPlayers();
        }
        log.info("Game has been started. CurrentPlayer is: {}", this.currentPlayer.getPosition());
    }

    void swapPlayers() {
        var tempPlayer = this.currentPlayer;
        this.currentPlayer = this.nextPlayer;
        this.nextPlayer = tempPlayer;
    }
    public void makeMove(int drawnMatches, Player.Position playerPosition) {
        if (drawnMatches < 1 || drawnMatches > 3) {
            throw new IllegalArgumentException("Player is only allowed to draw between 1 and 3 matches");
        }
        if (playerPosition != currentPlayer.getPosition()) {
            throw new IllegalArgumentException("Only the current Player is allowed to draw matches");
        }
        remainingMatches -= drawnMatches;
        currentPlayer.addMatches(drawnMatches);
        swapPlayers();
    }

    public enum State {
        RUNNING, STOPPED, WON,
    }
}
