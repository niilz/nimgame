import { useState } from "react";
import { GameState } from "../../model/GameState";
import { GameStateMessage } from "../../model/GameStateMessage";
import { Matches } from "../matches/Matches";
import { Option } from "../option/Option";
import { Player, PlayerType } from "../player/Player";
import { StartButton } from "../startbutton/StartButton";
import styles from "./Game.module.css";

type GameProps = {
  gameStateMessage: GameStateMessage;
  onStart: (startConfig: StartConfig) => void;
  makeMove: (drawnMatches?: number, autoPlay?: boolean) => void;
};

export type StartConfig = {
  isRestart: boolean;
  autoPlay: boolean;
  computerOpponent: boolean;
  playSmart: boolean;
};

export function Game(props: GameProps) {
  const [drawnMatches, setDrawnMatches] = useState("1");
  const [autoPlay, setAutoPlay] = useState<undefined | boolean>(undefined);
  const [autoPlayAtStart, setAutoPlayAtStart] = useState(false);
  const [computerOpponent, setComputerOpponent] = useState(false);
  const [playSmart, setPlaySmart] = useState(false);
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
      <Player
        player={stateMessage.player}
        playerType={stateMessage.type}
        gameWon={stateMessage.gameState === GameState.WON}
      />
      {stateMessage.type === PlayerType.HUMAN &&
        stateMessage.gameState === GameState.RUNNING && (
          <div className={styles.Matches}>
            <label htmlFor="match-count" className={styles.Label}>
              {`draw ${drawnMatches} match${drawnMatches === "1" ? "" : "es"}`}
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
          </div>
        )}
      {stateMessage.gameState !== GameState.RUNNING || (
        <div className={styles.ActionArea}>
          <button
            onClick={() =>
              props.makeMove(Number.parseInt(drawnMatches), autoPlay)
            }
          >
            draw
          </button>
          {stateMessage.type === PlayerType.HUMAN && (
            <Option optionName="auto-play" onChange={setAutoPlay} />
          )}
        </div>
      )}
      <div className={styles.StartOptions}>
        <h3 className={styles.H3}>{`${getStartOptionsHeading()} Options`}</h3>
        <Option
          optionName={"computer-opponent"}
          onChange={setComputerOpponent}
        />
        <Option optionName={"auto-play"} onChange={setAutoPlayAtStart} />
        <Option optionName={"play-smart"} onChange={setPlaySmart} />
        <StartButton
          onStart={props.onStart}
          gameState={stateMessage.gameState}
          autoPlay={autoPlayAtStart}
          computerOpponent={computerOpponent}
          playSmart={playSmart}
        />
      </div>
    </div>
  );
}
