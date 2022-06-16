package de.holisticon.vorsprechen.niilz.nimgame.viewmodel;

import de.holisticon.vorsprechen.niilz.nimgame.service.GameState;
import de.holisticon.vorsprechen.niilz.nimgame.model.Player;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

/**
 * @param player Whether the current player is {@link de.holisticon.vorsprechen.niilz.nimgame.model.Player.PlayerRank#ONE}
 *               or {@link de.holisticon.vorsprechen.niilz.nimgame.model.Player.PlayerRank#TWO}
 * @param type Wheter the current player is of type {@link de.holisticon.vorsprechen.niilz.nimgame.model.Player.PlayerType#HUMAN}
 *             or of type {@link de.holisticon.vorsprechen.niilz.nimgame.model.Player.PlayerType#COMPUTER}
 * @param currentMatchCount How many matches are remaining in the game
 * @param gameState Which state the current game has {@link GameState.State}
 */
public record GameStateMessage(Player.PlayerRank player,
                               Player.PlayerType type,
                               @Min(0) @Max(GameState.INITIAL_MATCH_COUNT) int currentMatchCount,
                               GameState.State gameState) {

    /**
     * @param gameState The current {@link GameState}
     * @return Client consumable representation of the current state of the game
     */
    public static GameStateMessage from(GameState gameState) {
        var rankAndType = Optional.ofNullable(gameState.getCurrentPlayer())
                .map(player -> new Enum[]{player.getRank(), player.getType()})
                .orElse(new Enum[] {null, null});
                return new GameStateMessage((Player.PlayerRank) rankAndType[0], (Player.PlayerType) rankAndType[1],
                        gameState.getRemainingMatches(), gameState.getState());
    }
}
