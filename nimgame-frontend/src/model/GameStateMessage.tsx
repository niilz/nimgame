import { PlayerRank, PlayerType } from "../components/player/Player";
import { GameState } from "./GameState";

export type GameStateMessage = {
  player: PlayerRank;
  type: PlayerType;
  currentMatchCount: number;
  gameState: GameState;
};
