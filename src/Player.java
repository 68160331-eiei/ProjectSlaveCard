import java.util.*;

abstract class Player {
    protected String name;
    protected List<Card> hand = new ArrayList<>();
    protected boolean isPassed = false;

    public Player(String name) { this.name = name; }
    public void addCard(Card c) { hand.add(c); }
    public void sortHand() { hand.sort(Comparator.comparingInt(Card::getWeight)); }
    public List<Card> getHand() { return hand; }
    public String getName() { return name; }
    public void setPassed(boolean p) { isPassed = p; }
    public boolean isPassed() { return isPassed; }
    public abstract List<Card> play(List<Card> tableCards);
}

class AIPlayer extends Player {
    public AIPlayer(String name) { super(name); }

    @Override
    public List<Card> play(List<Card> tableCards) {
        if (tableCards == null || tableCards.isEmpty()) {
            // AI strategy: Try to play Triple -> Pair -> Single from lowest rank
            for (int size = 3; size >= 1; size--) {
                for (int i = 0; i <= hand.size() - size; i++) {
                    List<Card> move = new ArrayList<>(hand.subList(i, i + size));
                    if (SlaveGameEngine.isValidMove(move, tableCards)) {
                        hand.removeAll(move);
                        return move;
                    }
                }
            }
        } else {
            int reqSize = tableCards.size();
            for (int i = 0; i <= hand.size() - reqSize; i++) {
                List<Card> move = new ArrayList<>(hand.subList(i, i + reqSize));
                if (SlaveGameEngine.isValidMove(move, tableCards)) {
                    hand.removeAll(move);
                    return move;
                }
            }
        }
        return null;
    }
}

class HumanPlayer extends Player {
    public HumanPlayer(String name) { super(name); }
    @Override public List<Card> play(List<Card> tableCards) { return null; }
}