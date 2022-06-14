import { API_BASE_URL } from "../Constants";
import { GameStateMessage } from "../model/GameStateMessage";

export async function makeFetch(path: string): Promise<GameStateMessage> {
  const options: RequestInit = {
    mode: "cors",
    headers: {
      "content-type": "application/json",
    },
  };
  const stateResponse = await fetch(`${API_BASE_URL}/${path}`, options);
  return await stateResponse.json();
}
