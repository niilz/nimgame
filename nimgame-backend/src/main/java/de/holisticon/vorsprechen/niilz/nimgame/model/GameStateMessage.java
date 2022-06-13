package de.holisticon.vorsprechen.niilz.nimgame.model;

public record GameStateMessage(Player.PlayerRank player, int currentMatchCount, GameState.State gameState) {

    public static GameStateMessage from(GameState gameState) {
        var rank = gameState.getCurrentPlayer() == null
                ? null
                : gameState.getCurrentPlayer().getRank();
        return new GameStateMessage(rank, gameState.getRemainingMatches(), gameState.getState());
    }
}
