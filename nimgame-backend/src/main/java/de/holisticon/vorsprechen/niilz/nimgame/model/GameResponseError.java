package de.holisticon.vorsprechen.niilz.nimgame.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @param error The error that occured during a request
 */
@Schema(description = "Reports an Error triggered by a request")
public record GameResponseError(String error) implements GameResponse {
}
