package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoveMessageTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @SneakyThrows
    void deserializationIntoMoveMessageWorks() {
        var moveMessageJson = "{\"player\":" +
                "{" +
                    "\"position\": \"ONE\"," +
                    "\"currentDrawnMatches\": 2," +
                    "\"type\": \"HUMAN\"" +
                "}, " +
                "\"autoPlay\": true}";
        var expectedPlayerPosition = Player.Position.ONE;
        var expectedDrawnMatches = 2;
        var expectedPlayerType = Player.PlayerType.HUMAN;
        var expectedAutoPlay = true;
        var deserializedMoveMessage = mapper.readValue(moveMessageJson, MoveMessage.class);
        assertEquals(expectedPlayerPosition, deserializedMoveMessage.player().getPosition());
        assertEquals(expectedDrawnMatches, deserializedMoveMessage.player().getCurrentDrawnMatches());
        assertEquals(expectedPlayerType, deserializedMoveMessage.player().getType());
        assertEquals(expectedAutoPlay, deserializedMoveMessage.autoPlay());
    }

    @Test
    void invalidPlayerPositionCanNotBeDeserialized() {
        var moveMessageJson = "{\"player\": \"NONE\", \"drawnMatches\": 2}";
        assertThrows(JsonMappingException.class, () -> mapper.readValue(moveMessageJson, MoveMessage.class));
    }

}