package de.holisticon.vorsprechen.niilz.nimgame.api;

import de.holisticon.vorsprechen.niilz.nimgame.common.Constants;
import de.holisticon.vorsprechen.niilz.nimgame.viewmodel.GameResponse;
import de.holisticon.vorsprechen.niilz.nimgame.viewmodel.GameResponseError;
import de.holisticon.vorsprechen.niilz.nimgame.viewmodel.GameResponseSuccess;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface NimGameApi {

    @Operation(summary = "Current GameState", description = "Retreive the current state of the game", tags = {"nimgame"})
    @ApiResponses(@ApiResponse(responseCode = "200", description = "GameState could be read successfully",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GameResponseSuccess.class))}))
    ResponseEntity<GameResponse> getState();

    @Operation(summary = "Start the game", description = "Start a new NimGame, as long as it is not already running", tags = {"nimgame"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game has been started successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GameResponseSuccess.class))}),
            @ApiResponse(responseCode = "400", description = "Game could not be started",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GameResponseError.class))})
    })
    @Parameters(value = {
            @Parameter(name = "computerOpponent", description = "Whether one player should be a computer"),
            @Parameter(name = "playSmart", description = "Whether computer should play smart"),
            @Parameter(name = "autoPlay", description = "Whether the computer should play immediately by itself if it is its turn first")
    })
    ResponseEntity<GameResponse> initGame(
            @RequestParam(required = false) boolean computerOpponent,
            @RequestParam(required = false) boolean playSmart,
            @RequestParam(required = false) boolean autoPlay);

    @Operation(summary = "Restart the game", description = "Start a new NimGame no matter if a current game is already running", tags = {"nimgame"})
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Game was restarted successfully", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GameResponseSuccess.class))
    }))
    @Parameters(value = {
            @Parameter(name = "computerOpponent", description = "Whether one player should be a computer"),
            @Parameter(name = "playSmart", description = "Whether the computer should play smart"),
            @Parameter(name = "autoPlay", description = "Whether the computer should play immediately by itself if it is its turn first")
    })
    ResponseEntity<GameResponse> restart(
            @RequestParam(required = false) boolean computerOpponent,
            @RequestParam(required = false) boolean playSmart,
            @RequestParam(required = false) boolean autoPlay);

    @Operation(summary = "Draw matches", description = "Make a move for a player an draw matches", tags = {"nimgame"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Move was successfull",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GameResponseSuccess.class))}),
            @ApiResponse(responseCode = "400", description = "Move-Attempt caused an error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GameResponseError.class, example = Constants.Error.WRONG_MATCH_COUNT_DRAWN))})
    })
    ResponseEntity<GameResponse> drawMatches(@RequestBody
                                                     (content = @Content(examples = {
                                                             @ExampleObject(name = "MoveMessageHuman",
                                                                     summary = "A human move",
                                                                     description = "The Move Request if a human plays",
                                                                     value = "{" +
                                                                             "\"playerRank\": \"ONE\"," +
                                                                             "\"playerType\": \"HUMAN\"," +
                                                                             "\"drawnMatches\": 1," +
                                                                             "\"autoPlay\": true" +
                                                                             "}"),
                                                             @ExampleObject(name = "MoveMessageComputer",
                                                                     summary = "A computer move",
                                                                     description = "The Move Request if a computer plays",
                                                                     value = "{" +
                                                                             "\"playerRank\": \"ONE\"," +
                                                                             "\"playerType\": \"COMPUTER\"" +
                                                                             "}"),
                                                     })) MoveMessage move);

}