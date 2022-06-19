import { useEffect, useState } from "react";
import logo from "./logo.svg";
import styles from "./App.module.css";
import { makeFetch } from "./service/FetchService";
import { Game, StartConfig } from "./components/game/Game";
import { GameStateMessage } from "./model/GameStateMessage";
import { PlayerType } from "./components/player/Player";
import { GameState } from "./model/GameState";

function App() {
  const [gameState, setGameState] = useState<null | GameStateMessage>(null);

  useEffect(() => {
    const initGame = async () => {
      const stateMessage = await makeFetch("state");
      setGameState(stateMessage);
    };
    initGame();
  }, []);

  const handleStart = async (config: StartConfig) => {
    const startUrlWithOptions = `${
      config.isRestart ? "restart" : "start"
    }?computerOpponent=${config.computerOpponent}&autoPlay=${
      config.autoPlay
    }&playSmart=${config.playSmart}`;
    const stateMessage = await makeFetch(startUrlWithOptions, "POST");
    setGameState(stateMessage);
  };

  const handleMove = async (drawnMatches?: number, autoPlay?: boolean) => {
    if (!gameState) {
      throw "Move can not be made before game is started";
    }
    const moveMessage =
      gameState.type === PlayerType.COMPUTER
        ? {
            player: gameState.player,
            playerType: gameState.type,
          }
        : {
            player: gameState.player,
            playerType: gameState.type,
            drawnMatches,
            autoPlay,
          };
    const stateMessage = await makeFetch(
      "draw",
      "POST",
      JSON.stringify(moveMessage)
    );
    setGameState(stateMessage);
  };

  return (
    <div className={styles.App}>
      <header className={styles.AppHeader}>
        <h1>The NimGame by</h1>
        <img src={logo} className={styles.AppLogo} alt="logo" />
      </header>
      <main>
        {gameState == null ? (
          <h2>"LOADING..."</h2>
        ) : (
          <Game
            gameStateMessage={gameState}
            onStart={handleStart}
            makeMove={handleMove}
          />
        )}
      </main>
      {gameState && gameState.gameState === GameState.WON && (
        <div id="glitter-wrapper" className={styles.GlitterWrapper}>
          {[...new Array(100)].map((_, idx) => (
            <div
              id={`${idx}`}
              key={`${idx}-glitter`}
              className={
                Math.random() > 0.5 ? styles.GlitterA : styles.GlitterB
              }
              style={{
                left: Math.random() * window.innerWidth + "px",
                top: Math.random() * window.innerHeight + "px",
                backgroundColor:
                  Math.random() > 0.5
                    ? "var(--primary-color)"
                    : "var(--secondary-color)",
              }}
            ></div>
          ))}
        </div>
      )}
    </div>
  );
}

export default App;
