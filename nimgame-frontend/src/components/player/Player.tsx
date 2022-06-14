import styles from "./Player.module.css";

type PlayerProps = {
  player: PlayerRank;
  playerType: PlayerType;
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
    <div className={styles.Player}>
      <p>Player: {`${props.player}`}</p>
      <p>Type: {`${props.playerType}`}</p>
    </div>
  );
}
