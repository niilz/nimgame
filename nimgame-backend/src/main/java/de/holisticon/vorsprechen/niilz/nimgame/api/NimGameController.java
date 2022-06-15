package de.holisticon.vorsprechen.niilz.nimgame.api;

import de.holisticon.vorsprechen.niilz.nimgame.model.GameResponse;
import de.holisticon.vorsprechen.niilz.nimgame.model.GameResponseError;
import de.holisticon.vorsprechen.niilz.nimgame.model.GameResponseSuccess;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessage;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessageHuman;
import de.holisticon.vorsprechen.niilz.nimgame.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Application Interface to play the NimGame with REST-Calls
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1")
@Tag(name = "nimgame", description = "The NimGame-Api")
@Slf4j
public record NimGameController(GameService gameService) {

    @GetMapping("/state")
    @Operation(summary = "Current GameState", description = "Retreive the current state of the game", tags = {"nimgame"})
    @ApiResponses(@ApiResponse(responseCode = "200", description = "GameState could be read successfully",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GameResponseSuccess.class))}))
    public ResponseEntity<GameResponse> getState() {
        var message = new GameResponseSuccess(gameService.getGameStateMessage());
        return ResponseEntity.ok(message);
    }

    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
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
            @Parameter(name = "autoPlay", description = "Whether the computer should play immediately by itself if it is its turn first")
    })
    public ResponseEntity<GameResponse> initGame(
            @RequestParam(required = false) boolean computerOpponent,
            @RequestParam(required = false) boolean autoPlay) {
        if (gameService.isGameStarted()) {
            var error = new GameResponseError("Game has already been started");
            return ResponseEntity.badRequest().body(error);
        }
        gameService.startGame(computerOpponent);
        maybeMakeAutoMove(autoPlay);
        var message = new GameResponseSuccess(gameService.getGameStateMessage());
        return ResponseEntity.ok(message);
    }

    @PostMapping(value = "/restart", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Restart the game", description = "Start a new NimGame no matter if a current game is already running", tags = {"nimgame"})
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Game was restarted successfully", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GameResponseSuccess.class))
    }))
    @Parameters(value = {
            @Parameter(name = "computerOpponent", description = "Whether one player should be a computer"),
            @Parameter(name = "autoPlay", description = "Whether the computer should play immediately by itself if it is its turn first")
    })
    public ResponseEntity<GameResponse> restart(
            @RequestParam(required = false) boolean computerOpponent,
            @RequestParam(required = false) boolean autoPlay) {
        gameService.restartGame(computerOpponent);
        maybeMakeAutoMove(autoPlay);
        var message = new GameResponseSuccess(gameService.getGameStateMessage());
        return ResponseEntity.ok(message);
    }

    @PostMapping(value = "/draw", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Draw matches", description = "Make a move for a player an draw matches", tags = {"nimgame"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Move was successfull",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GameResponseSuccess.class))}),
            @ApiResponse(responseCode = "400", description = "Move-Attempt caused an error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GameResponseError.class))})
    })
    public ResponseEntity<GameResponse> drawMatches(@RequestBody
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
                                                        })) MoveMessage move) {
        if (!gameService.isGameStarted()) {
            var error = new GameResponseError("Game must be started before matches can be drawn");
            return ResponseEntity.badRequest().body(error);
        }
        try {
            if (move instanceof MoveMessageHuman humanMove) {
                // MakesMove (which includes swapping the players)
                gameService.makeMove(humanMove);
                maybeMakeAutoMove(humanMove.isAutoPlay());
            } else {
                gameService.makeComputerMove(move.getPlayerRank());
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(new GameResponseError(e.getMessage()));
        }
        var message = new GameResponseSuccess(gameService.getGameStateMessage());
        return ResponseEntity.ok(message);
    }

    private void maybeMakeAutoMove(boolean autoPlay) {
        // Autoplay means trigger the computer-move immediately
        // (autoplay is ignored if next-player is not a computer
        // Also, if Computer starts and User wants autoPlay,
        // then immediately make move for Computer on start
        if (gameService.shouldMakeAutoMove(autoPlay)) {
            gameService.makeComputerMove(gameService.getCurrentPlayersRank());
        }
    }
}
