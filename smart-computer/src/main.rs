use std::{borrow::Borrow, cell::RefCell, fmt::Formatter, ptr::NonNull, rc::Rc};

const TOTAL_MATCH_COUNT: u8 = 13;

#[derive(PartialEq, Eq, Debug, Clone, Copy)]
enum Player {
    ONE,
    TWO,
}

#[derive(Debug)]
struct Game {
    winner: Player,
    moves: Vec<(Player, u8)>,
}

#[derive(Debug, Clone)]
struct MoveNode {
    remaining: u8,
    one: Option<Rc<RefCell<MoveNode>>>,
    two: Option<Rc<RefCell<MoveNode>>>,
    three: Option<Rc<RefCell<MoveNode>>>,
    player: Player,
}
impl MoveNode {
    fn new(remaining: u8, player: Player) -> Self {
        MoveNode {
            remaining,
            one: None,
            two: None,
            three: None,
            player,
        }
    }
}

fn main() {
    let mut move_tree = Rc::new(RefCell::new(MoveNode::new(13, Player::ONE)));
    // Collect all possible Moves
    let mut games = vec![];
    get_games(&mut games, &[], &mut move_tree);

    println!("The tree: {move_tree:#?}");
    println!();
    println!("Games: {games:?}");

    // Check in which ones Player ONE won (was not last);

    let games_won_by_one = games.iter().filter(|game| game.winner == Player::ONE);
    for game in games_won_by_one {
        println!("{:?}", game.moves);
    }
}

fn get_games(games: &mut Vec<Game>, moves: &[(Player, u8)], last_node: &Rc<RefCell<MoveNode>>) {
    // This is hefty
    let node = <Rc<RefCell<MoveNode>> as Borrow<RefCell<MoveNode>>>::borrow(last_node);

    // Swap the Players and make all moves (1, 2, 3)
    let player = if node.borrow().player == Player::ONE {
        Player::TWO
    } else {
        Player::ONE
    };

    if node.borrow().remaining == 0 {
        // No more matches: last_node.player looses (last_node.player drew the last match)
        let won_game = Game {
            winner: player,
            moves: moves.to_vec(),
        };
        games.push(won_game);
        return;
    }
    let remaining_matches = node.borrow().remaining;
    // Take one match
    let one_node = Rc::new(RefCell::new(MoveNode::new(remaining_matches - 1, player)));
    last_node.borrow_mut().one = Some(Rc::clone(&one_node));
    let mut moves_one = moves.to_vec();
    moves_one.push((player, 1));
    get_games(games, &moves_one[..], &one_node);
    // Take two matches
    if remaining_matches > 1 {
        let two_node = Rc::new(RefCell::new(MoveNode::new(remaining_matches - 2, player)));
        last_node.borrow_mut().two = Some(Rc::clone(&two_node));
        let mut moves_two = moves.to_vec();
        moves_two.push((player, 2));
        get_games(games, &moves_two[..], &two_node);
    }
    // Take three matches
    if remaining_matches > 2 {
        let three_node = Rc::new(RefCell::new(MoveNode::new(remaining_matches - 3, player)));
        last_node.borrow_mut().three = Some(Rc::clone(&three_node));
        let mut moves_three = moves.to_vec();
        moves_three.push((player, 3));
        get_games(games, &moves_three[..], &three_node);
    }
}
