import { PlayerRank, PlayerType } from "../components/player/Player";

export interface MoveMessage {
  player: PlayerRank;
  playerType: PlayerType;
}

export interface MoveMessageComputer extends MoveMessage {}

export interface MoveMessageHuman extends MoveMessage {
  drawnMatches: number;
  autoPlay: boolean;
}
