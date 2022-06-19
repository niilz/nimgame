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
struct MoveTree {
    player: Player,
    games: Vec<Game>,
    counter: u32,
}
impl MoveTree {
    fn new() -> Self {
        MoveTree {
            games: Vec::new(),
            player: Player::ONE,
            counter: 0,
        }
    }
    fn collect_all_games(&mut self, moves: &[Condition], remaining_matches: u8) {
        let current_player = self.player;
        let next_player = swap_player(current_player);
        if remaining_matches == 0 {
            // No more matches: last_node.player looses (last_node.player drew the last match)
            let finished_game = Game {
                winner: next_player,
                moves: moves.to_vec(),
            };
            self.games.push(finished_game);
            return;
        }
        self.player = next_player;
        // Take one match
        for drawn in 1..=3 {
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
}

fn main() {
    let mut move_tree = MoveTree::new();
    let mut moves = Vec::new();
    move_tree.collect_all_games(&mut moves, TOTAL_MATCH_COUNT);

    let games = move_tree.games;
    //println!("{games:?}");

    // Check in which ones Player ONE won (was not last);
    let games_won_by_one: Vec<&Game> = (&games[..])
        .iter()
        .filter(|game| game.winner == Player::ONE)
        .collect();
    for game in &games_won_by_one[..] {
        println!("{:?}", game.moves);
    }

    println!(
        "all-games: {}, games-won-by-ONE: {}",
        games.len(),
        games_won_by_one.len()
    );

    // Determine which positions have the highest chance
    let mut weights = HashMap::new();
    for game in games_won_by_one {
        for condition in game.moves.iter().step_by(2) {
            *weights.entry(condition).or_insert(0) += 1;
        }
    }

    println!("Weights");
    println!("{weights:#?}");
}

fn swap_player(player: Player) -> Player {
    // Swap the Players and make all moves (1, 2, 3)
    if player == Player::ONE {
        Player::TWO
    } else {
        Player::ONE
    }
}
