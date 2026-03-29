import java.util.List;

public class SlaveGameEngine {
    public static boolean isValidMove(List<Card> toPlay, List<Card> onTable) {
        if (toPlay == null || toPlay.isEmpty()) return false;

        // All selected cards must have the same rank (Singles, Pairs, or Triples)
        int firstRank = toPlay.get(0).getRank();
        for (Card c : toPlay) {
            if (c.getRank() != firstRank) return false;
        }

        // If table is empty, any valid set (1, 2, or 3 cards) can be played
        if (onTable == null || onTable.isEmpty()) return true;

        // Must play the same number of cards as on the table and beat the weight
        if (toPlay.size() != onTable.size()) return false;
        return toPlay.get(toPlay.size() - 1).getWeight() > onTable.get(onTable.size() - 1).getWeight();
    }
}