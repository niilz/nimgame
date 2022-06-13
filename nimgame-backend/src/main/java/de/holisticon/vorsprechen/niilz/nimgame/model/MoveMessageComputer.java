package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MoveMessageComputer extends MoveMessage {

    private final Player.PlayerType playerType = Player.PlayerType.COMPUTER;

    @JsonCreator
    public MoveMessageComputer(@JsonProperty("playerRank") Player.PlayerRank playerRank) {
        super(playerRank);
    }
}
