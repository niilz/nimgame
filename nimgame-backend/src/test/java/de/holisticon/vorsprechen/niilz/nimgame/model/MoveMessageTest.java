package de.holisticon.vorsprechen.niilz.nimgame.model;

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
    void deserializationIntoMoveMessageHumanWorks() {
        var moveMessageHumanJson = "{" +
                    "\"playerRank\": \"ONE\"," +
                    "\"drawnMatches\": 2," +
                    "\"autoPlay\": true" +
                "}";
        var expectedPlayerRank = Player.PlayerRank.ONE;
        var expectedDrawnMatches = 2;
        var expectedAutoPlay = true;
        var deserializedMoveMessage = mapper.readValue(moveMessageHumanJson, MoveMessageHuman.class);
        assertEquals(expectedPlayerRank, deserializedMoveMessage.getPlayerRank());
        assertEquals(expectedDrawnMatches, deserializedMoveMessage.getDrawnMatches());
        assertEquals(expectedAutoPlay, deserializedMoveMessage.isAutoPlay());
    }

    @Test
    @SneakyThrows
    void deserializationIntoMoveMessageComputerWorks() {
        var moveMessageHumanJson = "{" +
                "\"playerRank\": \"TWO\"" +
                "}";
        var expectedPlayerRank = Player.PlayerRank.TWO;
        var deserializedMoveMessage = mapper.readValue(moveMessageHumanJson, MoveMessageComputer.class);
        assertEquals(expectedPlayerRank, deserializedMoveMessage.getPlayerRank());
    }

    @Test
    void invalidPlayerPositionCanNotBeDeserialized() {
        var moveMessageJson = "{\"player\": \"NONE\", \"drawnMatches\": 2}";
        assertThrows(JsonMappingException.class, () -> mapper.readValue(moveMessageJson, MoveMessageHuman.class));
    }

}