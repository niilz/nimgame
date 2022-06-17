import { API_BASE_URL } from "../Constants";
import { GameStateMessage } from "../model/GameStateMessage";

export async function makeFetch(
  path: string,
  method?: string,
  body?: string
): Promise<GameStateMessage> {
  const options: RequestInit = {
    method: method || "GET",
    mode: "cors",
    headers: {
      "content-type": "application/json",
    },
    body,
  };
  const stateResponse = await fetch(`${API_BASE_URL}/${path}`, options);
  const res = await stateResponse.json();
  if (res.error) {
    throw `An error occured: ${res.error}`;
  }
  return res.message;
}
