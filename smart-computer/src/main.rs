use std::collections::HashMap;

const TOTAL_MATCH_COUNT: u8 = 13;

#[derive(PartialEq, Eq, Debug, Clone, Copy, Hash)]
enum Player {
    ONE,
    TWO,
}

#[derive(Debug, PartialEq, Eq, Clone)]
struct Game {
    winner: Player,
    moves: Vec<Condition>,
}

#[derive(Debug, PartialEq, Clone, Eq, Hash)]
struct Condition {
    player: Player,
    drawn: u8,
    remaining: u8,
}

#[derive(Debug, Clone)]
struct MoveCollector {
    player: Player,
    games: Vec<Game>,
    counter: u32,
}
impl MoveCollector {
    fn new() -> Self {
        MoveCollector {
            games: Vec::new(),
            player: Player::ONE,
            counter: 0,
        }
    }
    fn collect_all_games(&mut self, moves: &[Condition], remaining_matches: u8) {
        let current_player = self.player;
        let next_player = swap_player(current_player);
        if remaining_matches == 0 {
            // No more matches: last player looses (last player drew the last match)
            // So th player tha would play now (current_player) wins
            let finished_game = Game {
                winner: current_player,
                moves: moves.to_vec(),
            };
            self.games.push(finished_game);
            return;
        }
        // Draw one 1, 2 and 3 matches at this point in the game
        for drawn in 1..=3 {
            self.player = next_player;
            if drawn <= remaining_matches {
                self.counter += 1;
                let mut this_moves = moves.to_vec();
                this_moves.push(Condition {
                    player: current_player,
                    drawn,
                    remaining: remaining_matches,
                });
                self.collect_all_games(&this_moves, remaining_matches - drawn);
            }
        }
    }

    fn collect_won_games(&self, player: Player) -> Vec<&Game> {
        // Check in which ones Player ONE won (was not last);
        self.games
            .iter()
            .filter(|game| game.winner == player)
            .collect()
    }
}

fn main() {
    let mut move_collector = MoveCollector::new();
    let mut moves = Vec::new();
    move_collector.collect_all_games(&mut moves, TOTAL_MATCH_COUNT);

    let games_won_by_one = move_collector.collect_won_games(Player::ONE);
    let games_won_by_two = move_collector.collect_won_games(Player::TWO);

    println!(
        "all-games: {}, games-won-by-ONE: {}, games-won-by-two: {}",
        move_collector.games.len(),
        games_won_by_one.len(),
        games_won_by_two.len()
    );

    // Determine which positions have the highest chance ONE
    let mut weights_one = HashMap::new();
    for game in games_won_by_one {
        println!("{game:#?}");
        for condition in game.moves.iter().step_by(2) {
            *weights_one.entry(condition).or_insert(0) += 1;
        }
    }
    // Determine which positions have the highest chance for player TWO
    let mut weights_two = HashMap::new();
    for game in games_won_by_two {
        println!("{game:#?}");
        for condition in game.moves[1..].iter().step_by(2) {
            *weights_two.entry(condition).or_insert(0) += 1;
        }
    }

    println!("Weights-ONE");
    println!("{weights_one:#?}");
    println!();
    println!("Weights-TWO");
    println!("{weights_two:#?}");

    let best_draw_when_5_remain_one: Vec<_> = weights_one
        .iter()
        .filter(|(condition, weight)| condition.remaining == 10)
        .collect();

    println!("Chances for 5 remaing ONE");
    println!("{best_draw_when_5_remain_one:#?}");
    println!();

    let best_draw_when_5_remain_two: Vec<_> = weights_two
        .iter()
        .filter(|(condition, weight)| condition.remaining == 10)
        .collect();

    println!("Chances for 5 remaing TWO");
    println!("{best_draw_when_5_remain_two:#?}");
}

fn swap_player(player: Player) -> Player {
    // Swap the Players and make all moves (1, 2, 3)
    if player == Player::ONE {
        Player::TWO
    } else {
        Player::ONE
    }
}
