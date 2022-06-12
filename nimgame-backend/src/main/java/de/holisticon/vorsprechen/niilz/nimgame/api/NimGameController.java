package de.holisticon.vorsprechen.niilz.nimgame.api;

import de.holisticon.vorsprechen.niilz.nimgame.model.GameStateMessage;
import de.holisticon.vorsprechen.niilz.nimgame.model.MoveMessage;
import de.holisticon.vorsprechen.niilz.nimgame.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    /**
     * @return Dummy-Text to show that the controller is working
     */
    @GetMapping("/")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World!");
    }

    @PostMapping("/draw")
    public ResponseEntity<GameStateMessage> drawMatches(@RequestBody MoveMessage move) {
        gameService.makeMove(move);
        return ResponseEntity.ok(gameService.getGameStateMessage());
    }

    @GetMapping("/start")
    public ResponseEntity<GameStateMessage> initGame() {
        if (gameService.isGameStarted()) {
            log.error("Game has already been started");
            return ResponseEntity.badRequest().build();
        }
        log.info("Starting initial Game state");
        gameService.startGame();
        return ResponseEntity.ok(gameService.getGameStateMessage());
    }
}
