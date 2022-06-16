package de.holisticon.vorsprechen.niilz.nimgame.api;

import de.holisticon.vorsprechen.niilz.nimgame.common.Constants;
import de.holisticon.vorsprechen.niilz.nimgame.viewmodel.GameResponse;
import de.holisticon.vorsprechen.niilz.nimgame.viewmodel.GameResponseError;
import de.holisticon.vorsprechen.niilz.nimgame.viewmodel.GameResponseSuccess;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessage;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessageHuman;
import de.holisticon.vorsprechen.niilz.nimgame.service.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public record NimGameApiImpl(GameService gameService) implements NimGameApi {

    @Override
    @GetMapping("/state")
    public ResponseEntity<GameResponse> getState() {
        var message = new GameResponseSuccess(gameService.getGameStateMessage());
        return ResponseEntity.ok(message);
    }

    @Override
    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameResponse> initGame(
            @RequestParam(required = false) boolean computerOpponent,
            @RequestParam(required = false) boolean autoPlay) {
        if (gameService.isGameStarted()) {
            var error = new GameResponseError(Constants.Error.GAME_ALREADY_STARTED);
            return ResponseEntity.badRequest().body(error);
        }
        gameService.startGame(computerOpponent);
        maybeMakeAutoMove(autoPlay);
        var message = new GameResponseSuccess(gameService.getGameStateMessage());
        return ResponseEntity.ok(message);
    }

    @Override
    @PostMapping(value = "/restart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameResponse> restart(
            @RequestParam(required = false) boolean computerOpponent,
            @RequestParam(required = false) boolean autoPlay) {
        gameService.restartGame(computerOpponent);
        maybeMakeAutoMove(autoPlay);
        var message = new GameResponseSuccess(gameService.getGameStateMessage());
        return ResponseEntity.ok(message);
    }

    @Override
    @PostMapping(value = "/draw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameResponse> drawMatches(@RequestBody MoveMessage move) {
        if (!gameService.isGameStarted()) {
            var error = new GameResponseError(Constants.Error.GAME_MUST_BE_STARTED);
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
