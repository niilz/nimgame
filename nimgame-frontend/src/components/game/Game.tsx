import { ChangeEvent, FormEvent, useState } from "react";
import { GameState } from "../../model/GameState";
import { GameStateMessage } from "../../model/GameStateMessage";
import { Matches } from "../matches/Matches";
import { Player } from "../player/Player";
import styles from "./Game.module.css";

type GameProps = {
  state: GameStateMessage;
  onStart: () => void;
  onRestart: () => void;
};

export function Game(props: GameProps) {
  const [matches, setMatches] = useState("1");
  return (
    <div className={styles.Game}>
      <Matches remainingMatches={props.state.currentMatchCount} />
      <Player player={props.state.player} playerType={props.state.type} />
      <label className={styles.Label}>{matches}</label>
      <input
        type="range"
        min="1"
        max="3"
        value={matches}
        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
          setMatches(e.target.value)
        }
      />
      {props.state.gameState == GameState.RUNNING ? (
        <button onClick={props.onRestart}>restart</button>
      ) : (
        <button onClick={props.onStart}>start</button>
      )}
    </div>
  );
}
