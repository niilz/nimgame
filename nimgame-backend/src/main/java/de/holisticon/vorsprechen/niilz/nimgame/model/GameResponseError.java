package de.holisticon.vorsprechen.niilz.nimgame.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

/**
 * @param error The error that occured during a request
 */
@Schema(description = "Reports an Error triggered by a request", example = "The Game must be started before any matches can be drawn")
public record GameResponseError(@NonNull @NotEmpty String error) implements GameResponse {
}
