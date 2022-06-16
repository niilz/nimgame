package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(oneOf = {MoveMessageHuman.class, MoveMessageComputer.class})
public abstract class MoveMessage {

    @Getter
    private final Player.PlayerRank player;

    /**
     * Tells if player is a HUMAN or a COMPUTER
     */
    public abstract Player.PlayerType getPlayerType();

    /**
     * @param player Tells if player is ONE or TWO
     */
    public MoveMessage(Player.PlayerRank player) {
        this.player = player;
    }
}
