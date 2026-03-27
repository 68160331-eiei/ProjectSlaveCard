import java.awt.Image;
import javax.swing.ImageIcon;

public class Card {
    private final int rank; // 0=3, 11=A, 12=2
    private final int suit; // 0=Club, 1=Diamond, 2=Heart, 3=Spade
    private boolean selected = false;

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getWeight() { return (rank * 4) + suit; }
    public int getRank() { return rank; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean s) { this.selected = s; }

    @Override
    public String toString() {
        String[] ranks = {"3","4","5","6","7","8","9","10","J","Q","K","A","2"};
        String[] suits = {"♣", "♦", "♥", "♠"};
        return ranks[rank] + suits[suit];
    }
}