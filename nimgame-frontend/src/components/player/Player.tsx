import styles from "./Player.module.css";

type PlayerProps = {
  player: PlayerRank;
  playerType: PlayerType;
  gameWon: boolean;
};

export enum PlayerRank {
  ONE = "ONE",
  TWO = "TWO",
}

export enum PlayerType {
  HUMAN = "HUMAN",
  COMPUTER = "COMPUTER",
}

export function Player(props: PlayerProps) {
  return (
    <div
      className={`${styles.Player} ${props.gameWon ? styles.WonMessage : ""}`}
    >
      <p>Player: {`${props.player}`}</p>
      {props.gameWon && <p>of</p>}
      <p>Type: {`${props.playerType}`}</p>
      {props.gameWon && <p>WON!</p>}
    </div>
  );
}
