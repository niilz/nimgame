package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class MoveMessage {

    @Getter
    private final Player.PlayerRank playerRank;

    public MoveMessage(Player.PlayerRank playerRank) {
        this.playerRank = playerRank;
    }
}
