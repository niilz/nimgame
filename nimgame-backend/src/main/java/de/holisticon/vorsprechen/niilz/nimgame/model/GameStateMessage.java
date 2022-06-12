package de.holisticon.vorsprechen.niilz.nimgame.model;

public record GameStateMessage(int currentMatchCount, GameState.State gameState,
                               Player.Position currentPlayerPosition) {

    public static GameStateMessage from(GameState gameState) {
        var maybePlayer = gameState.getCurrentPlayer();
        var playerPosition = maybePlayer == null ? null : maybePlayer.getPosition();
        return new GameStateMessage(gameState.getMatches(),
                gameState.getState(), playerPosition);
    }
}
