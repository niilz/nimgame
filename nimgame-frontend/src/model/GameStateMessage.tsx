import { GameState } from "./GameState";

export type GameStateMessage = {
  player: Player;
  type: PlayerType;
  currentMatchCount: number;
  gameState: GameState;
};

enum Player {
  ONE,
  TWO,
}

enum PlayerType {
  HUMAN,
  COMPUTER,
}
