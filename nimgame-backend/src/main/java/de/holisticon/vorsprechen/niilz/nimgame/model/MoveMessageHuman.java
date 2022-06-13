package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MoveMessageHuman extends MoveMessage {
    private final int drawnMatches;
    private final boolean autoPlay;

    @JsonCreator
    public MoveMessageHuman(@JsonProperty("playerRank") Player.PlayerRank playerRank,
                            @JsonProperty("drawnMatches") int drawnMatches,
                            @JsonProperty("autoPlay") boolean autoPlay) {
        super(playerRank);
        this.drawnMatches = drawnMatches;
        this.autoPlay = autoPlay;
    }
}
