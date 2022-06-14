import React from "react";
import { GameState } from "../../model/GameState";
import styles from "./components/Game.module.css";

type GameProps = {
  state: GameState;
};

export function Game(props: GameProps) {
  return <div>THE GAME</div>
}
