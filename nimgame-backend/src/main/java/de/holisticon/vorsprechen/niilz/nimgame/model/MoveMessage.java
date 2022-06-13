package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "playerType", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = MoveMessageHuman.class, name = "HUMAN"),
        @JsonSubTypes.Type(value = MoveMessageComputer.class, name = "COMPUTER")})
public class MoveMessage {

    @Getter
    @JsonProperty("playerType")
    private Player.PlayerType playerType;
    @Getter
    private final Player.PlayerRank playerRank;

    public MoveMessage(Player.PlayerRank playerRank) {
        this.playerRank = playerRank;
    }
}
