package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

/**
 * General Message type that is expected by the API to make move for a player
 *
 * @see MoveMessageComputer
 * @see MoveMessageHuman
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "playerType")
@JsonSubTypes({@JsonSubTypes.Type(value = MoveMessageHuman.class, name = "HUMAN"),
        @JsonSubTypes.Type(value = MoveMessageComputer.class, name = "COMPUTER")})
public abstract class MoveMessage {

    @Getter
    private final Player.PlayerRank playerRank;

    /**
     * Tells if player is a HUMAN or a COMPUTER
     */
    public abstract Player.PlayerType getPlayerType();

    /**
     * @param playerRank Tells if player is ONE or TWO
     */
    public MoveMessage(Player.PlayerRank playerRank) {
        this.playerRank = playerRank;
    }
}
