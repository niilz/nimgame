import { GameState } from "../../model/GameState";
import { GameStateMessage } from "../../model/GameStateMessage";
import { Matches } from "../matches/Matches";
import { Player } from "../player/Player";
import styles from "./components/Game.module.css";

type GameProps = {
  state: GameStateMessage;
  onStart: () => void;
  onRestart: () => void;
};

export function Game(props: GameProps) {
  return (
    <div>
      <Matches remainingMatches={props.state.currentMatchCount} />
      <Player player={props.state.player} playerType={props.state.type} />
      {props.state.gameState == GameState.RUNNING ? (
        <button onClick={props.onRestart}>restart</button>
      ) : (
        <button onClick={props.onStart}>start</button>
      )}
    </div>
  );
}
