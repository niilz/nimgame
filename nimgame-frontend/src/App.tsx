import { useEffect, useState } from "react";
import logo from "./logo.svg";
import styles from "./App.module.css";
import { makeFetch } from "./service/FetchService";
import { Game } from "./components/game/Game";
import { GameStateMessage } from "./model/GameStateMessage";
import { PlayerType } from "./components/player/Player";

function App() {
  const [gameState, setGameState] = useState<null | GameStateMessage>(null);
  const [autoPlayAtStart, setAutoPlayAtStart] = useState(false);
  const [computerOpponent, setComputerOpponent] = useState(false);

  useEffect(() => {
    const initGame = async () => {
      const stateMessage = await makeFetch("state");
      setGameState(stateMessage);
    };
    initGame();
  }, []);

  const handleStart = async () => {
    const startUrlWithOptions = `start?computerOpponent=${computerOpponent}&autoPlay=${autoPlayAtStart}`;
    const stateMessage = await makeFetch(startUrlWithOptions, "POST");
    setGameState(stateMessage);
  };

  const handleRestart = async () => {
    const stateMessage = await makeFetch(
      "restart?computerOpponent=true",
      "POST"
    );
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
      <header className={styles["App-header"]}>
        <h1>The NimGame by</h1>
        <img src={logo} className={styles["App-logo"]} alt="logo" />
      </header>
      <main>
        {gameState == null ? (
          <h2>"LOADING..."</h2>
        ) : (
          <Game
            gameStateMessage={gameState}
            onStart={handleStart}
            onRestart={handleRestart}
            makeMove={handleMove}
          />
        )}
      </main>
    </div>
  );
}

export default App;
