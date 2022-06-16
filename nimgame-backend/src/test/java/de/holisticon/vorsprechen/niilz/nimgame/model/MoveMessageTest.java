package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoveMessageTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @SneakyThrows
    void deserializationIntoMoveMessageHumanWorks() {
        var moveMessageHumanJson = "{" +
                    "\"playerType\": \"HUMAN\"," +
                    "\"playerRank\": \"ONE\"," +
                    "\"drawnMatches\": 2," +
                    "\"autoPlay\": true" +
                "}";
        var expectedPlayerType = Player.PlayerType.HUMAN;
        var expectedPlayerRank = Player.PlayerRank.ONE;
        var expectedDrawnMatches = 2;
        var expectedAutoPlay = true;
        var deserializedMoveMessage = mapper.readValue(moveMessageHumanJson, MoveMessageHuman.class);
        assertEquals(expectedPlayerType, deserializedMoveMessage.getPlayerType());
        assertEquals(expectedPlayerRank, deserializedMoveMessage.getPlayer());
        assertEquals(expectedDrawnMatches, deserializedMoveMessage.getDrawnMatches());
        assertEquals(expectedAutoPlay, deserializedMoveMessage.isAutoPlay());
    }

    @Test
    @SneakyThrows
    void deserializationIntoMoveMessageComputerWorks() {
        var moveMessageHumanJson = "{" +
                "\"playerType\": \"COMPUTER\"," +
                "\"playerRank\": \"TWO\"" +
                "}";
        var expectedPlayerRank = Player.PlayerRank.TWO;
        var deserializedMoveMessage = mapper.readValue(moveMessageHumanJson, MoveMessageComputer.class);
        assertEquals(expectedPlayerRank, deserializedMoveMessage.getPlayer());
    }

    @Test
    void invalidPlayerTypeCanNotBeDeserialized() {
        var moveMessageJson = "{\"playerType\": \"NONE\", \"drawnMatches\": 2}";
        assertThrows(JsonMappingException.class, () -> mapper.readValue(moveMessageJson, MoveMessageHuman.class));
    }

    @Test
    @SneakyThrows
    void deserializationIntoMoveMessageSuperType() {
        var moveMessageHumanJson = "{" +
                "\"playerType\": \"HUMAN\"," +
                "\"playerRank\": \"ONE\"," +
                "\"drawnMatches\": 2," +
                "\"autoPlay\": true" +
                "}";
        var expectedPlayerRank = Player.PlayerRank.ONE;
        var deserializedMoveMessageHuman = mapper.readValue(moveMessageHumanJson, MoveMessage.class);
        assertEquals(Player.PlayerType.HUMAN, deserializedMoveMessageHuman.getPlayerType());
        assertEquals(expectedPlayerRank, deserializedMoveMessageHuman.getPlayer());
        assertTrue(deserializedMoveMessageHuman instanceof MoveMessageHuman);

        var moveMessageComputerJson = "{" +
                "\"playerType\": \"COMPUTER\"," +
                "\"playerRank\": \"ONE\"" +
                "}";
        var deserializedMoveMessageComputer = mapper.readValue(moveMessageComputerJson, MoveMessage.class);
        assertEquals(Player.PlayerType.COMPUTER, deserializedMoveMessageComputer.getPlayerType());
        assertEquals(expectedPlayerRank, deserializedMoveMessageComputer.getPlayer());
        assertTrue(deserializedMoveMessageComputer instanceof MoveMessageComputer);
    }

}