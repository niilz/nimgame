package de.holisticon.vorsprechen.niilz.nimgame.model;

public record GameStateMessage(int currentMatchCount, GameState.State gameState) {

    public static GameStateMessage from(GameState gameState) {
        return new GameStateMessage(gameState.getRemainingMatches(),
                gameState.getState());
    }
}
