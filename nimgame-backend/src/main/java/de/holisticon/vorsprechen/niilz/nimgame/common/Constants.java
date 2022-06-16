package de.holisticon.vorsprechen.niilz.nimgame.common;

/**
 * Contains general Messages like error-messages for API-Responses
 */
public abstract class Constants {

    public abstract class Message {

        public static final String GAME_STATE_CREATED = "GameState has been created";
        public static final String GAME_STARTED = "Game has been started";
        public static final String GAME_FINISHED = "Game has been finished";
    }

    public static abstract class Error {

        public static final String ILLEGAL_GAME_STATE = "Game could not be started because of illegal state";
        public static final String GAME_ALREADY_STARTED = "Game has already been started";
        public static final String GAME_MUST_BE_STARTED = "Game must be started before matches can be drawn";
        public static final String GAME_ALREADY_WON = "Game is already won";
        public static final String SAME_PLAYER = "Same Player must not play again";
        public static final String WRONG_MATCH_COUNT_DRAWN = "Player is only allowed to draw between 1 and 3 matches";
        public static final String NOT_ENOUGH_MATCHES_REMAINING = "Player must not draw more matches than there are remaining";
        public static final String PLAYER_TYPE_NOT_SUPPORTED = "Move must be of type Human or Computer";
        public static final String INVALID_PLAYER_TYPE = "Player is of wrong type";
    }

}
