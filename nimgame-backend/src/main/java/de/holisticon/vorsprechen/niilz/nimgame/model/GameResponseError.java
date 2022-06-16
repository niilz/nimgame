package de.holisticon.vorsprechen.niilz.nimgame.model;

import de.holisticon.vorsprechen.niilz.nimgame.common.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

/**
 * @param error The error that occured during a request
 */
@Schema(description = "Reports an Error triggered by a request", example = Constants.Error.GAME_MUST_BE_STARTED)
public record GameResponseError(@NonNull @NotEmpty String error) implements GameResponse {
}
