import React, { useEffect, useState } from "react";
import logo from "./logo.svg";
import styles from "./App.module.css";
import { GameState } from "./model/GameState";
import { makeFetch } from "./service/FetchService";
import { Game } from "./components/game/Game";

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
        <h1>The NimGame by</h1>
        <img src={logo} className={styles["App-logo"]} alt="logo" />
      </header>
      <main>
        {appState == null ? <h2>"LOADING..."</h2> : <Game state={appState} />}
      </main>
    </div>
  );
}

export default App;
