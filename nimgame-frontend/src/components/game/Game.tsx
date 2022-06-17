import { useState } from "react";
import { GameState } from "../../model/GameState";
import { GameStateMessage as GameStateMessage } from "../../model/GameStateMessage";
import { AutoPlayOption } from "../autoplayoption/AutoPlayOption";
import { Matches } from "../matches/Matches";
import { Player, PlayerType } from "../player/Player";
import styles from "./Game.module.css";

type GameProps = {
  gameStateMessage: GameStateMessage;
  onStart: () => void;
  onRestart: () => void;
  makeMove: (drawnMatches?: number, autoPlay?: boolean) => void;
};

export function Game(props: GameProps) {
  const [drawnMatches, setDrawnMatches] = useState("1");
  const [autoPlay, setAutoPlay] = useState<undefined | boolean>(undefined);
  const stateMessage = props.gameStateMessage;

  return (
    <div className={styles.Game}>
      <Matches remainingMatches={stateMessage.currentMatchCount} />
      <div>
        <AutoPlayOption onAutoPlayChange={setAutoPlay} />
      </div>
      <Player player={stateMessage.player} playerType={stateMessage.type} />
      {stateMessage.type === PlayerType.HUMAN && (
        <>
          <label htmlFor="match-count" className={styles.Label}>
            {drawnMatches}
          </label>
          <input
            name="match-count"
            type="range"
            min="1"
            max="3"
            value={drawnMatches}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setDrawnMatches(e.target.value)
            }
          />
        </>
      )}
      <div className={styles.ActionArea}>
        {stateMessage.gameState !== GameState.RUNNING || (
          <button
            onClick={() =>
              props.makeMove(Number.parseInt(drawnMatches), autoPlay)
            }
          >
            draw
          </button>
        )}
        {stateMessage.type === PlayerType.HUMAN && (
          <AutoPlayOption onAutoPlayChange={setAutoPlay} {...styles} />
        )}
        {stateMessage.gameState !== GameState.STOPPED ? (
          <button onClick={props.onRestart}>restart</button>
        ) : (
          <button onClick={props.onStart}>start</button>
        )}
      </div>
    </div>
  );
}
