import java.util.List;

public class SlaveGameEngine {
    public static boolean isValidMove(List<Card> toPlay, List<Card> onTable) {
        if (toPlay == null || toPlay.isEmpty()) return false;

        // ไพ่ที่ลงต้องมี Rank เดียวกันทุดใบ (กรณีลงคู่/ตอง)
        int firstRank = toPlay.get(0).getRank();
        for (Card c : toPlay) {
            if (c.getRank() != firstRank) return false;
        }

        // ถ้าโต๊ะว่าง (ได้เริ่มรอบใหม่) ลงกี่ใบก็ได้ที่แต้มเท่ากัน
        if (onTable == null || onTable.isEmpty()) return true;

        // ต้องลงจำนวนใบเท่ากับบนโต๊ะเท่านั้น และใบสุดท้าย(ใหญ่สุด)ต้องชนะบนโต๊ะ
        if (toPlay.size() != onTable.size()) return false;
        return toPlay.get(toPlay.size() - 1).getWeight() > onTable.get(onTable.size() - 1).getWeight();
    }
}