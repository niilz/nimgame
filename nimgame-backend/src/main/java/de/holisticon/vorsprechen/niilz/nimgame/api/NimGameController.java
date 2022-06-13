package de.holisticon.vorsprechen.niilz.nimgame.api;

import de.holisticon.vorsprechen.niilz.nimgame.model.GameResponse;
import de.holisticon.vorsprechen.niilz.nimgame.model.GameResponseError;
import de.holisticon.vorsprechen.niilz.nimgame.model.GameResponseSuccess;
import de.holisticon.vorsprechen.niilz.nimgame.model.GameStateMessage;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessage;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessageHuman;
import de.holisticon.vorsprechen.niilz.nimgame.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1")
@Slf4j
public class NimGameController {

    private final GameService gameService;

    NimGameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/state")
    public ResponseEntity<GameStateMessage> getState() {
        return ResponseEntity.ok(gameService.getGameStateMessage());
    }

    @PostMapping(value = "/draw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameResponse> drawMatches(@RequestBody MoveMessage move) {
        if (!gameService.isGameStarted()) {
            var error = new GameResponseError("Game must be started before matches can be drawn");
            return ResponseEntity.badRequest().body(error);
        }
        try {
            if (move instanceof MoveMessageHuman humanMove) {
                // MakesMove (which includes swapping the players)
                gameService.makeMove(humanMove);
                // Autoplay means trigger the computer-move immediately
                // (autoplay is ignored if next-player is not a computer
                if (gameService.isCurrentPlayerComputer() && humanMove.isAutoPlay()) {
                    gameService.makeComputerMove(gameService.getCurrentPlayersRank());
                }
            } else {
                gameService.makeComputerMove(move.getPlayerRank());
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(new GameResponseError(e.getMessage()));
        }
        var message = new GameResponseSuccess(gameService.getGameStateMessage());
        return ResponseEntity.ok(message);
    }

    @GetMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameResponse> initGame(@RequestParam(required = false) boolean computerOpponent) {
        if (gameService.isGameStarted()) {
            var error = new GameResponseError("Game has already been started");
            return ResponseEntity.badRequest().body(error);
        }
        log.info("Starting initial Game state");
        gameService.startGame(computerOpponent);
        var message = new GameResponseSuccess(gameService.getGameStateMessage());
        return ResponseEntity.ok(message);
    }

    @GetMapping(value = "/restart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> restart(@RequestParam(required = false) boolean computerOpponent) {
        gameService.restartGame(computerOpponent);
        return ResponseEntity.ok("Reset Game");
    }
}
