import React, { useEffect, useState } from "react";
import logo from "./logo.svg";
import styles from "./App.module.css";
import { GameState } from "./model/GameState";
import {makeFetch} from "./service/FetchService";

function App() {
  const [appState, setAppState] = useState<null | GameState>(null);

  useEffect(() => {
    const initGame = async () => {
      const stateMessage = await makeFetch("state");
      setAppState(stateMessage.gameState);
    };
    initGame();
  }, []);

  return (
    <div className={styles.App}>
      <header className={styles["App-header"]}>
        <img src={logo} className={styles["App-logo"]} alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className={styles["App-link"]}
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
      <div>
        <h2>{appState == null ? "LOADING..." : "initialized"}</h2>
      </div>
    </div>
  );
}

export default App;
