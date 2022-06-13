package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MoveMessageComputer extends MoveMessage {

    @JsonCreator
    public MoveMessageComputer(@JsonProperty("playerRank") Player.PlayerRank playerRank) {
        super(playerRank);
    }
}
