package de.holisticon.vorsprechen.niilz.nimgame.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * Representation of the Game's state
 */
@Slf4j
public class GameState {

    public static final int INITIAL_MATCH_COUNT = 13;

    @Getter
    private State state;
    @Getter
    private Integer remainingMatches;

    private boolean playAgainstComputer;

    @Getter
    private Player currentPlayer;
    @Getter
    private Player nextPlayer;

    public GameState() {
        this.state = State.STOPPED;
        this.remainingMatches = INITIAL_MATCH_COUNT;
        log.info("GameState has been created");
    }
    public void setComputerOpponent(boolean playAgainstComputer) {
        this.playAgainstComputer = playAgainstComputer;
    }

    public void startGame() {
        startGame(false);
    }
    public void startGame(boolean playAgainstComputer) {
        if (this.state == State.RUNNING) {
            throw new IllegalArgumentException("Running Game cannot be started");
        }
        setComputerOpponent(playAgainstComputer);
        this.state = State.RUNNING;
        var players = Player.createPlayers(playAgainstComputer);
        this.currentPlayer = players[0];
        this.nextPlayer = players[1];
        // Randomize which player starts the game
        if (new Random().nextBoolean()) {
            swapPlayers();
        }
        log.info("Game has been started");
    }

    void swapPlayers() {
        var tempPlayer = this.currentPlayer;
        this.currentPlayer = this.nextPlayer;
        this.nextPlayer = tempPlayer;
    }
    public void makeMove(int drawnMatches, Player.PlayerRank rank) {
        if (state == State.WON) {
            throw new IllegalStateException("Game is already won");
        }
        if (rank != currentPlayer.getRank()) {
            throw new IllegalArgumentException("Same Player must not play again");
        }
        if (drawnMatches < 1 || drawnMatches > 3) {
            throw new IllegalArgumentException("Player is only allowed to draw between 1 and 3 matches");
        }
        if (drawnMatches > remainingMatches) {
            throw new IllegalArgumentException("Player must not draw more matches than there are remaining");
        }
        remainingMatches -= drawnMatches;
        currentPlayer.addMatches(drawnMatches);
        swapPlayers();
        if (remainingMatches == 0) {
            state = State.WON;
            log.info("Game has been finished");
        }
    }

    public enum State {
        RUNNING, STOPPED, WON,
    }
}
