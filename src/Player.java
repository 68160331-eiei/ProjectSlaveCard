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
        if (isPassed) return null;
        int reqSize = (tableCards == null || tableCards.isEmpty()) ? 1 : tableCards.size();

        // ค้นหาชุดไพ่ในมือที่ขนาดเท่ากับบนโต๊ะ
        for (int i = 0; i <= hand.size() - reqSize; i++) {
            List<Card> move = new ArrayList<>(hand.subList(i, i + reqSize));
            if (SlaveGameEngine.isValidMove(move, tableCards)) {
                hand.removeAll(move);
                return move;
            }
        }
        setPassed(true);
        return null;
    }
}

class HumanPlayer extends Player {
    public HumanPlayer(String name) { super(name); }
    @Override
    public List<Card> play(List<Card> tableCards) { return null; }
}