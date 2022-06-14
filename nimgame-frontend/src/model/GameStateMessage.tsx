import { GameState } from "./GameState";

export type GameStateMessage = {
  player: Player;
  type: PlayerType;
  currentMatchCount: number;
  gameState: GameState;
};

enum Player {
  ONE = "ONE",
  TWO = "TWO",
}

enum PlayerType {
  HUMAN = "HUMAN",
  COMPUTER = "COMPUTER",
}
