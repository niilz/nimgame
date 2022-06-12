package de.holisticon.vorsprechen.niilz.nimgame.model;

public interface GameResponse {

    class GameResponseSuccess implements GameResponse {
        private final GameStateMessage message;

        public GameResponseSuccess(GameStateMessage message) {
            this.message = message;
        }
    }

    class GameResponseError implements GameResponse {
        private final String error;

        public GameResponseError(String error) {
            this.error = error;
        }
    }
}
