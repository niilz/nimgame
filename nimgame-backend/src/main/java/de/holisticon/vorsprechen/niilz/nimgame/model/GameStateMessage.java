package de.holisticon.vorsprechen.niilz.nimgame.model;

import java.util.Optional;

public record GameStateMessage(Player.PlayerRank player, Player.PlayerType type, int currentMatchCount,
                               GameState.State gameState) {

    public static GameStateMessage from(GameState gameState) {
        var rankAndType = Optional.ofNullable(gameState.getCurrentPlayer())
                .map(player -> new Enum[]{player.getRank(), player.getType()})
                .orElse(new Enum[] {null, null});
                return new GameStateMessage((Player.PlayerRank) rankAndType[0], (Player.PlayerType) rankAndType[1],
                        gameState.getRemainingMatches(), gameState.getState());
    }
}
