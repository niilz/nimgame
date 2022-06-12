package de.holisticon.vorsprechen.niilz.nimgame.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class MoveMessageTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @SneakyThrows
    void deserializationIntoMoveMessageWorks() {
        var moveMessageJson = "{\"playerPosition\": \"ONE\", \"drawnMatches\": 2}";
        var expectedPlayerPosition = Player.Position.ONE;
        var expectedDrawnMatches = 2;
        var deserializedMoveMessage = mapper.readValue(moveMessageJson, MoveMessage.class);
        assertEquals(expectedPlayerPosition, deserializedMoveMessage.playerPosition());
        assertEquals(expectedDrawnMatches, deserializedMoveMessage.drawnMatches());
    }

    @Test
    void invalidPlayerPositionCanNotBeDeserialized() {
        var moveMessageJson = "{\"playerPosition\": \"NONE-EXISTEND\", \"drawnMatches\": 2}";
        assertThrows(JsonMappingException.class, () -> mapper.readValue(moveMessageJson, MoveMessage.class));
    }

}