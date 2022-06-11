package de.holisticon.vorsprechen.niilz.nimgame.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Application Interface to play the NimGame with REST-Calls
 */
@RestController
@RequestMapping("/api/v1")
public class NimGameController {

    /**
     * @return Dummy-Text to show that the controller is working
     */
    @GetMapping("/")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World!");
    }
}
