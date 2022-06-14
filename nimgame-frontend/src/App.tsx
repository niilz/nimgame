import React, { useEffect, useState } from "react";
import logo from "./logo.svg";
import styles from "./App.module.css";
import { makeFetch } from "./service/FetchService";
import { Game } from "./components/game/Game";
import { GameStateMessage } from "./model/GameStateMessage";

function App() {
  const [appState, setAppState] = useState<null | GameStateMessage>(null);

  useEffect(() => {
    const initGame = async () => {
      const stateMessage = await makeFetch("state");
      setAppState(stateMessage);
    };
    initGame();
  }, []);

  const handleStart = async () => {
    const stateMessage = await makeFetch("start?computerOpponent=true");
    setAppState(stateMessage);
  };

  const handleRestart = async () => {
    const stateMessage = await makeFetch("restart?computerOpponent=true");
    setAppState(stateMessage);
  };

  return (
    <div className={styles.App}>
      <header className={styles["App-header"]}>
        <h1>The NimGame by</h1>
        <img src={logo} className={styles["App-logo"]} alt="logo" />
      </header>
      <main>
        {appState == null ? (
          <h2>"LOADING..."</h2>
        ) : (
          <Game
            state={appState}
            onStart={handleStart}
            onRestart={handleRestart}
          />
        )}
      </main>
    </div>
  );
}

export default App;
