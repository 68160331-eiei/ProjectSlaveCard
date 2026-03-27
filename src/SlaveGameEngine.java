import java.util.List;

public class SlaveGameEngine {
    public static boolean isValidMove(List<Card> toPlay, List<Card> onTable) {
        if (toPlay == null || toPlay.isEmpty()) return false;

        // ตรวจสอบว่าไพ่ที่เลือกต้องมี Rank เดียวกันทุดใบ (กรณีลงคู่/ตอง)
        int firstRank = toPlay.get(0).getRank();
        for (Card c : toPlay) {
            if (c.getRank() != firstRank) return false;
        }

        // ถ้าโต๊ะว่าง ลงกี่ใบก็ได้ที่แต้มเท่ากัน
        if (onTable == null || onTable.isEmpty()) return true;

        // ต้องลงจำนวนใบเท่ากับบนโต๊ะเท่านั้น
        if (toPlay.size() != onTable.size()) return false;

        // ไพ่ใบที่ใหญ่ที่สุดในชุดต้องชนะใบที่ใหญ่ที่สุดบนโต๊ะ
        return toPlay.get(toPlay.size() - 1).getWeight() > onTable.get(onTable.size() - 1).getWeight();
    }
}