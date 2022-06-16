package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Meta-Information which represents a game move for a computer player
 */
@Schema(description = "Represents the information of a game move made by a computer player")
public class MoveMessageComputer extends MoveMessage {

    @Getter
    private final Player.PlayerType playerType = Player.PlayerType.COMPUTER;

    /**
     * @param player Tells if player is ONE or TWO
     */
    @JsonCreator
    public MoveMessageComputer(@JsonProperty("player") Player.PlayerRank player) {
        super(player);
    }
}
