package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Meta-Information which represents a game move for a computer player
 */
@Schema(description = "Represents the information of a game move made by a human player")
@Getter
public class MoveMessageHuman extends MoveMessage {

    private final Player.PlayerType playerType = Player.PlayerType.HUMAN;
    @Min(1)
    @Max(3)
    private final int drawnMatches;

    private final boolean autoPlay;

    /**
     * @param playerRank Tells if player is ONE or TWO
     * @param drawnMatches The number of drawn matches by the player
     * @param autoPlay Should computer play on its own
     */
    @JsonCreator
    public MoveMessageHuman(@JsonProperty("playerRank") Player.PlayerRank playerRank,
                            @JsonProperty("drawnMatches") int drawnMatches,
                            @JsonProperty("autoPlay") boolean autoPlay) {
        super(playerRank);
        this.drawnMatches = drawnMatches;
        this.autoPlay = autoPlay;
    }
}
