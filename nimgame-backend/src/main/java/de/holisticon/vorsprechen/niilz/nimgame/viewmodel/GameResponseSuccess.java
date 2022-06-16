package de.holisticon.vorsprechen.niilz.nimgame.viewmodel;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @param message The state information about the current game
 */
@Schema(description = "Shows that a request was successful and provices the state information about the game")
public record GameResponseSuccess(GameStateMessage message) implements GameResponse {
}
