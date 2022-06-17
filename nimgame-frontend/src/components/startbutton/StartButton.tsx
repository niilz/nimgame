import { GameState } from "../../model/GameState";
import { StartConfig } from "../game/Game";

type StartButtonProps = {
  onStart: (config: StartConfig) => void;
  gameState: GameState;
  autoPlay: boolean;
  computerOpponent: boolean;
};

export function StartButton(props: StartButtonProps) {
  const startConfig = {
    isRestart: props.gameState !== GameState.STOPPED,
    autoPlay: props.autoPlay,
    computerOpponent: props.computerOpponent,
  };
  return (
    <button onClick={() => props.onStart(startConfig)}>
      {startConfig.isRestart ? "restart" : "start"}
    </button>
  );
}
