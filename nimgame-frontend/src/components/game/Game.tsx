import { useState } from "react";
import { GameState } from "../../model/GameState";
import { GameStateMessage } from "../../model/GameStateMessage";
import { Option as Option } from "../autoplayoption/AutoPlayOption";
import { Matches } from "../matches/Matches";
import { Player, PlayerType } from "../player/Player";
import styles from "./Game.module.css";

type GameProps = {
  gameStateMessage: GameStateMessage;
  onStart: (autoPlay: boolean, computerOpponent: boolean) => void;
  onRestart: (autoPlay: boolean, computerOpponent: boolean) => void;
  makeMove: (drawnMatches?: number, autoPlay?: boolean) => void;
};

export function Game(props: GameProps) {
  const [drawnMatches, setDrawnMatches] = useState("1");
  const [autoPlay, setAutoPlay] = useState<undefined | boolean>(undefined);
  const [autoPlayAtStart, setAutoPlayAtStart] = useState(false);
  const [computerOpponent, setComputerOpponent] = useState(false);
  const stateMessage = props.gameStateMessage;

  const getStartOptionsHeading = () => {
    if (stateMessage.gameState === GameState.STOPPED) {
      return "Start";
    }
    return "Restart";
  };
  return (
    <div className={styles.Game}>
      <Matches remainingMatches={stateMessage.currentMatchCount} />
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
          <Option optionName="auto-play" onChange={setAutoPlay} />
        )}
      </div>
      <div className={styles.StartOptions}>
        <h3 className={styles.H3}>{`${getStartOptionsHeading()} Options`}</h3>
        <Option
          optionName={"computer-opponent"}
          onChange={setComputerOpponent}
        />
        <Option optionName={"auto-play"} onChange={setAutoPlayAtStart} />
        {stateMessage.gameState !== GameState.STOPPED ? (
          <button
            onClick={() => props.onRestart(autoPlayAtStart, computerOpponent)}
          >
            restart
          </button>
        ) : (
          <button
            onClick={() => props.onStart(autoPlayAtStart, computerOpponent)}
          >
            start
          </button>
        )}
      </div>
    </div>
  );
}
