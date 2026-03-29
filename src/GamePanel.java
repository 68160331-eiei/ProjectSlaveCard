import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GamePanel extends JPanel {
    private MainFrame frame;

    public GamePanel(MainFrame frame) {
        this.frame = frame;
        setBackground(new Color(0, 102, 51));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (frame.getTurn() == 0) handleMouse(e.getX(), e.getY());
            }
        });
    }

    private void handleMouse(int mx, int my) {
        List<Card> hand = frame.getPlayers().get(0).getHand();
        for (int i = hand.size() - 1; i >= 0; i--) {
            int x = 100 + (i * 30), y = hand.get(i).isSelected() ? 420 : 450;
            if (mx >= x && mx <= x + 70 && my >= y && my <= y + 100) {
                hand.get(i).setSelected(!hand.get(i).isSelected());
                repaint();
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (frame.getPlayers().isEmpty()) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.drawString("TABLE CENTER", 450, 180);
        int tx = 400;
        for (Card c : frame.getTableCards()) {
            drawCard(g2, c, tx, 200); tx += 25;
        }

        int px = 100;
        for (Card c : frame.getPlayers().get(0).getHand()) {
            drawCard(g2, c, px, c.isSelected() ? 420 : 450); px += 30;
        }

        g2.setFont(new Font("Tahoma", Font.BOLD, 14));
        g2.setColor(Color.YELLOW);
        g2.drawString("Turn: " + frame.getPlayers().get(frame.getTurn()).getName(), 20, 30);
    }

    private void drawCard(Graphics2D g2, Card c, int x, int y) {
        g2.setColor(Color.WHITE); g2.fillRoundRect(x, y, 70, 100, 10, 10);
        g2.setColor(Color.BLACK); g2.drawRoundRect(x, y, 70, 100, 10, 10);
        g2.setColor(c.toString().contains("♥") || c.toString().contains("♦") ? Color.RED : Color.BLACK);
        g2.drawString(c.toString(), x + 10, y + 55);
    }
}