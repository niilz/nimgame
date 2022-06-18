use std::{borrow::Borrow, cell::RefCell, fmt::Formatter, ptr::NonNull, rc::Rc};

const TOTAL_MATCH_COUNT: u8 = 13;

#[derive(PartialEq, Eq, Debug, Clone, Copy)]
enum Player {
    ONE,
    TWO,
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
    let mut move_tree = Rc::new(RefCell::new(MoveNode::new(5, Player::ONE)));
    create_tree(&mut move_tree);

    println!("The tree: {move_tree:#?}");
}

fn create_tree(last_node: &Rc<RefCell<MoveNode>>) {
    // This is hefty
    let node = <Rc<RefCell<MoveNode>> as Borrow<RefCell<MoveNode>>>::borrow(last_node);
    if node.borrow().remaining == 0 {
        return;
    }
    let player = if node.borrow().player == Player::ONE {
        Player::TWO
    } else {
        Player::ONE
    };
    let remaining_matches = node.borrow().remaining - 1;
    let one_node = Rc::new(RefCell::new(MoveNode::new(remaining_matches, player)));
    last_node.borrow_mut().one = Some(Rc::clone(&one_node));
    create_tree(&one_node);
    if remaining_matches > 1 {
        let two_node = Rc::new(RefCell::new(MoveNode::new(remaining_matches - 1, player)));
        last_node.borrow_mut().two = Some(Rc::clone(&two_node));
        create_tree(&two_node);
    }
    if remaining_matches > 2 {
        let three_node = Rc::new(RefCell::new(MoveNode::new(remaining_matches - 2, player)));
        last_node.borrow_mut().three = Some(Rc::clone(&three_node));
        create_tree(&three_node);
    }
}
